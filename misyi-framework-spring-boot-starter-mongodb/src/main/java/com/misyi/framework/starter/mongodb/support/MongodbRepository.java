package com.misyi.framework.starter.mongodb.support;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.data.util.Pair;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 使用此类必须添加依赖：
 * <code>
 * <dependency>
 *     <groupId>org.mongodb</groupId>
 *     <artifactId>mongodb-driver</artifactId>
 * </dependency>
 * </code>
 *
 * 操作Mongodb仓库类
 *
 * @author licong
 * @date 2020-07-08 15:14 PM
 */
public class MongodbRepository {

    public static MongoOperations mongoOperations;

    private static final List<Class<?>> BASIC_CLASS = Arrays.asList(Byte.class
            , Short.class
            , Integer.class
            , Long.class
            , Float.class
            , Double.class
            , Character.class
            , Boolean.class
            , String.class
            , BigDecimal.class
            , Date.class
    );

    public static MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    public static void setMongoOperations(MongoOperations mongoOperations) {
        MongodbRepository.mongoOperations = mongoOperations;
    }

    /**
     * 保存文档
     *
     * @param document
     * @param <T>
     */
    public static <T> void save(T document) {
        mongoOperations.save(document);
    }

    /**
     * 根据ID删除文档
     *
     * @param id
     * @param <ID>
     */
    public static <ID extends Serializable> void delete(ID id) {
        mongoOperations.remove(id);
    }

    /**
     * 根据条件删除文档
     *
     * @param query
     * @param clazz
     * @param <T>
     */
    public static <T> void delete(Query query, Class<T> clazz) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        mongoOperations.remove(query, clazz);
    }

    /**
     * 根据条件修改一条匹配的数据集合
     *
     * @param query  更新条件
     * @param update 修改的对象
     * @param clazz  文档对象类
     */
    public static <T> UpdateResult updateByClazz(Query query, Update update, Class<T> clazz) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(update, "Update must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        return mongoOperations.updateFirst(query, update, clazz);
    }

    /**
     * 根据条件修改所有匹配的数据集合
     *
     * @param query  更新条件
     * @param update 修改的对象
     * @param clazz  文档对象类
     */
    public static UpdateResult updateAllByClazz(Query query, Update update, Class<?> clazz) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(update, "Update must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        return mongoOperations.updateMulti(query, update, clazz);
    }

    /**
     * 如果没有文档符合更新条件，就会以这个条件和更新文档为基础创建一个新的文档；如果找到了匹配文档，则正常更新
     *
     * @param query  更新条件
     * @param update 修改的对象
     * @param clazz  文档对象类
     * @return
     */
    public static UpdateResult upsert(Query query, Update update, Class<?> clazz) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(update, "Update must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        return mongoOperations.upsert(query, update, clazz);
    }

    /**
     * 根据ID查找文档
     *
     * @param id
     * @param clazz
     * @param <T>
     * @param <ID>
     * @return
     */
    public static <T, ID extends Serializable> T findById(ID id, Class<T> clazz) {
        Assert.notNull(id, "ID must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        return mongoOperations.findById(id, clazz);
    }

    /**
     * 分页查询
     *
     * @param query
     * @param pageable
     * @param <T>
     * @return
     */
    public static <T> Page<T> findByPage(final Query query, final Pageable pageable, final Class<T> clazz) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        long total = mongoOperations.count(query, clazz);
        query.with(pageable);
        List<T> documentList = mongoOperations.find(query, clazz);
        return PageableExecutionUtils.getPage(documentList, pageable, () -> total);
    }

    /**
     * 分页查询
     *
     * @param query
     * @param pageable
     * @param clazz
     * @param collectionName
     * @param <T>
     * @return
     */
    public static <T> Page<T> findByPage(final Query query, final Pageable pageable, final Class<T> clazz, final String collectionName) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        Assert.notNull(collectionName, "collectionName must not be null!");
        long total = mongoOperations.count(query, collectionName);
        query.with(pageable);
        List<T> documentList = mongoOperations.find(query, clazz, collectionName);
        return PageableExecutionUtils.getPage(documentList, pageable, () -> total);
    }

    /**
     * 根据条件查询列表
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> findListByClazz(Query query, Class<T> clazz) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        return mongoOperations.find(query, clazz);
    }

    /**
     * 根据条件查询列表
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> findListByClazz(Query query, Class<T> clazz, final String collectionName) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        Assert.notNull(clazz, "collectionName must not be null!");
        return mongoOperations.find(query, clazz, collectionName);
    }

    /**
     * 根据条件查询匹配的一条
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T findByClazz(Query query, Class<T> clazz) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        return mongoOperations.findOne(query, clazz);
    }

    /**
     * 制定集合名称并根据条件查询匹配的一条
     *
     * @param query
     * @param clazz
     * @param collectionName
     * @param <T>
     * @return
     */
    public static <T> T findByName(Query query, Class<T> clazz, String collectionName) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(collectionName, "Class<T> must not be null!");
        return mongoOperations.findOne(query, clazz, collectionName);
    }

    /**
     * 根据条件计算文档数量
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> long countByClazz(Query query, Class<T> clazz) {
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(clazz, "Class<T> must not be null!");
        return mongoOperations.count(query, clazz);
    }

    /**
     * 批量插入文档
     *
     * @param clazz
     * @param documents
     * @param <T>
     */
    public static <T> BulkWriteResult bulkInsert(Class<T> clazz, List<T> documents) {
        Assert.notNull(clazz, "Class<T> must not be null!");
        Assert.notNull(documents, "documents must not be null!");
        return mongoOperations.bulkOps(BulkOperations.BulkMode.UNORDERED, clazz).insert(documents).execute();
    }

    /**
     * 批量更新第一个匹配的文档
     *
     * @param clazz
     * @param updates
     * @param <T>
     * @return
     */
    public static <T> BulkWriteResult bulkUpdateOne(Class<T> clazz, List<Pair<Query, Update>> updates) {
        Assert.notNull(clazz, "Class<T> must not be null!");
        Assert.notNull(updates, "updates must not be null!");
        return mongoOperations.bulkOps(BulkOperations.BulkMode.UNORDERED, clazz).updateOne(updates).execute();
    }

    /**
     * 批量更新所有匹配的文档
     *
     * @param clazz
     * @param updates
     * @param <T>
     * @return
     */
    public static <T> BulkWriteResult bulkUpdateMulti(Class<T> clazz, List<Pair<Query, Update>> updates) {
        Assert.notNull(clazz, "Class<T> must not be null!");
        Assert.notNull(updates, "updates must not be null!");
        return mongoOperations.bulkOps(BulkOperations.BulkMode.UNORDERED, clazz).updateMulti(updates).execute();
    }


    /**
     * 判断是否是List类型
     * @param field
     * @return
     */
    private static boolean checkArrayType(Field field) {
        return java.util.List.class.isAssignableFrom(field.getType()) || field.getClass().isArray();
    }

    /**
     * 判断是否是Map类型
     * @param field
     * @return
     */
    private static boolean checkMapType(Field field) {
        return java.util.Map.class.isAssignableFrom(field.getType());
    }


    /**
     * 判断是否是需要全量更新的数据
     * @param field
     * @return
     */
    private static boolean checkBasicType(Field field) {
        Class<?> type = field.getType();
        return field.getDeclaringClass().isPrimitive() || BASIC_CLASS.contains(type);
    }

    /**
     * 新增或更新对象的所有字段，包含null值
     * @param query 查询
     * @param domain 实体
     * @param <T>
     * @return
     */
    public static <T> int upsertAllFields(Query query, T domain) {
        try {
            Update update = new Update();
            Class<?> domainClass = domain.getClass();
            Field[] declaredFields = domainClass.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object value = field.get(domain);
                update.set(field.getName(), value);
            }
            UpdateResult updateResult = upsert(query, update, domainClass);
            return (int)updateResult.getModifiedCount();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("MongoDB update data is Exception!");
        }
    }

    /**
     * 新增或更新嵌套对象，不包含null值
     * @param query 查询
     * @param domain 实体
     * @param <T>
     * @return
     */
    public static <T> int upsertNestField(Query query, T domain) {
        try {
            Update update = getNestUpdate(domain);
            UpdateResult updateResult = upsert(query, update, domain.getClass());
            return (int)updateResult.getModifiedCount();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("MongoDB update data is Exception!");
        }
    }


    /**
     * 更新指定对象中所有的非null字段，包含嵌套对象
     * @param domain
     * @return
     */
    private static Update getNestUpdate(Object domain) {
        return iterationField("", domain.getClass(), domain, new Update());
    }

    /**
     * 迭代类的所有字段，如果是基础类型，进行update设值
     * @param prefix 前缀
     * @param clazz 需要迭代的对象字节码
     * @param obj 需要迭代的对象
     * @param update mongodb更新类
     * @return
     */
    private static Update iterationField(String prefix, Class<?> clazz, Object obj, Update update) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(obj);
                if("serialVersionUID".equals(name) || value == null) {
                    continue;
                }
                if(StringUtils.isNotBlank(prefix)) {
                    name = prefix + "." + name;
                }
                if(checkBasicType(field)) {
                    update.set(name, value);
                } else if(checkArrayType(field)){
                    // List 处理, 暂时全量更新, 目前无法解决下标变为属性问题
                    update.set(name, value);
                } else if(checkMapType(field)){
                    // Map处理, 暂时全量更新
                    update.set(name, value);
                } else {
                    iterationField(name, field.getType(), value, update);
                }
            }
            return update;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
