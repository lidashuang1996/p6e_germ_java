package com.p6e.germ.oauth2.infrastructure.task;

import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import com.p6e.germ.oauth2.model.db.P6eOauth2ClientDb;
import com.p6e.germ.oauth2.infrastructure.repository.mapper.P6eOauth2ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 由于 CLIENT 比较少，调用频繁
 * 可以缓存一下到 CACHE 中间键
 * @author lidashuang
 * @version 1.0
 */
@Component
@EnableScheduling
public class P6eCacheDatabaseTask {

    /**
     * 每次操作读取的长度
     */
    private static final int DB_SIZE = 100;

    /**
     * 注入日志操作对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eCacheDatabaseTask.class);

    /**
     * 注入服务
     */
    @Resource
    private P6eOauth2ClientMapper p6eClientMapper;

    /**
     * 每隔 15 分钟执行，同步一次缓存数据
     */
    @Scheduled(initialDelay = 10 * 1000, fixedRate = 900 * 1000)
    public void execute() {
        LOGGER.info("start operation db to cache.");
        int page = 0;
        List<P6eOauth2ClientDb> p6eClientDbList;
        // 创建查询的对象
        final P6eOauth2ClientDb p6eClientDb = new P6eOauth2ClientDb();
        do {
            try {
                // 设置页码和长度
                p6eClientDb.setPage(page);
                p6eClientDb.setSize(DB_SIZE);
                // 查询全部的数据
                p6eClientDbList = p6eClientMapper.queryAll(p6eClientDb);
                LOGGER.info("operation db to cache ==> [ "
                        + p6eClientDb.getStart() + " ~ " + (p6eClientDb.getEnd()) + " ]");
                // 查询到的数据写入到缓存中
                for (final P6eOauth2ClientDb db : p6eClientDbList) {
                    try {
                        final String content = P6eJsonUtil.toJson(db);
                        P6eCache.client.setDbKey(db.getKey(), content);
                        P6eCache.client.setDbId(String.valueOf(db.getId()), content);
                        LOGGER.info("operation db to cache ==> [ id: " + db.getId() + " - key: " + db.getKey() + " ] success.");
                    } catch (Exception e) {
                        LOGGER.info("operation db to cache ==> [ id: " + db.getId() + " - key: " + db.getKey() + " ] error.");
                        LOGGER.error("operation db to cache error ==> " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("operation db to cache error ==> " + e.getMessage());
                break;
            }
        } while (p6eClientDbList.size() >= DB_SIZE);
        LOGGER.info("end operation db to cache.");
    }

}
