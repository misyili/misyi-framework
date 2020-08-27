package com.misyi.framework.starter.mongodb;

import com.misyi.framework.starter.mongodb.support.MongodbRepository;
import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Mongodb仓库自动配置
 *
 * @author licong
 * @date 2020-07-21 6:26 PM
 */
@Configuration
@ConditionalOnClass(MongoClient.class)
@AutoConfigureAfter(MongodbOptionAutoConfiguration.class)
public class MongodbRepositoryAutoConfiguration {

    @Bean
    public Initialization4Utils Initialization4Utils(MongoOperations mongoOperations) {
        MongodbRepository.setMongoOperations(mongoOperations);
        return new Initialization4Utils();
    }

    /**
     * 此类专门用于做初始化
     */
    public static class Initialization4Utils {

    }
}
