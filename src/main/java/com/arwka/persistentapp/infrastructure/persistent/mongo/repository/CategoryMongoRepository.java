package com.arwka.persistentapp.infrastructure.persistent.mongo.repository;

import com.arwka.persistentapp.infrastructure.persistent.mongo.entity.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMongoRepository extends MongoRepository<CategoryDocument, String> {
}
