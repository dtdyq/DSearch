/*
 * 
 */

package dyq.dsearch.service;

import dyq.dsearch.model.Document;
import dyq.dsearch.model.IndexingItem;
import dyq.dsearch.nlp.normalize.SimpleNormalizer;
import dyq.dsearch.repo.DocumentRepo;
import dyq.dsearch.repo.IndexingItemRepo;
import dyq.dsearch.nlp.tokenize.TextTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class IndexingService {
    private static final Logger LOG = LoggerFactory.getLogger(IndexingService.class);
    @Autowired
    private IndexingItemRepo indexingItemRepo;
    @Autowired
    private DocumentRepo documentRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TextTokenizer textTokenizer;

    @Autowired
    private SimpleNormalizer simpleNormalizer;

    public void indexDocuments(List<Document> documents) {

        documents.forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                document.setContent(simpleNormalizer.normalize(document.getContent()));
                String uuid = UUID.randomUUID().toString();
                document.setUuid(uuid);
                documentRepo.save(document);
                List<String> words = textTokenizer.tokenizerForIndex(document.getContent())
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
                LOG.info("tokens {} for doc{}", words, uuid);
                words.forEach(word -> {
                    IndexingItem item = indexingItemRepo.findById(word).orElse(new IndexingItem());
                    item.getUuids().add(uuid);
                    item.setWord(word);
                    LOG.info("save indexing item:{}", item);
                    indexingItemRepo.save(item);
                });
            }
        });
    }
}
