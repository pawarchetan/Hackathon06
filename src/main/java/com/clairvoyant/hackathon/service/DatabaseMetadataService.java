package com.clairvoyant.hackathon.service;

import java.util.Map;

public interface DatabaseMetadataService {
    Map<String,Map<String,Boolean>> getDatabaseMetadata(String connectionURL, String userName, String password);
}
