package com.lyubendimitrov.gifapp.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
@PropertySource("app.properties")
public class DataConfig {

    @Value("${giflib.entity.package}")
    private String entitiesPackage;

    @Value("${giflib.db.driver}")
    private String dataSourceDriverName;

    @Value("${giflib.db.url}")
    private String dataSourceUrl;

    @Value("${giflib.db.username}")
    private String dataSourceUserName;

    @Value("${giflib.db.password}")
    private String dataSourcePassword;

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean() {
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setConfigLocation(config);
        sessionFactoryBean.setPackagesToScan(entitiesPackage);
        sessionFactoryBean.setDataSource(dataSource());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        // Driver class name
        dataSource.setDriverClassName(dataSourceDriverName);

        // Set Url
        dataSource.setUrl(dataSourceUrl);

        // Set Username and Password
        dataSource.setUsername(dataSourceUserName);
        dataSource.setPassword(dataSourcePassword);

        return dataSource;

    }
}
