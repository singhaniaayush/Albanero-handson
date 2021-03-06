package com.test.handson1.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TableMetadataDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String schemaName;

	private String tableName;

	private Boolean isDelete;

	private Boolean isActive;

}
