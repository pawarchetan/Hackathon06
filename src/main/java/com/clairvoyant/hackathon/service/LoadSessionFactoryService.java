package com.clairvoyant.hackathon.service;

import org.hibernate.SessionFactory;

public interface LoadSessionFactoryService {
    SessionFactory getSessionFactory(String connectionURL, String userName, String password, String databaseType);
}
