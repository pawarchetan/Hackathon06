package com.clairvoyant.hackathon.dao;

import com.clairvoyant.hackathon.service.LoadSessionFactoryService;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Log4j2
public class DatabaseMetadataDaoImpl implements DatabaseMetadataDao {

    private static final String TABLE_CODE = "TABLE";

    @Autowired
    LoadSessionFactoryService loadSessionFactoryService;

    /**
     * DatabaseMetaData interface provides methods to get meta data of a database such as database product name,
     * database product version, driver name, name of total number of tables, name of total number of views etc.
     * REFERENCES :- https://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html
     */
    @Override
    public DatabaseMetaData getDatabaseMetadata(String connectionURL, String userName, String password, String databaseType) {
        SessionFactory sessionFactory = loadSessionFactoryService.getSessionFactory(connectionURL, userName, password, databaseType);
        SessionImpl sessionImpl = (SessionImpl) sessionFactory.openSession();
        try {
            Connection connection = sessionImpl.connection();
            return connection.getMetaData();
        } catch (SQLException e) {
            log.error("Exception while getting database metadata......... !!!!!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
     * Retrieves a description of the tables available in the given catalog.
     * REFERENCES :- https://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html
     */
    @Override
    public ResultSet getTableMetaData(DatabaseMetaData databaseMetaData) {
        try {
            return databaseMetaData.getTables(null, null, null, new String[]{TABLE_CODE});
        } catch (SQLException e) {
            log.error("Exception while getting table metadata........!!!!!!");
            e.printStackTrace();
        }
        return null;
    }
}
