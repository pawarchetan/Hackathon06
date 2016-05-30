package com.clairvoyant.hackathon.dao;

import org.springframework.stereotype.Repository;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@Repository
public interface DatabaseMetadataDao {
    DatabaseMetaData getDatabaseMetadata(String connectionURL, String userName, String password);
    ResultSet getTableMetaData(DatabaseMetaData databaseMetaData);
}
