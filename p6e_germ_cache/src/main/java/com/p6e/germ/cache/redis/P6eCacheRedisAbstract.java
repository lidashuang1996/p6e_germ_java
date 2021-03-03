package com.p6e.germ.cache.redis;

import com.p6e.germ.common.config.P6eCacheRedisConfig;
import com.p6e.germ.common.config.P6eConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis 缓存的实现抽象类
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eCacheRedisAbstract implements IP6eCacheRedis {

    /** 最大的重试创建连接的次数 */
    private static final int MAX_RETRY = 3;
    /** 默认的名称 */
    private static final String DEFAULT_NAME = "$DEFAULT";
    /** 保存连接器对象的集合 */
    private static final Map<String, StringRedisTemplate> REDIS_TEMPLATE_CACHE_MAP = new HashMap<>();

    /** 配置文件 */
    private static P6eCacheRedisConfig CONFIG =
            P6eConfig.isInit() ? P6eConfig.get().getCache().getRedis() : new P6eCacheRedisConfig();

    /** Jedis 连接器 */
    private static P6eCacheRedisConnector P6E_JEDIS_CONNECTOR = new P6eCacheRedisJedisConnector(CONFIG);
    /** Lettuce 连接器 */
    private static P6eCacheRedisConnector P6E_LETTUCE_CONNECTOR = new P6eCacheRedisLettuceConnector(CONFIG);

    /**
     * 写入配置文件对象
     * @param config 配置文件对象
     */
    public static void setConfig(P6eCacheRedisConfig config) {
        CONFIG = config;
        P6E_JEDIS_CONNECTOR = new P6eCacheRedisJedisConnector(CONFIG);
        P6E_LETTUCE_CONNECTOR = new P6eCacheRedisLettuceConnector(CONFIG);
    }

    /**
     * 写入数据对象 JedisConnector
     * @param connector 连接器对象
     */
    public static void setJedisConnector(P6eCacheRedisConnector connector) {
        P6E_JEDIS_CONNECTOR = connector;
    }

    /**
     * 写入数据对象 LettuceConnector
     * @param connector 连接器对象
     */
    public static void setLettuceConnector(P6eCacheRedisConnector connector) {
        P6E_LETTUCE_CONNECTOR = connector;
    }

    /**
     * 获取 redis 数据对象
     * @param source 数据源名称
     * @param count 重试次数
     * @return redis 数据对象列表
     */
    public static Map<String, StringRedisTemplate> getStringRedisTemplate(String source, int count) {
        // 判断不能为空
        Assert.notNull(source, P6eCacheRedisAbstract.class + " ==> get redis source is null.");
        source = source.toUpperCase();
        final Map<String, StringRedisTemplate> map = new HashMap<>(3);
        for (final String key : REDIS_TEMPLATE_CACHE_MAP.keySet()) {
            if (key.equals(source) || key.startsWith(source.toUpperCase() + ".")) {
                map.put(key, REDIS_TEMPLATE_CACHE_MAP.get(key));
            }
        }
        // 查找不到对应的数据源
        if (map.size() == 0 && count < MAX_RETRY) {
            createStringRedisTemplate(source);
            return getStringRedisTemplate(source, ++count);
        } else {
            return map;
        }
    }

    /**
     * 创建 redis 数据连接
     * @param source 数据源名称
     */
    private static synchronized void createStringRedisTemplate(String source) {
        // 判断不能为空
        Assert.notNull(source, P6eCacheRedisAbstract.class + " ==> create redis source is null.");
        source = source.toUpperCase();
        // 检查是否存在这样的连接配置
        if (DEFAULT_NAME.equals(source) || CONFIG.getSource().get(source) != null) {
            // 再次检查一遍对象是否创建
            for (final String key : REDIS_TEMPLATE_CACHE_MAP.keySet()) {
                if (key.equals(source) || key.startsWith(source + ".")) {
                    return;
                }
            }
            // 连接并写入数据
            switch (CONFIG.getType()) {
                case JEDIS:
                    REDIS_TEMPLATE_CACHE_MAP.putAll(P6E_JEDIS_CONNECTOR.connect(DEFAULT_NAME.equals(source) ? null : source));
                    break;
                case LETTUCE:
                    REDIS_TEMPLATE_CACHE_MAP.putAll(P6E_LETTUCE_CONNECTOR.connect(DEFAULT_NAME.equals(source) ? null : source));
                    break;
                default:
                    throw new RuntimeException(P6eCacheRedisAbstract.class + " ==> create redis source type is null.");
            }
        }
    }

    /**
     * 根据默认数据源获取 redis 数据连接
     * @return redis 数据连接对象
     */
    public StringRedisTemplate getStringRedisTemplate() {
        return getStringRedisTemplate(DEFAULT_NAME);
    }

    /**
     * 根据数据源获取 redis 数据连接
     * @param source 数据源名称
     * @return redis 数据连接对象
     */
    @Override
    public StringRedisTemplate getStringRedisTemplate(String source) {
        // 判断不能为空
        Assert.notNull(source, this.getClass() + " ==> get redis source is null.");
        final Map<String, StringRedisTemplate> map = getStringRedisTemplate(source, 0);
        if (map.size() == 0) {
            throw new RuntimeException(this.getClass() + " the corresponding data source cannot be queried.");
        } else if (map.size() == 1) {
            return map.values().toArray(new StringRedisTemplate[0])[0];
        } else {
            throw new RuntimeException(this.getClass() + " there are multiple data sources, which data source is returned ?"
                    + "\n ============================"
                    + map.keySet()
                    + "\n ============================");
        }
    }
}
