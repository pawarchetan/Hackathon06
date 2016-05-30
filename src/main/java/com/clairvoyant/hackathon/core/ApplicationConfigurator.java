package com.clairvoyant.hackathon.core;

import com.clairvoyant.hackathon.dao.DatabaseMetadataDao;
import com.clairvoyant.hackathon.dao.DatabaseMetadataDaoImpl;
import com.clairvoyant.hackathon.service.DatabaseMetadataService;
import com.clairvoyant.hackathon.service.DatabaseMetadataServiceImpl;
import com.clairvoyant.hackathon.service.LoadSessionFactoryService;
import com.clairvoyant.hackathon.service.LoadSessionFactoryServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@ComponentScan(basePackages = "com.clairvoyant.hackathon")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Log4j2
public class ApplicationConfigurator {

    //TODO
//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        log.info("Configuring the Transaction manager........");
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sessionFactory);
//        return txManager;
//    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        log.info("Configuring the View Resolver............");
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".html");
        resolver.setViewClass(JstlView.class);
        log.info("Configuration Successful.....!!!!!!!!");
        return resolver;
    }

    @Bean
    public LoadSessionFactoryService loadSessionFactoryService(){
        return new LoadSessionFactoryServiceImpl();
    }

    @Bean
    public DatabaseMetadataService databaseMetadataService(){
        return new DatabaseMetadataServiceImpl();
    }

    @Bean
    public DatabaseMetadataDao databaseMetadataDao(){
        return new DatabaseMetadataDaoImpl();
    }
}
