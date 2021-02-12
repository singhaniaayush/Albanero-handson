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
@Table(name = "column_meta_data")
@Data
public class ColumnMetadata implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "column_meta_data_id")
	private Integer id;

	@Column(name = "table_meta_data_id")
	private Integer tableMetadataId;

	@Column(name = "column_name")
	private String schemaName;

	@Column(name = "column_type")
	private String tableName;

	@Column(name = "is_delete")
	private Boolean isDelete;

	@Column(name = "is_active")
	private Boolean isActive;

}
