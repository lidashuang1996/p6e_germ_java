package com.p6e.germ.common.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring Boot Redis 缓存的配置
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisConfig {

    /**
     * Redis 节点
     */
    private Node node;

    /**
     * Redis 是否开起 SSL 连接
     * 默认为 关闭 SSL 连接
     */
    private boolean ssl = false;

    /**
     * Redis 客户端名称
     * 默认为 P6E_REDIS_CLIENT
     */
    private String clientName = "P6E_REDIS_CLIENT";

    /**
     * Redis 命令超时时间
     * 默认为 5S
     */
    private Duration timeout = Duration.ofSeconds(5L);

    /**
     * Redis 关闭超时时间
     * 默认为 100S
     */
    private Duration shutdownTimeout = Duration.ofSeconds(100L);

    /**
     * Redis 连接方式
     */
    private Type type = Type.LETTUCE;

    /**
     * Redis 线程池
     */
    private final Pool pool = new Pool();

    /**
     * Redis 多数据源配置
     */
    private final Map<String, Node> source = new HashMap<>();



    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getShutdownTimeout() {
        return shutdownTimeout;
    }

    public void setShutdownTimeout(Duration shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Pool getPool() {
        return pool;
    }

    public Map<String, Node> getSource() {
        return source;
    }

    /**
     * Redis 的连接类型
     */
    public enum Type {
        /**
         * Redis 采用 JEDIS 的连接方式
         */
        JEDIS,
        /**
         * Redis 采用 LETTUCE 的连接方式
         */
        LETTUCE
    }

    /**
     * Redis 的线程池
     */
    public static class Pool {

        /**
         * 最大空闲数
         */
        private int maxIdle = 8;

        /**
         * 最小空闲数
         */
        private int minIdle = 0;

        /**
         * 最大连接数
         */
        private int maxActive = 8;

        /**
         * 最大等待时间
         */
        private Duration maxWait = Duration.ofMillis(-1L);

        /**
         * 操作运行之间的时间间隔
         */
        private Duration timeBetweenEvictionRuns = Duration.ofMillis(-1L);

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return this.minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return this.maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public Duration getMaxWait() {
            return this.maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }

        public Duration getTimeBetweenEvictionRuns() {
            return this.timeBetweenEvictionRuns;
        }

        public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
            this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
        }
    }

    /**
     * 节点
     */
    public static class Node {
        /**
         * Redis 库的序号
         * 默认为 0
         */
        private int database = 0;

        /**
         * Redis 连接端口
         * 默认为 6379
         */
        private int port = 6379;

        /**
         * Redis 连接密码
         * 默认为 null
         */
        private String password;

        /**
         * Redis 连接地址
         * 默认为 localhost
         */
        private String host = "localhost";

        /**
         * Redis Cluster 连接方式
         * 默认为 null
         */
        private Cluster cluster;

        /**
         * Redis Sentinel 连接方式
         * 默认为 null
         */
        private Sentinel sentinel;

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Cluster getCluster() {
            return cluster;
        }

        public void setCluster(Cluster cluster) {
            this.cluster = cluster;
        }

        public Sentinel getSentinel() {
            return sentinel;
        }

        public void setSentinel(Sentinel sentinel) {
            this.sentinel = sentinel;
        }

        /**
         * Redis Sentinel
         */
        public static class Sentinel {

            /**
             * Redis Sentinel Master 节点
             * 默认为 null
             */
            private String master;

            /**
             * Redis Sentinel 连接密码
             * 默认为 null
             */
            private String password;

            /**
             * Redis Sentinel Nodes 节点
             * 默认为 null
             */
            private List<String> nodes;

            public String getMaster() {
                return master;
            }

            public void setMaster(String master) {
                this.master = master;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public List<String> getNodes() {
                return nodes;
            }

            public void setNodes(List<String> nodes) {
                this.nodes = nodes;
            }
        }

        /**
         * Redis Cluster
         */
        public static class Cluster {

            /**
             * Redis Cluster Nodes 节点
             * 默认为 null
             */
            private List<String> nodes;

            /**
             * Redis Cluster 最大重定向的次数
             * 默认为 5
             */
            private Integer maxRedirects = 5;

            public List<String> getNodes() {
                return this.nodes;
            }

            public void setNodes(List<String> nodes) {
                this.nodes = nodes;
            }

            public Integer getMaxRedirects() {
                return this.maxRedirects;
            }

            public void setMaxRedirects(Integer maxRedirects) {
                this.maxRedirects = maxRedirects;
            }
        }
    }



}
