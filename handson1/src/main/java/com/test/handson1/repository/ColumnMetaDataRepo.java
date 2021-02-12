package com.test.handson1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.handson1.entities.ColumnMetadata;

@Repository
public interface ColumnMetaDataRepo extends JpaRepository<ColumnMetadata, Integer> {

	List<ColumnMetadata> findByTableMetadataId(Integer tableMetadataId);

}
