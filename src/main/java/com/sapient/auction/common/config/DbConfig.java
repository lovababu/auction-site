package com.sapient.auction.common.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Database configuration class.
 *
 * Created by dpadal on 11/11/2016.
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
public class DbConfig {

    @Value("${hibernate.hibernateDialect}")
    private String hibernateDialect;
    @Value("${hibernate.showSQL}")
    private String showSql;
    @Value("${hibernate.generateStatistics}")
    private String generateStatistics;

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.sapient.auction.sale.entity", "com.sapient.auction.user.entity");
        sessionFactoryBean.setAnnotatedPackages("com.sapient.auction.sale.entity", "com.sapient.auction.user.entity");
        sessionFactoryBean.setHibernateProperties(getHibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    /**
     * Spring provided H2 Embedded Database.
     * Read the dbscript and initiates the Database with the name H2-Test-DB.
     *
     * @return DataSource
     */
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setName("H2-Sab-DB");
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db-scripts/ddl.sql")
                .addScript("classpath:db-scripts/dml.sql").build();
        return db;
    }


    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager() throws Exception {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(this.sessionFactory());
        return transactionManager;
    }

    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.cache.use_second_level_cache", false);
        properties.put("hibernate.generate_statistics", generateStatistics);
        return properties;
    }
}
