/*
 * 
 */

package dyq.dsearch.repo;

import dyq.dsearch.model.IndexingItem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IndexingItemRepo extends MongoRepository<IndexingItem,String> { }
