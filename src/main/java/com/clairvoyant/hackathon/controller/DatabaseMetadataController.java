package com.clairvoyant.hackathon.controller;

import com.clairvoyant.hackathon.service.DatabaseMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@RestController
@RequestMapping("/databasemetadata")
public class DatabaseMetadataController {
    @Autowired
    private DatabaseMetadataService databaseMetadataService;

    @POST
    @RequestMapping("/tables")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // In the parameters  we get the details of variables mentioned in the method
    public Map<String,Map<String,Boolean>> getAllTables(@RequestBody Map< String, String > inputParameters) throws Exception {
        String connectionURL = inputParameters.get("connectionURL");
        String userName = inputParameters.get("userName");
        String password = inputParameters.get("password");
        String databaseType = inputParameters.get("databaseType");
        return databaseMetadataService.getDatabaseMetadata(connectionURL, userName, password, databaseType);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTestString() throws Exception {
        return "GET call Tested!!!!!!!";
    }
}
