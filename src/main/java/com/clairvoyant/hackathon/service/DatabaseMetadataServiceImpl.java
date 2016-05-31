package com.clairvoyant.hackathon.service;

import com.clairvoyant.hackathon.dao.DatabaseMetadataDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class DatabaseMetadataServiceImpl implements DatabaseMetadataService {

    // Get property information from properties file by using the property name
    private static final String SENSITIVE_COLUMN_NAMES = "sensitive.column.name";

    @Autowired
    private DatabaseMetadataDao databaseMetadataDao;

    @Autowired
    private Environment environment;

    @Override
    public Map<String, Map<String, Boolean>> getDatabaseMetadata(String connectionURL, String userName, String password, String databaseType) {
        Map<String, Map<String, Boolean>> tableColumnMap = null;
        DatabaseMetaData databaseMetadata = databaseMetadataDao.getDatabaseMetadata(connectionURL, userName, password, databaseType);

        if(databaseMetadata != null) {
            ResultSet resultSet = databaseMetadataDao.getTableMetaData(databaseMetadata);
            tableColumnMap = getTableColumnMappings(databaseMetadata, resultSet);
        }
        return tableColumnMap;
    }

    //
    private Map<String, Map<String, Boolean>> getTableColumnMappings(DatabaseMetaData databaseMetadata, ResultSet resultSet){
        Map<String, Map<String, Boolean>> tableColumnMap = new HashMap<>();
        List<String> sensitiveColumnNames = Arrays.asList(environment.getProperty(SENSITIVE_COLUMN_NAMES, String[].class));
        log.info("Sensitive column names :" + sensitiveColumnNames);
        try {
            while(resultSet.next()) {
                String tableName = resultSet.getString(3);

                // Get all the columns of the table mentioned in the method for the selected database
                ResultSet columns = databaseMetadata.getColumns(null, null, tableName, null);
                // Store the status of column as sensitive or not by mentioning name and true/false flag in the below map
                Map<String, Boolean> columnMap = new HashMap<>();
                while(columns.next()) {
                    String columnName = columns.getString(4);
                    if(sensitiveColumnNames.contains(columnName) || sensitiveColumnNames.contains(columnName) || sensitiveColumnNames.contains(columnName)) {
                        columnMap.put(columnName, true);
                    } else {
                        columnMap.put(columnName, false);
                    }
                }
                // Map the column map into the tableColumn map with its table name
                tableColumnMap.put(tableName, columnMap);
            }
        } catch (SQLException e) {
            log.error("Exception while getting table column mappings.......... !!!!!!");
            e.printStackTrace();
        }
        return tableColumnMap;
    }
}
