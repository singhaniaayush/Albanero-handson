package com.test.handson1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.handson1.entities.TableMetadata;

@Repository
public interface TableMetaDataRepo extends JpaRepository<TableMetadata, Integer>{

}
