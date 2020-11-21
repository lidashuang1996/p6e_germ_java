package com.p6e.germ.oauth2.infrastructure.task;

import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheClient;
import com.p6e.germ.oauth2.infrastructure.repository.db.P6eClientDb;
import com.p6e.germ.oauth2.infrastructure.repository.mapper.P6eClientMapper;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 由于 CLIENT 比较少，调用频繁
 * 可以缓存一下到 CACHE 中间健中
 * @author lidashuang
 * @version 1.0
 */
@Component
public class CacheDatabaseTask {

    /**
     * 每次操作读取的长度
     */
    private static final int DB_SIZE = 100;

    /**
     * 注入日志操作对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheDatabaseTask.class);

    /**
     * 注入服务
     */
    @Resource
    private IP6eCacheClient p6eCacheClient;

    @Resource
    private P6eClientMapper p6eClientMapper;

    /**
     * 每隔 15 分钟执行，同步一次缓存数据
     */
    @Scheduled(initialDelay = 10 * 1000, fixedRate = 900 * 1000)
    public void execute() {
        LOGGER.info("start operation db to cache.");
        int page = 0;
        List<P6eClientDb> p6eClientDbList;
        final P6eClientDb p6eClientDb = new P6eClientDb();
        do {
            try {
                p6eClientDb.setSize(DB_SIZE);
                p6eClientDb.setPage(page * DB_SIZE);
                p6eClientDbList = p6eClientMapper.queryAll(p6eClientDb);
                LOGGER.info("operation db to cache ==> [ "
                        + p6eClientDb.getPage() + " ~ "
                        + (p6eClientDb.getPage() + p6eClientDb.getSize()) + " ]");
                for (P6eClientDb db : p6eClientDbList) {
                    try {
                        final String content = JsonUtil.toJson(db);
                        p6eCacheClient.setDbId(String.valueOf(db.getId()), content);
                        p6eCacheClient.setDbKey(db.getKey(), content);
                        LOGGER.info("operation db to cache ==> [ id: " + db.getId() + " - key: " + db.getKey() + " ] success.");
                    } catch (Exception e) {
                        LOGGER.info("operation db to cache ==> [ id: " + db.getId() + " - key: " + db.getKey() + " ] error.");
                        LOGGER.error("operation db to cache error ==> " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("operation db to cache error ==> " + e.getMessage());
                p6eClientDbList = new ArrayList<>();
            }
        } while (p6eClientDbList.size() >= DB_SIZE);
        LOGGER.info("end operation db to cache.");
    }

}
