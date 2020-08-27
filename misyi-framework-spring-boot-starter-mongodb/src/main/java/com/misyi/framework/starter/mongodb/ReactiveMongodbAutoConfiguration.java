package com.misyi.framework.starter.mongodb;

import com.misyi.framework.starter.mongodb.support.ReactiveMongodbOperationsFactory;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.connection.*;
import com.mongodb.internal.connection.ServerAddressHelper;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.mongodb.connection.ClusterConnectionMode.MULTIPLE;
import static com.mongodb.connection.ClusterConnectionMode.SINGLE;

/**
 * 响应式Mongodb自动配置
 *
 * @author licong
 * @date 2018-08-09 18:08 PM
 */
@Configuration
@ConditionalOnClass({ MongoClient.class, ReactiveMongoTemplate.class })
@EnableConfigurationProperties({ MongoProperties.class, MongodbOptionProperties.class })
@AutoConfigureBefore({ MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class })
public class ReactiveMongodbAutoConfiguration {

    private MongoProperties properties;
    private MongodbOptionProperties optionProperties;
    private MongoClient mongoClient;

    public ReactiveMongodbAutoConfiguration(MongoProperties properties, MongodbOptionProperties optionProperties) {
        this.properties = properties;
        this.optionProperties = optionProperties;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoDatabaseFactory());
    }

    @Bean
    public ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory() {
        return new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), getDatabaseName());
    }

    @Bean
    public MongoClient reactiveMongoClient() {
        this.mongoClient = MongoClients.create(mongoClientSettings());
        return this.mongoClient;
    }

    @Bean
    public Initialization4Utils Initialization4Utils(ReactiveMongoTemplate reactiveMongoTemplate) {
        ReactiveMongodbOperationsFactory.setReactiveMongoTemplate(reactiveMongoTemplate);
        return new Initialization4Utils();
    }

    /**
     * 此类专门用于做初始化
     */
    public static class Initialization4Utils {

    }

    @Bean
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applicationName(optionProperties.getApplicationName())
                .credential(MongoCredential.createCredential(
                        properties.getUsername(), getDatabaseName(), properties.getPassword()))
                .readPreference(optionProperties.getReadPreference())
                .writeConcern(optionProperties.getWriteConcern())
                .readConcern(optionProperties.getReadConcern())
                .clusterSettings(buildClusterSettings(properties, optionProperties))
                .connectionPoolSettings(buildConnectionPoolSettings(optionProperties))
                .serverSettings(buildServerSettings(optionProperties))
                .socketSettings(buildSocketSettings(optionProperties))
                .sslSettings(buildSslSettings(optionProperties))
                .heartbeatSocketSettings(buildHeartbeatSocketSettings(optionProperties))
                .build();
    }

    @PreDestroy
    public void close() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
        }
    }

    private String getDatabaseName() {
        return (this.properties.getAuthenticationDatabase() != null)
                ? this.properties.getAuthenticationDatabase()
                : this.properties.getDatabase();
    }

    private ClusterSettings buildClusterSettings(MongoProperties properties,
                                                 MongodbOptionProperties optionProperties) {
        boolean multiHost = getHostList(properties.getHost()).size() > 1 ;
        return ClusterSettings.builder()
                .description(optionProperties.getDescription())
                .hosts(getServerAddressList(properties.getHost(), properties.getPort()))
                .mode(multiHost ? MULTIPLE : SINGLE)
                .requiredClusterType(getRequiredClusterType(multiHost, optionProperties.getReplicaSetName()))
                .requiredReplicaSetName(optionProperties.getReplicaSetName())
                .serverSelectionTimeout(optionProperties.getServerSelectionTimeout(), TimeUnit.MILLISECONDS)
                .localThreshold(optionProperties.getLocalThreshold(), TimeUnit.MILLISECONDS)
                .maxWaitQueueSize(optionProperties.getMaxConnectionPerHost() * optionProperties.getThreadsAllowedToBlockForConnectionMultiplier())
                .build();
    }

    private List<ServerAddress> getServerAddressList(String hosts, Integer port){
        return getHostList(hosts).parallelStream()
                .map(host -> ServerAddressHelper.createServerAddress(host, port))
                .collect(Collectors.toList());
    }

    private List<String> getHostList(String hosts){
        return Arrays.stream(hosts.split(",")).collect(Collectors.toList());
    }

    private ClusterType getRequiredClusterType(boolean multiHost, String replicaSetName){
        return multiHost ? StringUtils.isNotBlank(replicaSetName) ? ClusterType.REPLICA_SET :
                ClusterType.SHARDED : ClusterType.STANDALONE;
    }

    private ConnectionPoolSettings buildConnectionPoolSettings(MongodbOptionProperties optionProperties) {
        return ConnectionPoolSettings.builder()
                .minSize(optionProperties.getMinConnectionPerHost())
                .maxSize(optionProperties.getMaxConnectionPerHost())
                .maxWaitTime(optionProperties.getMaxWaitTime(), TimeUnit.MILLISECONDS)
                .maxWaitQueueSize(optionProperties.getThreadsAllowedToBlockForConnectionMultiplier() * optionProperties.getMaxConnectionPerHost())
                .maxConnectionLifeTime(optionProperties.getMaxConnectionLifeTime(), TimeUnit.MILLISECONDS)
                .maxConnectionIdleTime(optionProperties.getMaxConnectionIdleTime(), TimeUnit.MILLISECONDS)
                .maintenanceFrequency(optionProperties.getMinHeartbeatFrequency(), TimeUnit.MILLISECONDS)
                .maintenanceInitialDelay(optionProperties.getHeartbeatFrequency(), TimeUnit.MILLISECONDS)
                .build();
    }

    private ServerSettings buildServerSettings(MongodbOptionProperties optionProperties) {
        return ServerSettings.builder()
                .minHeartbeatFrequency(optionProperties.getMinHeartbeatFrequency(), TimeUnit.MILLISECONDS)
                .heartbeatFrequency(optionProperties.getHeartbeatFrequency(), TimeUnit.MILLISECONDS)
                .build();
    }


    private SocketSettings buildSocketSettings(MongodbOptionProperties optionProperties) {
        return SocketSettings.builder()
                .connectTimeout(optionProperties.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(optionProperties.getSocketTimeout(), TimeUnit.MILLISECONDS)
                .build();
    }

    private SslSettings buildSslSettings(MongodbOptionProperties optionProperties) {
        return SslSettings.builder()
                .enabled(optionProperties.getSslEnabled())
                .invalidHostNameAllowed(optionProperties.getSslInvalidHostNameAllowed())
                .build();
    }

    private SocketSettings buildHeartbeatSocketSettings(MongodbOptionProperties optionProperties) {
        return SocketSettings.builder()
                .connectTimeout(optionProperties.getHeartbeatConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(optionProperties.getHeartbeatSocketTimeout(), TimeUnit.MILLISECONDS)
                .build();
    }
}
