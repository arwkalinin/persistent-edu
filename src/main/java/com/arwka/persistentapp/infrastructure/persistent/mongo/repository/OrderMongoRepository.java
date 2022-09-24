package com.arwka.persistentapp.infrastructure.persistent.mongo.repository;

import com.arwka.persistentapp.infrastructure.persistent.mongo.entity.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMongoRepository extends MongoRepository<OrderDocument, String> {
}
