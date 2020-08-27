package com.misyi.framework.starter.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Mongodb可选配置自动配置
 *
 * @author licong
 * @since 2020/8/18 6:27 下午
 */
@Configuration
@ConditionalOnClass(MongoClient.class)
@EnableConfigurationProperties(MongodbOptionProperties.class)
@ConditionalOnMissingBean(type = "org.springframework.data.mongodb.MongoDbFactory")
public class MongodbOptionAutoConfiguration {

    @Bean
    @ConditionalOnClass(MongoClient.class)
    public MongoClientOptions mongoClientOptions(MongodbOptionProperties optionProperties) {
        if (optionProperties == null) {
            return new MongoClientOptions.Builder().build();
        }

        return new MongoClientOptions.Builder()
                .description(optionProperties.getDescription())
                .applicationName(optionProperties.getApplicationName())
                .readPreference(optionProperties.getReadPreference())
                .writeConcern(optionProperties.getWriteConcern())
                .readConcern(optionProperties.getReadConcern())
                .minConnectionsPerHost(optionProperties.getMinConnectionPerHost())
                .connectionsPerHost(optionProperties.getMaxConnectionPerHost())
                .threadsAllowedToBlockForConnectionMultiplier(optionProperties.getThreadsAllowedToBlockForConnectionMultiplier())
                .serverSelectionTimeout(optionProperties.getServerSelectionTimeout())
                .maxWaitTime(optionProperties.getMaxWaitTime())
                .maxConnectionIdleTime(optionProperties.getMaxConnectionIdleTime())
                .maxConnectionLifeTime(optionProperties.getMaxConnectionLifeTime())
                .connectTimeout(optionProperties.getConnectTimeout())
                .socketTimeout(optionProperties.getSocketTimeout())
                .socketKeepAlive(optionProperties.getSocketKeepAlive())
                .sslEnabled(optionProperties.getSslEnabled())
                .sslInvalidHostNameAllowed(optionProperties.getSslInvalidHostNameAllowed())
                .alwaysUseMBeans(optionProperties.getAlwaysUseMBeans())
                .heartbeatFrequency(optionProperties.getHeartbeatFrequency())
                .minConnectionsPerHost(optionProperties.getMinConnectionPerHost())
                .heartbeatConnectTimeout(optionProperties.getHeartbeatConnectTimeout())
                .heartbeatSocketTimeout(optionProperties.getSocketTimeout())
                .localThreshold(optionProperties.getLocalThreshold())
                .build();
    }

}
