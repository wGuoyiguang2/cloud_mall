package com.cibnvideo.config.datasource;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.admin.master")
    public DataSource MasterDataSource() {
        return new DataSource();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.admin.slave00")
    public DataSource SlaveDataSource00() {
        return new DataSource();
    }

    @Bean
    public DynamicDataSource dataSource(@Qualifier("MasterDataSource") DataSource masterDataSource,
                                        @Qualifier("SlaveDataSource00") DataSource slaveDataSource00) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DatabaseType.master, masterDataSource);
        targetDataSources.put(DatabaseType.slave00, slaveDataSource00);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(masterDataSource);// 默认的datasource设置为myTestDbDataSource
        return dataSource;
    }
}
