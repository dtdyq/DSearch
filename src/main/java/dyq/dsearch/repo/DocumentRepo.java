/*
 * 
 */

package dyq.dsearch.repo;

import dyq.dsearch.model.Document;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepo extends MongoRepository<Document,String> { }
