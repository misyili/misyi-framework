package com.misyi.framework.starter.mongodb.support;


import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * 响应式Mongodb工具类
 *
 * @author licong
 * @date 2020-07-08 15:14 PM
 */
public class ReactiveMongodbOperationsFactory {


    public static ReactiveMongoTemplate reactiveMongoTemplate;

    public static ReactiveMongoTemplate getReactiveMongoTemplate() {
        return reactiveMongoTemplate;
    }

    public static void setReactiveMongoTemplate(ReactiveMongoTemplate reactiveMongoTemplate) {
        ReactiveMongodbOperationsFactory.reactiveMongoTemplate = reactiveMongoTemplate;
    }
}
