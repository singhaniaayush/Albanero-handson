package com.test.handson1.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.test.handson1.entities.mongo.TableMetadataMongoEntity;

@Repository
public interface TableMetaDataMongo extends MongoRepository<TableMetadataMongoEntity, Integer> {

}
