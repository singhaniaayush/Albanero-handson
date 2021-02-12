package com.test.handson1.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "table_meta_data")
@Data
public class TableMetadata implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "table_meta_data_id")
	private Integer id;

	@Column(name = "schema_name")
	private String schemaName;

	@Column(name = "table_name")
	private String tableName;

	@Column(name = "is_delete")
	private Boolean isDelete;

	@Column(name = "is_active")
	private Boolean isActive;

}
