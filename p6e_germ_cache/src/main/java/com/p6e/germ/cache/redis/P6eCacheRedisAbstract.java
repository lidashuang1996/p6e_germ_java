package com.p6e.germ.cache.redis;

import com.p6e.germ.cache.config.P6eCacheRedisConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eCacheRedisAbstract implements IP6eCacheRedis {

    /** 最大的重试创建连接的次数 */
    private static final int MAX_RETRY = 3;
    /** 默认的名称 */
    private static final String DEFAULT_NAME = "$DEFAULT";

    /** 配置文件 */
    private final P6eCacheRedisConfig config;
    /** Jedis */
    private P6eBaseCacheRedisConnector p6eJedisConnector;
    /** Lettuce */
    private P6eBaseCacheRedisConnector p6eLettuceConnector;

    /**
     * 保存链接对象的 map 集合
     */
    private final Map<String, StringRedisTemplate> stringRedisTemplateMap = new HashMap<>();

    /**
     * 构造方法
     * 写入配置文件，初始化连接器
     * @param config 配置文件对象
     */
    public P6eCacheRedisAbstract(P6eCacheRedisConfig config) {
        this.config = config;
        this.p6eJedisConnector = new P6eCacheRedisLettuceConnector(config);
        this.p6eLettuceConnector = new P6eCacheRedisLettuceConnector(config);
    }

    /**
     * 写入数据对象 JedisConnector
     * @param connector 连接器对象
     */
    public void setP6eJedisConnector(P6eBaseCacheRedisConnector connector) {
        p6eJedisConnector = connector;
    }

    /**
     * 写入数据对象 LettuceConnector
     * @param connector 连接器对象
     */
    public void setP6eLettuceConnector(P6eBaseCacheRedisConnector connector) {
        p6eLettuceConnector = connector;
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
        Assert.notNull(source, this.getClass() + " ==> source is null.");
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

    /**
     * 获取 redis 数据对象
     * @param source 数据源名称
     * @param count 重试次数
     * @return redis 数据对象列表
     */
    private Map<String, StringRedisTemplate> getStringRedisTemplate(String source, int count) {
        // 判断不能为空
        Assert.notNull(source, this.getClass() + " ==> source is null.");
        source = source.toUpperCase();
        final Map<String, StringRedisTemplate> map = new HashMap<>(3);
        for (String key : stringRedisTemplateMap.keySet()) {
            if (key.equals(source) || key.startsWith(source.toUpperCase() + ".")) {
                map.put(key, stringRedisTemplateMap.get(key));
            }
        }
        // 查找不到对应的数据源
        if (map.size() == 0 && count < MAX_RETRY) {
            this.createStringRedisTemplate(source);
            return this.getStringRedisTemplate(source, ++count);
        } else {
            return map;
        }
    }

    /**
     * 创建 redis 数据连接
     * @param source 数据源名称
     */
    private synchronized void createStringRedisTemplate(String source) {
        // 判断不能为空
        Assert.notNull(source, this.getClass() + " ==> source is null.");
        source = source.toUpperCase();
        // 检查是否存在这样的链接配置
        if (DEFAULT_NAME.equals(source) || config.getSource().get(source) != null) {
            // 再次检查一遍对象是否创建
            for (String key : stringRedisTemplateMap.keySet()) {
                if (key.equals(source) || key.startsWith(source + ".")) {
                    return;
                }
            }
            // 连接并写入数据
            switch (config.getType()) {
                case JEDIS:
                    stringRedisTemplateMap.putAll(p6eJedisConnector.connect(DEFAULT_NAME.equals(source) ? null : source));
                    break;
                case LETTUCE:
                    stringRedisTemplateMap.putAll(p6eLettuceConnector.connect(DEFAULT_NAME.equals(source) ? null : source));
                    break;
                default:
                    throw new RuntimeException(this.getClass() + " ==> type is null.");
            }
        }
    }
}
