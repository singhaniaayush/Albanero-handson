package com.test.handson1.config;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.MDC;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import com.test.handson1.constants.ApplicationConstants;

public class MultiTenancy extends AbstractRoutingDataSource implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final ConcurrentHashMap<String, DataSource> tenants = new ConcurrentHashMap<>();

	protected void addTenant(String tenantName, DataSource dataSource) {
		tenants.put(tenantName, dataSource);
	}

	protected ConcurrentHashMap<String, DataSource> getTenants() {
		return tenants;
	}

	@Override
	protected DataSource determineTargetDataSource() {
		String lookupKey = (String) determineCurrentLookupKey();

		if (StringUtils.hasText(lookupKey)) {
			return tenants.get(lookupKey);
		}
		return tenants.get("0");
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return MDC.get(ApplicationConstants.TENANT_ID);
	}

}
