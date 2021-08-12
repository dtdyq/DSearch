/*
 * 
 */

package dyq.dsearch.service;

import com.google.gson.GsonBuilder;
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;

import dyq.dsearch.model.Document;
import dyq.dsearch.model.IndexingItem;
import dyq.dsearch.model.Word;
import dyq.dsearch.nlp.normalize.SimpleNormalizer;
import dyq.dsearch.repo.DocumentRepo;
import dyq.dsearch.repo.IndexingItemRepo;
import dyq.dsearch.nlp.simi.TFIDF;
import dyq.dsearch.nlp.tokenize.TextTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xm.similarity.text.CosineSimilarity;
import org.xm.similarity.text.TextSimilarity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private TFIDF tfidf;
    @Autowired
    private TextTokenizer textTokenizer;
    @Autowired
    private DocumentRepo documentRepo;
    @Autowired
    private IndexingItemRepo indexingItemRepo;

    @Autowired
    private SimpleNormalizer simpleNormalizer;

    public static void main(String[] args) {
        Word2VecTrainer train = new Word2VecTrainer();
        train.setLayerSize(300);
        train.train("D:\\code\\superProject\\dsearch\\data\\text8\\text8",
            "D:\\code\\superProject\\dsearch\\data\\text8\\text8.vec");

    }

    public String searchForSimpleText(String text) {
        text = simpleNormalizer.normalize(text);
        List<String> qWords = textTokenizer.tokenizerForIndex(text).stream().distinct().collect(Collectors.toList());
        Iterable<IndexingItem> indexes = indexingItemRepo.findAllById(qWords);
        List<String> docIds = new ArrayList<>();
        indexes.forEach(new Consumer<IndexingItem>() {
            @Override
            public void accept(IndexingItem indexingItem) {
                docIds.addAll(indexingItem.getUuids());
            }
        });
        Iterable<Document> docs = documentRepo.findAllById(docIds);

        Map<String, Double> docWeight = new HashMap<>();

        List<org.xm.tokenizer.Word> query = tfidf.TFDF(text).stream().map(new Function<Word, org.xm.tokenizer.Word>() {
            @Override
            public org.xm.tokenizer.Word apply(Word word) {
                org.xm.tokenizer.Word w = new org.xm.tokenizer.Word(word.getName());
                w.setWeight(word.getWeight());
                return w;
            }
        }).collect(Collectors.toList());
        TextSimilarity similarity = new CosineSimilarity();

        List<Document> res = new ArrayList<>();
        docs.forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                double ret = similarity.getSimilarity(
                    tfidf.TFDF(document.getContent()).stream().map(new Function<Word, org.xm.tokenizer.Word>() {
                        @Override
                        public org.xm.tokenizer.Word apply(Word word) {
                            org.xm.tokenizer.Word w = new org.xm.tokenizer.Word(word.getName());
                            w.setWeight(word.getWeight());
                            return w;
                        }
                    }).collect(Collectors.toList()), query);
                docWeight.put(document.getUuid(), ret);
                res.add(document);
            }
        });
        res.sort(new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                return Double.compare(docWeight.getOrDefault(o1.getUuid(), 0D),
                    docWeight.getOrDefault(o2.getUuid(), 0D));
            }
        });
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(res);
    }

}
