package com.arwka.persistentapp.infrastructure.persistent.mongo.repository;

import com.arwka.persistentapp.infrastructure.persistent.mongo.entity.ProductDocument;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMongoRepository extends MongoRepository<ProductDocument, String> {
  List<ProductDocument> findAllByDescriptionContains(String word);
  ProductDocument findByName(String name);
  ProductDocument deleteByName(String name);

  ProductDocument findProductEntityByName(String name);

}
