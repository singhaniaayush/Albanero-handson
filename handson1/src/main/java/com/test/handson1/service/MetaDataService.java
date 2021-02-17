package com.test.handson1.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.test.handson1.config.MultiTenancy;
import com.test.handson1.constants.ApplicationConstants;
import com.test.handson1.entities.ColumnMetadata;
import com.test.handson1.entities.TableMetadata;
import com.test.handson1.entities.mongo.ColumnMetadataMongoEntity;
import com.test.handson1.entities.mongo.TableMetadataMongoEntity;
import com.test.handson1.mapper.MetadataMapper;
import com.test.handson1.model.ColumnMetadataDTO;
import com.test.handson1.model.MetaData;
import com.test.handson1.model.TableMetadataDTO;
import com.test.handson1.repository.ColumnMetaDataRepo;
import com.test.handson1.repository.TableMetaDataRepo;
import com.test.handson1.repository.mongo.ColumnMetaDataMongo;
import com.test.handson1.repository.mongo.TableMetaDataMongo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MetaDataService {

	@Autowired
	private TableMetaDataRepo tableMetaDataRepo;

	@Autowired
	private ColumnMetaDataRepo columnMetaDataRepo;

	@Autowired
	private MetadataMapper mapper;

	@Autowired
	private TableMetaDataMongo tenantMetaDataMongo;

	@Autowired
	private ColumnMetaDataMongo columnMetaDataMongo;

	@Value("${json.path}")
	private String jsonFilePath;

	public MetaData retrieveMetaDataForId(Integer id) {
		log.info("retrieveMetaDataForId: {}", id);
		String tenantId = MDC.get(ApplicationConstants.TENANT_ID);

		if (StringUtils.hasText(tenantId) && tenantId.equalsIgnoreCase("all")) {

			Map<String, DataSource> dataSources = MultiTenancy.getInstance().getTenants();

			dataSources.entrySet().stream().parallel().forEach(entry -> {

				try (Connection connection = entry.getValue().getConnection()) {

					PreparedStatement preparedStatement = connection.prepareStatement(""); 
					
				} catch (Exception e) {
					log.error("retrieveMetaDataForId|Key: {}|Exception: {}", entry.getKey(), e.getMessage());
				}

			});

			return loadForMongo(id);

		} else if (StringUtils.hasText(tenantId) && tenantId.equalsIgnoreCase("mongo")) {

			return loadForMongo(id);

		} else {
			return loadForSql(id);
		}

	}

	private MetaData loadForMongo(Integer id) {
		Optional<TableMetadataMongoEntity> tableMetadata = tenantMetaDataMongo.findById(id);

		if (tableMetadata.isPresent()) {
			List<ColumnMetadataMongoEntity> columnMetadatas = columnMetaDataMongo.findByTableMetadataId(id);

			String tenantName = MDC.get(ApplicationConstants.TENANT_ID);

			tableMetadata.get().setTenantName(tenantName);
			columnMetadatas.stream().parallel().filter(Objects::nonNull)
					.forEach(data -> data.setTenantName(tenantName));

			tenantMetaDataMongo.save(tableMetadata.get());
			columnMetaDataMongo.saveAll(columnMetadatas);

			MetaData metaData = new MetaData();
			metaData.setColumnMetadatas(mapper.convertColumnMongoToDTO(columnMetadatas));
			metaData.setTableMetadata(mapper.convertTableMongoToDTO(tableMetadata.get()));

			return metaData;
		}
		return null;
	}

	private MetaData loadForSql(Integer id) {
		Optional<TableMetadata> tableMetadata = tableMetaDataRepo.findById(id);

		if (tableMetadata.isPresent()) {
			List<ColumnMetadata> columnMetadatas = columnMetaDataRepo.findByTableMetadataId(id);

			TableMetadataDTO table = mapper.convertTableToDTO(tableMetadata.get());
			List<ColumnMetadataDTO> colums = mapper.convertColumnToDTO(columnMetadatas);

			MetaData metaData = new MetaData();
			metaData.setColumnMetadatas(colums);
			metaData.setTableMetadata(table);

			TableMetadataMongoEntity tableMongoEntity = mapper.convertTableToMongo(tableMetadata.get());
			List<ColumnMetadataMongoEntity> columnMongoEntities = mapper.convertColumnToDTOMongo(columnMetadatas);

			String tenantName = MDC.get(ApplicationConstants.TENANT_ID);
			tableMongoEntity.setTenantName(tenantName);

			columnMongoEntities.stream().parallel().filter(Objects::nonNull)
					.forEach(data -> data.setTenantName(tenantName));

			tenantMetaDataMongo.save(tableMongoEntity);
			columnMetaDataMongo.saveAll(columnMongoEntities);

			return metaData;
		}
		return null;
	}

}
