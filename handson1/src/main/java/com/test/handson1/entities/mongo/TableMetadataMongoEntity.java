package com.test.handson1.entities.mongo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class TableMetadataMongoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

//	private Integer tableMetadataId;

	private String schemaName;

	private String tableName;

	private Boolean isDelete;

	private Boolean isActive;

	private String tenantName;
}
