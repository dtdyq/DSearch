/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.nlp.simi;

import dyq.dsearch.model.IndexingItem;
import dyq.dsearch.model.Word;
import dyq.dsearch.nlp.tokenize.TextTokenizer;
import dyq.dsearch.repo.DocumentRepo;
import dyq.dsearch.repo.IndexingItemRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class TFIDF {
    @Autowired
    private TextTokenizer textTokenizer;

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private IndexingItemRepo indexingItemRepo;

    public List<Word> TF(String text) {
        List<String> words = textTokenizer.tokenizerForIndex(text);
        BigDecimal total = new BigDecimal(words.size());
        List<Word> tf = new ArrayList<>();
        words.forEach(s -> {
            Word word =
                new Word(s, new BigDecimal(count(words, s)).divide(total, 4, BigDecimal.ROUND_HALF_UP).floatValue());
            if (!tf.contains(word)) {
                tf.add(word);
            }
        });
        tf.sort((o1, o2) -> -Float.compare(o1.getWeight(), o2.getWeight()));
        return tf;
    }

    public List<Word> DF(String text) {
        BigDecimal docSize = BigDecimal.valueOf(documentRepo.count());
        List<String> words = textTokenizer.tokenizerForIndex(text);
        Iterable<IndexingItem> indexes = indexingItemRepo.findAllById(words);

        List<Word> idf = new ArrayList<>();

        words.forEach(s -> {
            long cnt = freq(s, indexes);
            if (cnt >= docSize.longValue()) {
                cnt = docSize.longValue() - 1;
            }
            BigDecimal freq = docSize.divide(BigDecimal.valueOf(cnt + 1), 4, BigDecimal.ROUND_HALF_UP);
            float res = 1.0000F;
            if (freq.longValue() != 1L) {
                res = (float) Math.log(freq.doubleValue());
            }
            Word word = new Word(s, res);
            if (!idf.contains(word)) {
                idf.add(word);
            }
        });
        idf.sort((o1, o2) -> -Float.compare(o1.getWeight(), o2.getWeight()));
        return idf;
    }

    public List<Word> TFDF(String text) {
        // idf
        BigDecimal docSize = BigDecimal.valueOf(documentRepo.count());
        List<String> words = textTokenizer.tokenizerForIndex(text);
        Iterable<IndexingItem> indexes = indexingItemRepo.findAllById(words);

        List<Word> idf = new ArrayList<>();

        words.forEach(s -> {
            long cnt = freq(s, indexes);
            if (cnt >= docSize.longValue()) {
                cnt = docSize.longValue() - 1;
            }
            BigDecimal freq = docSize.divide(BigDecimal.valueOf(cnt + 1), 4, BigDecimal.ROUND_HALF_UP);
            float res = 1.0000F;
            if (freq.longValue() != 1L) {
                res = (float) Math.log(freq.doubleValue());
            }
            Word word = new Word(s, res);
            if (!idf.contains(word)) {
                idf.add(word);
            }
        });
        // tf
        BigDecimal total = new BigDecimal(words.size());
        List<Word> tf = new ArrayList<>();
        words.forEach(s -> {
            Word word =
                new Word(s, new BigDecimal(count(words, s)).divide(total, 4, BigDecimal.ROUND_HALF_UP).floatValue());
            if (!tf.contains(word)) {
                tf.add(word);
            }
        });

        List<Word> weight = new ArrayList<>();
        tf.forEach(new Consumer<Word>() {
            @Override
            public void accept(Word word) {
                float res = BigDecimal.valueOf(word.getWeight())
                    .multiply(BigDecimal.valueOf(idf.get(idf.indexOf(word)).getWeight()))
                    .floatValue();
                weight.add(new Word(word.getName(), res));
            }
        });
        return weight;
    }

    private int freq(String s, Iterable<IndexingItem> indexes) {
        final int[] cnt = {0};
        indexes.forEach(new Consumer<IndexingItem>() {
            @Override
            public void accept(IndexingItem indexingItem) {
                if (indexingItem.getWord().equals(s)) {
                    cnt[0] = indexingItem.getUuids().size();
                }
            }
        });
        return cnt[0];
    }

    private int count(List<String> words, String s) {
        return (int) words.stream().filter(s::equals).count();
    }
}
