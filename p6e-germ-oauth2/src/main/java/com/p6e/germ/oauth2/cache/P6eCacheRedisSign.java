//package com.p6e.germ.oauth2.cache;
//
//import com.p6e.germ.oauth2.utils.CommonUtil;
//import com.p6e.germ.oauth2.utils.GsonUtil;
//import org.springframework.data.redis.core.Cursor;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.ScanOptions;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.util.concurrent.TimeUnit;
//
///**
// * 该类实现了 sign 模式缓存用户信息
// * 通过写入、读取、删除缓存实现用户的认证功能
// *
// * 认证可以有三种模式:
// * 1. 一个账号同时有一个人使用，后来使前者退出登录
// * 2. 一个账号同时有多个人使用，后来不会对前者产生影响
// * 3. 一个账号同时多个设备使用，相同设备登录会使前者退出登录
// *
// * 该类现在默认实现 一个账号多个人使用
// * 你可以在这里修改代码从而切换为其他模式，来适应你需要的场景
// *
// * @author lidashuang
// * @version 1.0
// */
//@Component
//public class P6eCacheRedisSign extends P6eCacheRedis implements IP6eCacheSign {
//
//
//
//    /**
//     * 认证的模型枚举类别
//     */
//    private enum Type {
//        /** 一个账号同时有一个人使用，后来使前者退出登录 */
//        U,
//        /** 一个账号同时有多个人使用，后来不会对前者产生影响 */
//        M,
//        /** 一个账号同时多个设备使用，相同设备登录会使前者退出登录 */
//        UQ
//    }
//
//    /** 选择的认证缓存方式 */
//    private static final Type type = Type.U;
//
//    @Override
//    public P6eCacheRedisSignModel.Core set(String id, Object data) {
//        switch (type) {
//            case U:
//                setUserU(id, data);
//                break;
//            case M:
//                setUserM(id, data);
//                break;
//            case UQ:
//                setUserUQ(id, data);
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//        return null;
//    }
//
//
//    @Override
//    public void setToken(String key1, String key2, String value) {
//        switch (type) {
//            case U:
//                setTokenU(key1, value);
//                break;
//            case M:
//                setTokenM(key1, key2, value);
//                break;
//            case UQ:
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    @Override
//    public void setRefreshToken(String key1, String key2, String value) {
//        switch (type) {
//            case U:
//                setRefreshTokenU(key1, value);
//                break;
//            case M:
//                setRefreshTokenM(key1, key2, value);
//                break;
//            case UQ:
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    @Override
//    public String getUser(String key) {
//        switch (type) {
//            case U:
//                return getUserU(key);
//            case M:
//                return getUserM(key);
//                break;
//            case UQ:
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    @Override
//    public String getToken(String key1, String key2) {
//        switch (type) {
//            case U:
//                return getTokenU(key1);
//            case M:
//                break;
//            case UQ:
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    @Override
//    public String getRefreshToken(String key1, String key2) {
//        switch (type) {
//            case U:
//                return getRefreshTokenU(key1);
//            case M:
//                break;
//            case UQ:
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    @Override
//    public void delUser(String key) {
//        switch (type) {
//            case U:
//                delUserU(key);
//                break;
//            case M:
//                delUserM(key);
//                break;
//            case UQ:
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    @Override
//    public void delToken(String key1, String key2) {
//        switch (type) {
//            case U:
//                delTokenU(key1);
//                break;
//            case M:
//                break;
//            case UQ:
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    @Override
//    public void delRefreshToken(String key1, String key2) {
//        switch (type) {
//            case U:
//                delRefreshTokenU(key1);
//                break;
//            case M:
//                break;
//            case UQ:
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    @Override
//    public void cleanUser(String key) {
//        switch (type) {
//            case U:
//                cleanUserU(key);
//                break;
//            case M:
//                cleanUserM(key);
//                break;
//            case UQ:
//                cleanUserUQ(key);
//                break;
//            default:
//                throw new RuntimeException("type [ " + Type.class.getName() + " ] defined type not found");
//        }
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * 添加用户数据
//     * @param id 用户的 ID
//     * @param data 用户的数据
//     */
//    private void setUserU(String id, Object data) {
//        final String token = CommonUtil.generateUUID();
//        final String refreshToken = CommonUtil.generateUUID();
//        P6eCacheRedisSignModel.Voucher voucher = new P6eCacheRedisSignModel.Voucher(id, token, refreshToken);
//        redisTemplate.opsForValue().set(SIGN_USER_NAME + token, GsonUtil.toJson(value), SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * 添加 token 数据
//     * @param key token
//     * @param value 用户的 ID
//     */
//    private void setTokenU(String key, String value) {
//        redisTemplate.opsForValue().set(SIGN_TOKEN_NAME + key, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * 添加 refresh token 数据
//     * @param key refresh token
//     * @param value 用户的 ID
//     */
//    private void setRefreshTokenU(String key, String value) {
//        redisTemplate.opsForValue().set(SIGN_REFRESH_TOKEN_NAME + key, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * 获取用户数据
//     * @param key 用户的 ID
//     */
//    private String getUserU(String key) {
//        return redisTemplate.opsForValue().get(SIGN_USER_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * 获取 token 数据
//     * @param key token
//     */
//    private String getTokenU(String key) {
//        return redisTemplate.opsForValue().get(SIGN_TOKEN_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * 获取 refresh token 数据
//     * @param key refresh token
//     */
//    private String getRefreshTokenU(String key) {
//        return redisTemplate.opsForValue().get(SIGN_REFRESH_TOKEN_NAME + key);
//
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * @param key 用户的 ID
//     */
//    private void delUserU(String key) {
//        redisTemplate.delete(SIGN_USER_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * @param key token
//     */
//    private void delTokenU(String key) {
//        redisTemplate.delete(SIGN_TOKEN_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * 删除 refresh token
//     * @param key refresh token
//     */
//    private void delRefreshTokenU(String key) {
//        redisTemplate.delete(SIGN_REFRESH_TOKEN_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有一个人使用，后来使前者退出登录 -- 模式
//     * 清除用户数据
//     * @param key 用户的 ID
//     */
//    private void cleanUserU(String key) {
//        try {
//            String json = redisTemplate.opsForValue().get(key);
//            if (json != null) {
//                Auth auth = GsonUtil.fromJson(json, Auth.class);
//                if (auth != null) {
//                    redisTemplate.delete(SIGN_TOKEN_NAME + auth.token);
//                    redisTemplate.delete(SIGN_REFRESH_TOKEN_NAME + auth.token);
//                }
//            }
//        } catch (Exception e) {
//            redisTemplate.delete(key);
//        }
//    }
//
//
//
//
//
//
//
//
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * 添加用户数据
//     * @param key 用户的 ID
//     * @param value 用户的数据
//     */
//    private void setUserM(String key, String value) {
//        redisTemplate.opsForValue().set(SIGN_USER_NAME + key, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * 添加 token 数据
//     * @param key1 token
//     * @param key2 用户的 ID
//     * @param value auth 对象
//     */
//    private void setTokenM(String key1, String key2, String value) {
//        redisTemplate.opsForValue().set(SIGN_TOKEN_NAME + key1, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set(SIGN_TOKEN_NAME + key2 + ":" + key1, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * 添加 refresh token 数据
//     * @param key1 token
//     * @param key2 用户的 ID
//     * @param value auth 对象
//     */
//    private void setRefreshTokenM(String key1, String key2, String value) {
//        redisTemplate.opsForValue().set(SIGN_REFRESH_TOKEN_NAME + key1, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set(SIGN_REFRESH_TOKEN_NAME + key2 + ":" + key1, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * 获取用户数据
//     * @param key 用户的 ID
//     */
//    private String getUserM(String key) {
//        return redisTemplate.opsForValue().get(SIGN_USER_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * 获取 token 数据
//     * @param key token
//     */
//    private String getTokenM(String key) {
//        return redisTemplate.opsForValue().get(SIGN_TOKEN_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * 获取 refresh token 数据
//     * @param key refresh token
//     */
//    private String getRefreshTokenM(String key) {
//        return redisTemplate.opsForValue().get(SIGN_REFRESH_TOKEN_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * @param key 用户的 ID
//     */
//    private void delUserM(String key) {
//        redisTemplate.delete(SIGN_USER_NAME + key);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * @param key1 token
//     */
//    private void delTokenM(String key1, String key2) {
//        redisTemplate.delete(SIGN_TOKEN_NAME + key1);
//        redisTemplate.delete(SIGN_TOKEN_NAME + key2 + ":" + key1);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响录 -- 模式
//     * 删除 refresh token
//     * @param key1 refresh token
//     */
//    private void delRefreshTokenM(String key1, String key2) {
//        redisTemplate.delete(SIGN_REFRESH_TOKEN_NAME + key1);
//        redisTemplate.delete(SIGN_REFRESH_TOKEN_NAME + key2 + ":" + key1);
//    }
//
//    /**
//     * 一个账号同时有多个人使用，后来不会对前者产生影响 -- 模式
//     * 清除用户数据
//     * @param key 用户的 ID
//     */
//    private void cleanUserM(final String key) {
//        redisTemplate.execute((RedisCallback<String>) redisConnection -> {
//            redisConnection.del((SIGN_USER_NAME + key).getBytes(StandardCharsets.UTF_8));
//            final Cursor<byte[]> cursor1 = redisConnection.scan(
//                    ScanOptions.scanOptions().match(SIGN_TOKEN_NAME + key + ":*").count(1000).build());
//            final Cursor<byte[]> cursor2 = redisConnection.scan(
//                    ScanOptions.scanOptions().match(SIGN_REFRESH_TOKEN_NAME + key + ":*").count(1000).build());
//            while (cursor1.hasNext()) {
//                redisConnection.del(cursor1.next());
//            }
//            while (cursor2.hasNext()) {
//                redisConnection.del(cursor2.next());
//            }
//            return null;
//        });
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * 添加用户数据
//     * @param key 用户的 ID
//     * @param value 用户的数据
//     */
//    private void setUserUQ(String key, String value) {
//        redisTemplate.opsForValue().set(SIGN_USER_NAME + key, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * 添加 token 数据
//     * @param key1 token
//     * @param key2 设备的名称
//     * @param key3 用户的 ID
//     * @param value auth 对象
//     */
//    private void setTokenUQ(String key1, String key2, String key3, String value) {
//        delLike(SIGN_TOKEN_NAME + key3 + ":" + key2 + ":*");
//        redisTemplate.opsForValue().set(SIGN_TOKEN_NAME + key1, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set(
//                SIGN_TOKEN_NAME + key3 + ":" + key2 + ":" + key1, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * 添加 refresh token 数据
//     * @param key1 token
//     * @param key2 用户的 ID
//     * @param value auth 对象
//     */
//    private void setRefreshTokenUQ(String key1, String key2, String key3, String value) {
//        delLike(SIGN_REFRESH_TOKEN_NAME + key3 + ":" + key2 + ":*");
//        redisTemplate.opsForValue().set(SIGN_REFRESH_TOKEN_NAME + key1, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set(
//                SIGN_REFRESH_TOKEN_NAME + key3 + ":" + key2 + ":" + key1, value, SIGN_DATE_TIME, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * 获取用户数据
//     * @param key 用户的 ID
//     */
//    private String getUserUQ(String key) {
//        return redisTemplate.opsForValue().get(SIGN_USER_NAME + key);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * 获取 token 数据
//     * @param key token
//     */
//    private String getTokenUQ(String key) {
//        return redisTemplate.opsForValue().get(SIGN_TOKEN_NAME + key);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * 获取 refresh token 数据
//     * @param key refresh token
//     */
//    private String getRefreshTokenUQ(String key) {
//        return redisTemplate.opsForValue().get(SIGN_REFRESH_TOKEN_NAME + key);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * @param key 用户的 ID
//     */
//    private void delUserUQ(String key) {
//        redisTemplate.delete(SIGN_USER_NAME + key);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * @param key1 token
//     */
//    private void delTokenUQ(String key1, String key2, String key3) {
//        redisTemplate.delete(SIGN_TOKEN_NAME + key1);
//        redisTemplate.delete(SIGN_TOKEN_NAME + key3 + ":" + key2 + ":" + key1);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * 删除 refresh token
//     * @param key1 refresh token
//     */
//    private void delRefreshTokenUQ(String key1, String key2, String key3) {
//        redisTemplate.delete(SIGN_REFRESH_TOKEN_NAME + key1);
//        redisTemplate.delete(SIGN_REFRESH_TOKEN_NAME + key3 + ":" + key2 + ":" + key1);
//    }
//
//    /**
//     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录 -- 模式
//     * 清除用户数据
//     * @param key 用户的 ID
//     */
//    private void cleanUserUQ(final String key) {
//        redisTemplate.execute((RedisCallback<String>) redisConnection -> {
//            redisConnection.del((SIGN_USER_NAME + key).getBytes(StandardCharsets.UTF_8));
//            final Cursor<byte[]> cursor1 = redisConnection.scan(
//                    ScanOptions.scanOptions().match(SIGN_TOKEN_NAME + key + ":*").count(1000).build());
//            final Cursor<byte[]> cursor2 = redisConnection.scan(
//                    ScanOptions.scanOptions().match(SIGN_REFRESH_TOKEN_NAME + key + ":*").count(1000).build());
//            while (cursor1.hasNext()) {
//                redisConnection.del(cursor1.next());
//            }
//            while (cursor2.hasNext()) {
//                redisConnection.del(cursor2.next());
//            }
//            return null;
//        });
//    }
//
//    /**
//     * 该方法为 redis 的模糊查询删除
//     * @param match 模糊查询内容
//     */
//    private void delLike(String match) {
//        redisTemplate.execute((RedisCallback<String>) redisConnection -> {
//            Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions().match(match).count(10).build());
//            while (cursor.hasNext()) {
//                redisConnection.del(cursor.next());
//            }
//            return null;
//        });
//    }
//
//}
