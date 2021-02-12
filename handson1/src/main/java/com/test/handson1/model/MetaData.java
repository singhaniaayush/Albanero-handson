package com.test.handson1.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MetaData implements Serializable {

	private static final long serialVersionUID = 1L;

	private TableMetadataDTO tableMetadata;

	private List<ColumnMetadataDTO> columnMetadatas;

}
