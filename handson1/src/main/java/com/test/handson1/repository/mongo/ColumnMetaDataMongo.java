package com.test.handson1.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.test.handson1.entities.mongo.ColumnMetadataMongoEntity;

@Repository
public interface ColumnMetaDataMongo extends MongoRepository<ColumnMetadataMongoEntity, Integer> {

	List<ColumnMetadataMongoEntity> findByTableMetadataId(Integer tableMetadataId);

}
