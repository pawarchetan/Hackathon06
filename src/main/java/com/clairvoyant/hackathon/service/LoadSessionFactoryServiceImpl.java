package com.clairvoyant.hackathon.service;

import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Log4j2
public class LoadSessionFactoryServiceImpl implements LoadSessionFactoryService {
    private static final String PACKAGE_TO_SCAN = "com.clairvoyant.hackathon.model";  // Where is model ?
    private static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
    private static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
    private static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
    private static final String HIBERNATE_CONNECTION_DRIVER = "hibernate.connection.driver_class";
    private static final String JDBC_DRIVER_NAME = "jdbc.driver";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";// TO which dialect does it point ?
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

    @Autowired
    private Environment environment;

    @Override
    public SessionFactory getSessionFactory(String connectionURL, String userName, String password, String databaseType) {
        return new AnnotationConfiguration()
                .addPackage(PACKAGE_TO_SCAN)
                .addProperties(getHibernateProperties(connectionURL, userName, password, databaseType))
                .addAnnotatedClass(Object.class)
                .buildSessionFactory();
    }

//    private DataSource dataSource(String connectionURL, String userName, String password) {
//        log.info("Configuring the Data source.........");
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(getDriverClassName("sqlServer"));
//        dataSource.setUrl(connectionURL);
//        dataSource.setUsername(userName);
//        dataSource.setPassword(password);
//        return dataSource;
//    }

    private Properties getHibernateProperties(String connectionURL, String username, String password, String databaseType) {
        log.info("Reading the Hibernate properties file........");
        Properties properties = new Properties();
        properties.setProperty(HIBERNATE_CONNECTION_URL, connectionURL);
        properties.setProperty(HIBERNATE_CONNECTION_USERNAME, username);
        properties.setProperty(HIBERNATE_CONNECTION_PASSWORD, password);
        properties.setProperty(HIBERNATE_DIALECT, getHibernateDialect(databaseType));
        properties.setProperty(HIBERNATE_CONNECTION_DRIVER, getDriverClassName(databaseType));
        properties.setProperty(HIBERNATE_SHOW_SQL, environment.getRequiredProperty(HIBERNATE_SHOW_SQL));
        properties.setProperty(HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(HIBERNATE_FORMAT_SQL));
        return properties;
    }

    private String getHibernateDialect(String typeOfDatabase){
        return environment.getRequiredProperty(HIBERNATE_DIALECT + "." + typeOfDatabase);
    }

    private String getDriverClassName(String typeOfDatabase) {
        return environment.getRequiredProperty(JDBC_DRIVER_NAME + "." + typeOfDatabase);
    }

//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        log.info("Configuring the Transaction manager........");
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sessionFactory);
//        return txManager;
//    }
}
