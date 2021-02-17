
package com.test.handson1.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.beanutils.BeanUtils;
import org.omg.CORBA.portable.ApplicationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultSetMapper {

	private ResultSetMapper() {
	}

	public static <T> List<T> mapResultSetTolist(ResultSet rs, Class<T> outputClass) throws ApplicationException {
		List<T> outputList = null;
		try {
			// make sure resultset is not null
			if (rs != null) {
				// check if outputClass has 'Entity' annotation
				if (outputClass.isAnnotationPresent(Entity.class)) {
					// get the resultset metadata
					ResultSetMetaData rsmd = rs.getMetaData();
					// get all the attributes of outputClass
					Field[] fields = outputClass.getDeclaredFields();
					outputList = resultSetIterator(rs, outputClass, outputList, rsmd, fields);

				} else {
					log.error("ResultSetMapper|mapResultSetTolist|Not an entity");
				}
			} else {
				log.info("ResultSetMapper|mapResultSetTolist|Exit");
				return outputList;
			}
		} catch (IllegalAccessException e) {
			log.error("ResultSetMapper|mapResultSetTolist|IllegalAccessException| {}", e.getMessage());
			e.printStackTrace();

		} catch (SQLException e) {
			log.error("ResultSetMapper|mapResultSetTolist|SQLException| {}", e.getMessage());
			e.printStackTrace();

		} catch (InstantiationException e) {
			log.error("ResultSetMapper|mapResultSetTolist|InstantiationException| {}", e.getMessage());
			e.printStackTrace();

		} catch (InvocationTargetException e) {
			log.error("ResultSetMapper|mapResultSetTolist|InvocationTargetException| {}", e.getMessage());
			e.printStackTrace();
		}
		return outputList;
	}

	private static <T> List<T> resultSetIterator(ResultSet rs, Class<T> outputClass, List<T> outputList,
			ResultSetMetaData rsmd, Field[] fields)
			throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException {
		while (rs.next()) {
			T bean = outputClass.newInstance();
			for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
				// getting the SQL column name
				String columnName = rsmd.getColumnName(_iterator + 1);
				// reading the value of the SQL column
				Object columnValue = rs.getObject(_iterator + 1);
				// iterating over outputClass attributes to check if any attribute has 'Column'
				// annotation with matching 'name' value
				fieldIteration(fields, bean, columnName, columnValue);
			}
			if (outputList == null) {
				outputList = new ArrayList<>();
			}
			outputList.add(bean);
		}
		return outputList;
	}

	private static <T> void fieldIteration(Field[] fields, T bean, String columnName, Object columnValue)
			throws IllegalAccessException, InvocationTargetException {
		for (Field field : fields) {
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				if (column.name().equalsIgnoreCase(columnName) && columnValue != null) {
					BeanUtils.setProperty(bean, field.getName(), columnValue);
					break;
				}
			}
		}
	}

	/**
	 * Map result set to object.
	 *
	 * @param <T>         the generic type
	 * @param rs          the rs
	 * @param outputClass the output class
	 * @return the t
	 * @throws ApplicationException sql exception
	 */
	public static <T> T mapResultSetToObject(ResultSet rs, Class<T> outputClass) throws ApplicationException {
		List<T> data = ResultSetMapper.mapResultSetTolist(rs, outputClass);
		return (data == null ? null : data.get(0));
	}

}
