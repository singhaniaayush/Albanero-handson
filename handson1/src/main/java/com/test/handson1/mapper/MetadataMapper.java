package com.test.handson1.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.test.handson1.entities.ColumnMetadata;
import com.test.handson1.entities.TableMetadata;
import com.test.handson1.entities.mongo.ColumnMetadataMongoEntity;
import com.test.handson1.entities.mongo.TableMetadataMongoEntity;
import com.test.handson1.model.ColumnMetadataDTO;
import com.test.handson1.model.TableMetadataDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MetadataMapper {

	/////////////////////////////
	TableMetadataDTO convertTableToDTO(TableMetadata tableMetadata);

//	@Mapping(source = "id", target = "tableMetadataId")
	TableMetadataMongoEntity convertTableToMongo(TableMetadata tableMetadata);

	TableMetadataDTO convertTableMongoToDTO(TableMetadataMongoEntity tableMetadata);

	////////////////////////////////////////
//	@Mapping(source = "id", target = "columnMetadataId")
	ColumnMetadataMongoEntity convertColumnToMongo(ColumnMetadata columnMetadata);

	ColumnMetadataDTO convertColumnToDTO(ColumnMetadata columnMetadata);

	ColumnMetadataDTO convertColumnMongoToDTO(ColumnMetadataMongoEntity tableMetadata);

	//////////////////////////////
	List<ColumnMetadataDTO> convertColumnToDTO(List<ColumnMetadata> columnMetadata);

	List<ColumnMetadataMongoEntity> convertColumnToDTOMongo(List<ColumnMetadata> columnMetadata);

	List<ColumnMetadataDTO> convertColumnMongoToDTO(List<ColumnMetadataMongoEntity> tableMetadata);

}
