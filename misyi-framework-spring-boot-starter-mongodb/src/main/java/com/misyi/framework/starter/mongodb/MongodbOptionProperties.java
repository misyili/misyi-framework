package com.misyi.framework.starter.mongodb;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mongodb可选参数配置属性
 *
 * @author licong
 * @date 2018-05-21 15:52
 */
@ConfigurationProperties(prefix = MongodbOptionProperties.MONGODB_OPTION_PREFIX)
public class MongodbOptionProperties {

    public static final String MONGODB_OPTION_PREFIX = "spring.data.mongodb.option";

    /**
     * MongoClient的描述，可以用日志或者JMX
     * 默认值为null
     */
    private String description;
    /**
     * 使用MongoClient的应用的逻辑名称
     * 客户端可以使用应用程序名称来标识服务器的应用程序，
     * 以用于服务器日志，慢查询日志和配置文件集合。
     * 默认值为null
     */
    private String applicationName;
    /**
     * 读偏好
     * ReadPreference 主要控制客户端 Driver 从复制集的哪个节点读取数据，这个特性可方便的实现读写分离、就近读取等策略。
     * primary 只从 primary 节点读数据，这个是默认设置
     * primaryPreferred 优先从 primary 读取，primary 不可服务，从 secondary 读
     * secondary 只从 secondary 节点读数据
     * secondaryPreferred 优先从 secondary 读取，没有 secondary 成员时，从 primary 读取
     * nearest 根据网络距离就近读取
     * 默认值：secondaryPreferred
     */
    private ReadPreference readPreference = ReadPreference.secondaryPreferred();
    /**
     * 写安全
     * w: <number>，数据写入到number个节点才向用客户端确认
     *
     * {w: 0} 对客户端的写入不需要发送任何确认，适用于性能要求高，但不关注正确性的场景
     * {w: 1} 默认的writeConcern，数据写入到Primary就向客户端发送确认
     * {w: "majority"} 数据写入到副本集大多数成员后向客户端发送确认，适用于对数据安全性要求比较高的场景，该选项会降低写入性能
     * j: <boolean> ，写入操作的journal持久化后才向客户端确认
     *
     * 默认为"{j: false}，如果要求Primary写入持久化了才向客户端确认，则指定该选项为true
     * wTimeoutMS: <milliseconds>，写入超时时间，仅w的值大于1时有效。
     *
     * 当指定{w: }时，数据需要成功写入number个节点才算成功，如果写入过程中有节点故障，可能导致这个条件一直不能满足，
     * 从而一直不能向客户端发送确认结果，针对这种情况，客户端可设置wTimeoutMS选项来指定超时时间，当写入过程持续超过该时间仍未结束，则认为写入失败。
     *
     * 默认值：ACKNOWLEDGED
     */
    private WriteConcern writeConcern = WriteConcern.ACKNOWLEDGED;
    /**
     * 读安全
     * readConcern 决定到某个读取数据时，能读到什么样的数据。
     * local 能读取任意数据，这个是默认设置
     * majority 只能读取到『成功写入到大多数节点的数据』
     */
    private ReadConcern readConcern = ReadConcern.DEFAULT;
    /**
     * 每个主机的最小连接数
     * 此MongoClient实例每个主机的最小连接数。
     * 这些连接在闲置时将保存在池中，池将随时保证它至少包含这个最小值。
     * 默认值为0
     */
    private Integer minConnectionPerHost = 0;
    /**
     * 每个主机的最大连接数
     * 此MongoClient实例的每个主机允许的最大连接数。
     * 闲置时，这些连接将保留在游泳池中。
     * 一旦池被耗尽，任何需要连接的操作都将阻止等待可用连接。
     * 默认值为100
     */
    private Integer maxConnectionPerHost = 100;
    /**
     * 每个连接允许阻塞的线程数量的乘数
     * 这个乘数乘以connectionsPerHost设置，给出可能正在等待连接从池中变为可用的最大线程数。 所有进来的线程将立即得到一个异常。
     * 例如，如果connectionsPerHost为10，并且threadsAllowedToBlockForConnectionMultiplier为5，则最多50个线程可以等待连接。
     */
    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;
    /**
     * 服务器选择超时时间（单位：毫秒）
     * 驱动程序在抛出异常之前等待服务器选择成功的时间。
     * 默认值是30,000。值为0意味着如果没有服务器可用，它将立即超时。负值意味着无限期地等待。
     */
    private Integer serverSelectionTimeout = 30000;
    /**
     * 线程阻塞等待连接的最长时间（单位：毫秒）
     * 线程等待连接变为可用的最长等待时间
     * 默认值是120,000。 值为0意味着它不会等待。 负值意味着无限期地等待。
     */
    private Integer maxWaitTime = 120000;
    /**
     * 池里面连接的最长空闲时间（单位：毫秒）
     * 零值表示对空闲时间没有限制。 已超过其空闲时间的池化连接将被关闭并在必要时通过新连接进行替换。
     * 默认值为0
     */
    private Integer maxConnectionIdleTime = 0;
    /**
     * 池里面连接的最长活动时间（单位：毫秒）
     * 零值表示对寿命没有限制。 超过其使用期限的连接池将被关闭并在必要时通过新连接进行更换。
     * 默认值为0
     */
    private Integer maxConnectionLifeTime = 0;
    /**
     * 连接超时时间（单位：毫秒）
     * 值为0意味着没有超时，它仅在建立新连接时使用
     * 默认值为10000
     */
    private Integer connectTimeout = 10000;
    /**
     * Socket超时时间（单位：毫秒）
     * 它用于I/O Socket 字读写操作
     * 默认值为0
     */
    private Integer socketTimeout = 0;
    /**
     * Socket是否保持存活
     * 此标志控制Socket保持活动功能，通过防火墙保持连接的连通性
     */
    private Boolean socketKeepAlive = false;
    /**
     * 是否开启SSL
     */
    private Boolean sslEnabled = false;
    /**
     * 在开启SSL的情况下是否允许非法主机名
     */
    private Boolean sslInvalidHostNameAllowed = false;
    /**
     * 是否一直使用MBean
     * 获取驱动程序注册的JMX bean是否应始终为MBean，而不管该VM是否为Java 6或更高版本。
     * 如果为false，则如果VM为Java 6或更高版本，则驱动程序将使用MXBeans，如果VM为Java 5，则使用MBean。
     * 默认值位false
     */
    private Boolean alwaysUseMBeans = false;
    /**
     * 心跳频率时间（单位：毫秒）
     * 这是驱动程序尝试确定群集中每个服务器当前状态的频率
     * 默认值是10000
     */
    private Integer heartbeatFrequency = 10000;
    /**
     * 最小心跳频率时间（单位：毫秒）
     * 如果驱动程序必须经常重新检查服务器的可用性，它至少会等待自上次检查以来的这段时间，以避免浪费时间。
     * 默认值是500
     */
    private Integer minHeartbeatFrequency = 500;
    /**
     * 心跳连接超时时间（单位：毫秒）
     * 用于连接群集心跳的连接超时时间
     * 默认值是20000
     */
    private Integer heartbeatConnectTimeout = 20000;
    /**
     * 心跳Socket超时时间（单位：毫秒）
     * 用于连接群集心跳的的Socket超时时间
     * 默认值是20000
     */
    private Integer heartbeatSocketTimeout = 20000;
    /**
     * 在多个Server的情况下选择哪台服务器的ping时间本地阈值
     * 在多个MongoDB服务器之间选择发送请求时，MongoClient只会将该请求发送到ping时间小于或等于ping时间加上本地阈值的服务器的服务器。
     * 例如，假设客户端在读取首选项为ReadPreference.secondary（）时选择发送查询的服务器，
     * 并且有三个次级服务器server1，server2和server3，其ping时间为10 ，15和16毫秒。
     * 使用5毫秒的本地阈值，客户端将发送查询到server1或server2（在两者之间随机选择）。
     * 默认值位15
     */
    private Integer localThreshold = 15;
    /**
     * 分片名称
     */
    private String replicaSetName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public ReadPreference getReadPreference() {
        return readPreference;
    }

    public void setReadPreference(ReadPreference readPreference) {
        this.readPreference = readPreference;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public ReadConcern getReadConcern() {
        return readConcern;
    }

    public void setReadConcern(ReadConcern readConcern) {
        this.readConcern = readConcern;
    }

    public Integer getMinConnectionPerHost() {
        return minConnectionPerHost;
    }

    public void setMinConnectionPerHost(Integer minConnectionPerHost) {
        this.minConnectionPerHost = minConnectionPerHost;
    }

    public Integer getMaxConnectionPerHost() {
        return maxConnectionPerHost;
    }

    public void setMaxConnectionPerHost(Integer maxConnectionPerHost) {
        this.maxConnectionPerHost = maxConnectionPerHost;
    }

    public Integer getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(Integer threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public Integer getServerSelectionTimeout() {
        return serverSelectionTimeout;
    }

    public void setServerSelectionTimeout(Integer serverSelectionTimeout) {
        this.serverSelectionTimeout = serverSelectionTimeout;
    }

    public Integer getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Integer maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Integer getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(Integer maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }

    public Integer getMaxConnectionLifeTime() {
        return maxConnectionLifeTime;
    }

    public void setMaxConnectionLifeTime(Integer maxConnectionLifeTime) {
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Boolean getSocketKeepAlive() {
        return socketKeepAlive;
    }

    public void setSocketKeepAlive(Boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    public Boolean getSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(Boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public Boolean getSslInvalidHostNameAllowed() {
        return sslInvalidHostNameAllowed;
    }

    public void setSslInvalidHostNameAllowed(Boolean sslInvalidHostNameAllowed) {
        this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
    }

    public Boolean getAlwaysUseMBeans() {
        return alwaysUseMBeans;
    }

    public void setAlwaysUseMBeans(Boolean alwaysUseMBeans) {
        this.alwaysUseMBeans = alwaysUseMBeans;
    }

    public Integer getHeartbeatFrequency() {
        return heartbeatFrequency;
    }

    public void setHeartbeatFrequency(Integer heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }

    public Integer getMinHeartbeatFrequency() {
        return minHeartbeatFrequency;
    }

    public void setMinHeartbeatFrequency(Integer minHeartbeatFrequency) {
        this.minHeartbeatFrequency = minHeartbeatFrequency;
    }

    public Integer getHeartbeatConnectTimeout() {
        return heartbeatConnectTimeout;
    }

    public void setHeartbeatConnectTimeout(Integer heartbeatConnectTimeout) {
        this.heartbeatConnectTimeout = heartbeatConnectTimeout;
    }

    public Integer getHeartbeatSocketTimeout() {
        return heartbeatSocketTimeout;
    }

    public void setHeartbeatSocketTimeout(Integer heartbeatSocketTimeout) {
        this.heartbeatSocketTimeout = heartbeatSocketTimeout;
    }

    public Integer getLocalThreshold() {
        return localThreshold;
    }

    public void setLocalThreshold(Integer localThreshold) {
        this.localThreshold = localThreshold;
    }

    public String getReplicaSetName() {
        return replicaSetName;
    }

    public void setReplicaSetName(String replicaSetName) {
        this.replicaSetName = replicaSetName;
    }

    @Override
    public String toString() {
        return "MongodbOptionProperties{" +
                "description='" + description + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", readPreference=" + readPreference +
                ", writeConcern=" + writeConcern +
                ", readConcern=" + readConcern +
                ", minConnectionPerHost=" + minConnectionPerHost +
                ", maxConnectionPerHost=" + maxConnectionPerHost +
                ", threadsAllowedToBlockForConnectionMultiplier=" + threadsAllowedToBlockForConnectionMultiplier +
                ", serverSelectionTimeout=" + serverSelectionTimeout +
                ", maxWaitTime=" + maxWaitTime +
                ", maxConnectionIdleTime=" + maxConnectionIdleTime +
                ", maxConnectionLifeTime=" + maxConnectionLifeTime +
                ", connectTimeout=" + connectTimeout +
                ", socketTimeout=" + socketTimeout +
                ", socketKeepAlive=" + socketKeepAlive +
                ", sslEnabled=" + sslEnabled +
                ", sslInvalidHostNameAllowed=" + sslInvalidHostNameAllowed +
                ", alwaysUseMBeans=" + alwaysUseMBeans +
                ", heartbeatFrequency=" + heartbeatFrequency +
                ", minHeartbeatFrequency=" + minHeartbeatFrequency +
                ", heartbeatConnectTimeout=" + heartbeatConnectTimeout +
                ", heartbeatSocketTimeout=" + heartbeatSocketTimeout +
                ", localThreshold=" + localThreshold +
                ", replicaSetName=" + replicaSetName +
                '}';
    }
}
