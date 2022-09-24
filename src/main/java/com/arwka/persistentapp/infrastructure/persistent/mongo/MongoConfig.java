package com.arwka.persistentapp.infrastructure.persistent.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@EntityScan(basePackages = {"com/arwka/persistentapp/infrastructure/persistent/mongo/entity"})
public class MongoConfig {

  @Bean
  public MongoClient mongo() {
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder().build();
    return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongo(), "mongo-db");
  }


}
