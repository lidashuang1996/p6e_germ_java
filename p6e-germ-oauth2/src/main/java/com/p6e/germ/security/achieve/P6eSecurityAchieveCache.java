package com.p6e.germ.security.achieve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
final class P6eSecurityAchieveCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(P6eSecurityAchieveCache.class);

    private static final Map<String, P6eSecurityAchieveGroupModel> GROUP_CACHE = new HashMap<>();
    private static final Map<String, P6eSecurityAchieveJurisdictionModel> JURISDICTION_CACHE = new HashMap<>();
    private static volatile boolean IS_INIT_GROUP = false;
    private static volatile boolean IS_INIT_JURISDICTION = false;
    private static volatile P6eSecurityAchieveInterface ACHIEVE;

    static void init(P6eSecurityAchieveInterface achieve) {
        ACHIEVE = achieve;
        initGroup();
        initJurisdiction();
    }
    
    private synchronized static void initGroup() {
        // 开始初始化的 group
        LOGGER.info("[ SECURITY ACHIEVE ] START INIT GROUP.");
        IS_INIT_GROUP = true;
        if (ACHIEVE == null) {
            throw new RuntimeException(
                    P6eSecurityAchieveCache.class + " " + P6eSecurityAchieveInterface.class + " is null." );
        } else {
            // 获取数据
            final List<P6eSecurityAchieveGroupModel> groupModels = ACHIEVE.getGroupData();
            // 清除缓存的数据
            GROUP_CACHE.clear();
            // 添加获取的最新数据
            groupModels.forEach(item -> GROUP_CACHE.put(String.valueOf(item.getId()), item));
        }
        // 结束初始化
        IS_INIT_GROUP = false;
        LOGGER.info("[ SECURITY ACHIEVE ] END INIT GROUP.");
    }

    private synchronized static void initJurisdiction() {
        // 开始初始化的 jurisdiction
        LOGGER.info("[ SECURITY ACHIEVE ] START INIT JURISDICTION.");
        IS_INIT_JURISDICTION = true;
        if (ACHIEVE == null) {
            throw new RuntimeException(
                    P6eSecurityAchieveCache.class + " " + P6eSecurityAchieveInterface.class + " is null." );
        } else {
            // 获取数据
            final List<P6eSecurityAchieveJurisdictionModel> jurisdictionModels = ACHIEVE.getJurisdictionData();
            // 清除缓存的数据
            JURISDICTION_CACHE.clear();
            // 添加获取的最新数据
            jurisdictionModels.forEach(item -> JURISDICTION_CACHE.put(item.getCode(), item));
        }
        // 结束初始化
        IS_INIT_JURISDICTION = false;
        LOGGER.info("[ SECURITY ACHIEVE ] END INIT JURISDICTION.");
    }

    static P6eSecurityAchieveGroupModel getGroupCache(String id) {
        if (IS_INIT_GROUP) {
            throw new RuntimeException(
                    P6eSecurityAchieveCache.class + " group initializing...");
        } else {
            return GROUP_CACHE.get(id);
        }
    }

    static P6eSecurityAchieveJurisdictionModel getJurisdictionCache(String code) {
        if (IS_INIT_JURISDICTION) {
            throw new RuntimeException(
                    P6eSecurityAchieveCache.class + " jurisdiction initializing...");
        } else {
            return JURISDICTION_CACHE.get(code);
        }
    }
}
