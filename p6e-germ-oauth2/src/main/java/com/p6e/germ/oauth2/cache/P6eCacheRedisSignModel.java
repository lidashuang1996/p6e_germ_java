package com.p6e.germ.oauth2.cache;

import com.p6e.germ.oauth2.utils.CommonUtil;
import com.p6e.germ.oauth2.utils.GsonUtil;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类实现了 sign 模式缓存用户信息
 * 通过写入、读取、删除缓存实现用户的认证功能
 *
 * 这里是建立的模型对象
 *
 * 认证可以有三种模式:
 * 1. 一个账号同时有一个人使用，后来使前者退出登录
 * 2. 一个账号同时有多个人使用，后来不会对前者产生影响
 * 3. 一个账号同时多个设备使用，相同设备登录会使前者退出登录
 *
 * 该类现在默认实现 一个账号多个人使用
 * 你可以在这里修改代码从而切换为其他模式，来适应你需要的场景
 *
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisSignModel {

    /**
    * 认证的模型枚举类别
    */
    public enum Type {
        /** 一个账号同时有一个人使用，后来使前者退出登录 */
        U,
        /** 一个账号同时有多个人使用，后来不会对前者产生影响 */
        M,
        /** 一个账号同时多个设备使用，相同设备登录会使前者退出登录 */
        UQ;

        /**
         * 初始化，并注入枚举类型对应的认证实现
         */
        private static final Map<Type, IType> CACHE = new HashMap<>();
        static {
            CACHE.put(U, new TypeU());
            CACHE.put(M, new TypeM());
            CACHE.put(UQ, new TypeUQ());
        }

        /**
         * 枚举选项对应的类型
         * @return 认证类型的实现返回
         */
        public IType type() {
            return CACHE.get(this);
        }
    }

    /**
     * 认证类型接口的定义
     * @author lidashuang
     * @version 1.0
     */
    public interface IType {
        /**
         * 写入缓存数据
         * @param redisTemplate redis 模版
         * @param name 前缀名称
         * @param time 过期时间-秒
         * @param contract 参数对象
         * @param data 缓存的用户数据对象
         * @return 写入的用户数据对象
         */
        P6eCacheRedisSignModel.Core set(RedisTemplate<String, String> redisTemplate,
                                        String name, Long time, Contract contract, Object data);

        /**
         * 删除缓存数据
         * @param redisTemplate redis 模版
         * @param name 前缀名称
         * @param contract 参数对象
         * @param tClass 缓存的用户数据对象的类型
         * @return 删除的用户数据对象
         */
        P6eCacheRedisSignModel.Core del(RedisTemplate<String, String> redisTemplate,
                                        String name, Contract contract, Class<?> tClass);

        /**
         * 获取缓存数据
         * @param redisTemplate redis 模版
         * @param name 前缀名称
         * @param contract 参数对象
         * @param tClass 缓存的用户数据对象的类型
         * @return 读取的用户数据对象
         */
        P6eCacheRedisSignModel.Core get(RedisTemplate<String, String> redisTemplate,
                                        String name, Contract contract, Class<?> tClass);

        /**
         * 清空缓存数据
         * @param redisTemplate redis 模版
         * @param name 前缀名称
         * @param contract 参数对象
         */
        void clean(RedisTemplate<String, String> redisTemplate, String name, Contract contract);
    }

    /**
     * 认证类型接口的实现
     * 一个账号同时有一个人使用，后来使前者退出登录的功能实现
     * @author lidashuang
     * @version 1.0
     */
    public static class TypeU implements IType {
        @Override
        public Core set(final RedisTemplate<String, String> redisTemplate,
                        final String name, final Long time, final Contract contract, final Object data) {
            final Integer id = contract.getId();
            final String token = CommonUtil.generateUUID();
            final String refreshToken = CommonUtil.generateUUID();
            // 生产凭证对象
            final Voucher voucher = new Voucher(String.valueOf(id), token, refreshToken, time);
            // 开启 redis 管道流
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                final byte[] markBytes = (name + "MARK:" + id).getBytes(StandardCharsets.UTF_8);
                final byte[] userBytes = (name + "USER:" + id).getBytes(StandardCharsets.UTF_8);
                final byte[] tokenBytes = (name + "TOKEN:" + token).getBytes(StandardCharsets.UTF_8);
                final byte[] refreshTokenBytes = (name + "REFRESH_TOKEN:" + refreshToken).getBytes(StandardCharsets.UTF_8);
                final byte[] voucherBytes = GsonUtil.toJson(voucher).getBytes(StandardCharsets.UTF_8);
                final byte[] bytes = redisConnection.get(markBytes);
                if (bytes != null) { // 如果存在用户信息，就把之前的用户的认证信息删除
                    try {
                        Voucher v = GsonUtil.fromJson(new String(bytes, StandardCharsets.UTF_8), Voucher.class);
                        redisConnection.del((name + "TOKEN:" + v.getToken()).getBytes(StandardCharsets.UTF_8));
                        redisConnection.del((name + "REFRESH_TOKEN:" + v.getRefreshToken()).getBytes(StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        // 忽略异常
                    }
                }
                redisConnection.set(markBytes, voucherBytes);
                redisConnection.set(tokenBytes, voucherBytes);
                redisConnection.set(refreshTokenBytes, voucherBytes);
                redisConnection.set(userBytes, GsonUtil.toJson(data).getBytes(StandardCharsets.UTF_8));
                return null;
            });
            return new Core(contract.getId(), token, refreshToken, time, data);
        }

        @Override
        public Core del(final RedisTemplate<String, String> redisTemplate,
                        final String name, final Contract contract, final Class<?> tClass) {
            final Core core = new Core();
            final String token = contract.getToken();
            core.setToken(token);
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                try {
                    final byte[] voucherBytes = redisConnection.get(
                            (name + "TOKEN:" + token).getBytes(StandardCharsets.UTF_8));

                    if (voucherBytes != null) {
                        final Voucher voucher = GsonUtil.fromJson(
                                new String(voucherBytes, StandardCharsets.UTF_8), Voucher.class);
                        if (voucher != null) {
                            final byte[] dataBytes = redisConnection.get(
                                    (name + "USER:" + voucher.getId()).getBytes(StandardCharsets.UTF_8));

                            if (dataBytes != null) {
                                core.setData(GsonUtil.fromJson(new String(dataBytes, StandardCharsets.UTF_8), tClass));
                            }

                            core.setId(Integer.valueOf(voucher.getId()));
                            core.setExpiration(voucher.getExpiration());
                            core.setRefreshToken(voucher.getRefreshToken());

                            redisConnection.del((name + "MARK:" + voucher.getId()).getBytes(StandardCharsets.UTF_8));
                            redisConnection.del((name + "USER:" + voucher.getId()).getBytes(StandardCharsets.UTF_8));
                            redisConnection.del((name + "REFRESH_TOKEN:"
                                    + voucher.getRefreshToken()).getBytes(StandardCharsets.UTF_8));
                        }
                    }
                } catch (Exception e) {
                    // 忽略异常
                } finally {
                    redisTemplate.delete(name + "TOKEN:" + token);
                }
                return null;
            });
            return core;
        }

        @Override
        public Core get(final RedisTemplate<String, String> redisTemplate,
                        final String name, final Contract contract, final Class<?> tClass) {
            return getData(redisTemplate, name, contract, tClass);
        }

        @Override
        public void clean(RedisTemplate<String, String> redisTemplate, String name, Contract contract) {
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                try {
                    byte[] bytes = redisConnection.get(
                            (name + "MARK:" + contract.getId()).getBytes(StandardCharsets.UTF_8));
                    if (bytes != null) {
                        delVoucherCache(
                                redisConnection,
                                GsonUtil.fromJson(new String(bytes, StandardCharsets.UTF_8), Voucher.class),
                                name
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    redisConnection.del((name + "MARK:" + contract.getId()).getBytes(StandardCharsets.UTF_8));
                    redisConnection.del((name + "USER:" + contract.getId()).getBytes(StandardCharsets.UTF_8));
                }
                return null;
            });
        }
    }

    /**
     * 认证类型接口的实现
     * 一个账号同时有多个人使用，后来不会对前者产生影响
     * @author lidashuang
     * @version 1.0
     */
    public static class TypeM implements IType {

        @Override
        public Core set(final RedisTemplate<String, String> redisTemplate,
                        final String name, Long time, final Contract contract, final Object data) {
            final Integer id = contract.getId();
            final String token = CommonUtil.generateUUID();
            final String refreshToken = CommonUtil.generateUUID();
            // 生产凭证对象
            final Voucher voucher = new Voucher(String.valueOf(id), token, refreshToken, time);
            // 开启 redis 管道流
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                final byte[] userBytes = (name + "USER:" + id).getBytes(StandardCharsets.UTF_8);
                final byte[] tokenBytes = (name + "TOKEN:" + token).getBytes(StandardCharsets.UTF_8);
                final byte[] markTokenBytes = (name + "MARK:" + id + ":TOKEN_" + token).getBytes(StandardCharsets.UTF_8);
                final byte[] markRefreshTokenBytes = (name + "MARK:" + id
                        + ":REFRESH_TOKEN_" + refreshToken).getBytes(StandardCharsets.UTF_8);
                final byte[] refreshTokenBytes = (name + "REFRESH_TOKEN:" + refreshToken).getBytes(StandardCharsets.UTF_8);
                final byte[] voucherBytes = GsonUtil.toJson(voucher).getBytes(StandardCharsets.UTF_8);
                redisConnection.set(tokenBytes, voucherBytes);
                redisConnection.set(refreshTokenBytes, voucherBytes);
                redisConnection.set(markTokenBytes, voucherBytes);
                redisConnection.set(markRefreshTokenBytes, voucherBytes);
                redisConnection.set(userBytes, GsonUtil.toJson(data).getBytes(StandardCharsets.UTF_8));
                return null;
            });
            return new Core(contract.getId(), token, refreshToken, time, data);
        }

        @Override
        public Core del(final RedisTemplate<String, String> redisTemplate,
                        final String name, final Contract contract, final Class<?> tClass) {
            final Core core = new Core();
            final String token = contract.getToken();
            core.setToken(token);
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                try {
                    final byte[] voucherBytes = redisConnection.get(
                            (name + "TOKEN:" + token).getBytes(StandardCharsets.UTF_8));

                    if (voucherBytes != null) {
                        final Voucher voucher = GsonUtil.fromJson(
                                new String(voucherBytes, StandardCharsets.UTF_8), Voucher.class);
                        if (voucher != null) {
                            core.setId(Integer.valueOf(voucher.getId()));
                            core.setExpiration(voucher.getExpiration());
                            core.setRefreshToken(voucher.getRefreshToken());
                            final byte[] dataBytes = redisConnection.get(
                                    (name + "USER:" + voucher.getId()).getBytes(StandardCharsets.UTF_8));
                            if (dataBytes != null) {
                                core.setData(GsonUtil.fromJson(new String(dataBytes, StandardCharsets.UTF_8), tClass));
                            }
                            redisConnection.del((name + "MARK:" + voucher.getId()
                                    + ":TOKEN_" + voucher.getToken()).getBytes(StandardCharsets.UTF_8));
                            redisConnection.del((name + "MARK:" + voucher.getId()
                                    + ":REFRESH_TOKEN_" + voucher.getRefreshToken()).getBytes(StandardCharsets.UTF_8));
                            redisConnection.del((name + "TOKEN:"
                                    + voucher.getToken()).getBytes(StandardCharsets.UTF_8));
                            redisConnection.del((name + "REFRESH_TOKEN:"
                                    + voucher.getRefreshToken()).getBytes(StandardCharsets.UTF_8));
                        }
                    }
                } catch (Exception e) {
                    // 忽略异常
                } finally {
                    redisTemplate.delete(name + "TOKEN:" + token);
                }
                return null;
            });
            return core;
        }

        @Override
        public Core get(final RedisTemplate<String, String> redisTemplate,
                        final String name, final Contract contract, final Class<?> tClass) {
            return getData(redisTemplate, name, contract, tClass);
        }

        @Override
        public void clean(final RedisTemplate<String, String> redisTemplate, final String name, final Contract contract) {
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                try {
                    final Cursor<byte[]> cursor1 = redisConnection.scan(ScanOptions.scanOptions()
                            .match(name + "MARK:" + contract.getId() + ":TOKEN_*").count(200).build());
                    final Cursor<byte[]> cursor2 = redisConnection.scan(ScanOptions.scanOptions()
                            .match(name + "MARK:" + contract.getId() + ":REFRESH_TOKEN_*").count(200).build());
                    while (cursor1.hasNext()) {
                        byte[] bytes = cursor1.next();
                        redisConnection.del(bytes);
                        redisConnection.del((name + "TOKEN:" + new String(bytes,
                                StandardCharsets.UTF_8).split("_")[1]).getBytes(StandardCharsets.UTF_8));
                    }
                    while (cursor2.hasNext()) {
                        byte[] bytes = cursor2.next();
                        redisConnection.del(bytes);
                        redisConnection.del((name + "REFRESH_TOKEN:" + new String(bytes,
                                StandardCharsets.UTF_8).split("_")[1]).getBytes(StandardCharsets.UTF_8));
                    }
                } catch (Exception e) {
                    // 忽略异常
                } finally {
                    redisConnection.del((name + "USER:" + contract.getId()).getBytes(StandardCharsets.UTF_8));
                }
                return null;
            });
        }
    }

    /**
     * 认证类型接口的实现
     * 一个账号同时多个设备使用，相同设备登录会使前者退出登录
     * @author lidashuang
     * @version 1.0
     */
    public static class TypeUQ implements IType {

        @Override
        public Core set(final RedisTemplate<String, String> redisTemplate,
                        final String name, final Long time, final Contract contract, final Object data) {

            final Integer id = contract.getId();
            final String token = CommonUtil.generateUUID();
            final String refreshToken = CommonUtil.generateUUID();
            // 生产凭证对象
            final Voucher voucher = new Voucher(String.valueOf(id), token, refreshToken, time);
            // 开启 redis 管道流
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                final byte[] userBytes = (name + "USER:" + id).getBytes(StandardCharsets.UTF_8);
                final byte[] tokenBytes = (name + "TOKEN:" + token).getBytes(StandardCharsets.UTF_8);
                final byte[] refreshTokenBytes = (name
                        + "REFRESH_TOKEN:" + refreshToken).getBytes(StandardCharsets.UTF_8);
                final byte[] deviceBytes = (name + "DEVICE:"
                        + id + ":" + contract.getDevice()).getBytes(StandardCharsets.UTF_8);
                final byte[] voucherBytes = GsonUtil.toJson(voucher).getBytes(StandardCharsets.UTF_8);
                final byte[] deviceContentBytes = redisConnection.get(deviceBytes);
                if (deviceContentBytes != null) {
                    // 删除之前的 token
                    try {
                        delVoucherCache(
                                redisConnection,
                                GsonUtil.fromJson(new String(deviceContentBytes, StandardCharsets.UTF_8), Voucher.class),
                                name
                        );
                    } catch (Exception e) {
                        // 忽略异常
                    }
                }
                redisConnection.set(tokenBytes, voucherBytes);
                redisConnection.set(refreshTokenBytes, voucherBytes);
                redisConnection.set(deviceBytes, voucherBytes);
                redisConnection.set(userBytes, GsonUtil.toJson(data).getBytes(StandardCharsets.UTF_8));
                return null;
            });
            return new Core(contract.getId(), token, refreshToken, time, data);
        }

        @Override
        public Core del(final RedisTemplate<String, String> redisTemplate,
                        final String name, final Contract contract, final Class<?> tClass) {
            final Core core = new Core();
            final String token = contract.getToken();
            core.setToken(token);
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                try {
                    final byte[] voucherBytes = redisConnection.get(
                            (name + "TOKEN:" + token).getBytes(StandardCharsets.UTF_8));

                    if (voucherBytes != null) {
                        final Voucher voucher = GsonUtil.fromJson(
                                new String(voucherBytes, StandardCharsets.UTF_8), Voucher.class);
                        if (voucher != null) {
                            core.setId(Integer.valueOf(voucher.getId()));
                            core.setExpiration(voucher.getExpiration());
                            core.setRefreshToken(voucher.getRefreshToken());
                            redisConnection.del((name + "DEVICE:" + contract.getId()
                                    + ":" + contract.getDevice()).getBytes(StandardCharsets.UTF_8));
                            redisConnection.del((name + "TOKEN:"
                                    + voucher.getToken()).getBytes(StandardCharsets.UTF_8));
                            redisConnection.del((name + "REFRESH_TOKEN:"
                                    + voucher.getRefreshToken()).getBytes(StandardCharsets.UTF_8));
                            final byte[] dataBytes = redisConnection.get(
                                    (name + "USER:" + voucher.getId()).getBytes(StandardCharsets.UTF_8));
                            if (dataBytes != null) {
                                core.setData(GsonUtil.fromJson(new String(dataBytes, StandardCharsets.UTF_8), tClass));
                            }
                        }
                    }
                } catch (Exception e) {
                    // 忽略异常
                } finally {
                    redisTemplate.delete(name + "TOKEN:" + token);
                }
                return null;
            });
            return core;
        }

        @Override
        public Core get(final RedisTemplate<String, String> redisTemplate,
                        final String name, final Contract contract, final Class<?> tClass) {
            return getData(redisTemplate, name, contract, tClass);
        }

        @Override
        public void clean(final RedisTemplate<String, String> redisTemplate, final String name, final Contract contract) {
            redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                try {
                    final Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions()
                            .match(name + "DEVICE:" + contract.getId() + ":*").count(200).build());
                    while (cursor.hasNext()) {
                        byte[] bytes = cursor.next();
                        if (bytes != null) {
                            byte[] byteContents = redisConnection.get(bytes);
                            if (byteContents != null) {
                                Voucher v = GsonUtil.fromJson(
                                        new String(byteContents, StandardCharsets.UTF_8), Voucher.class);
                                redisConnection.del(bytes);
                                redisConnection.del((name + "TOKEN:" + v.getToken()).getBytes(StandardCharsets.UTF_8));
                                redisConnection.del((name
                                        + "REFRESH_TOKEN:" + v.getRefreshToken()).getBytes(StandardCharsets.UTF_8));
                            }
                        }
                    }
                } catch (Exception e) {
                    // 忽略异常
                } finally {
                    redisConnection.del((name + "USER:" + contract.getId()).getBytes(StandardCharsets.UTF_8));
                }
                return null;
            });
        }
    }


    /**
     * 通用返回数据的方法
     * @param redisTemplate redis 缓存模版
     * @param name 前缀名称
     * @param contract 缓存对象数据
     * @param tClass 缓存数据类型
     * @return 用户写入的核心对象
     */
    private static Core getData(final RedisTemplate<String, String> redisTemplate,
                         final String name, final Contract contract, final Class<?> tClass) {
        final Core core = new Core();
        final String token = contract.getToken();
        core.setToken(token);
        final String voucherJson = redisTemplate.opsForValue().get(name + "TOKEN:" + token);
        if (voucherJson != null) {
            final Voucher voucher = GsonUtil.fromJson(voucherJson, Voucher.class);
            if (voucher != null) {
                core.setId(Integer.valueOf(voucher.getId()));
                core.setExpiration(voucher.getExpiration());
                core.setRefreshToken(voucher.getRefreshToken());
                String dataJson = redisTemplate.opsForValue().get(name + "USER:" + voucher.getId());
                if (dataJson != null) {
                    core.setData(GsonUtil.fromJson(dataJson, tClass));
                }
            }
        }
        return core;
    }

    private static void delVoucherCache(final RedisConnection redisConnection, Voucher v, String name) {
        if (v != null) {
            redisConnection.del((name + "TOKEN:" + v.getToken()).getBytes(StandardCharsets.UTF_8));
            redisConnection.del((name + "REFRESH_TOKEN:" + v.getRefreshToken()).getBytes(StandardCharsets.UTF_8));
        }
    }


    /**
     * 认证模型
     * @author lidashuang
     * @version 1.0
     */
    public static class Core {
        private Integer id;
        private String token;
        private String refreshToken;
        private Long expiration;

        private Object data;

        public Core() {
        }

        public Core(Integer id, String token, String refreshToken, Long expiration, Object data) {
            this.id = id;
            this.token = token;
            this.refreshToken = refreshToken;
            this.expiration = expiration;
            this.data = data;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public Long getExpiration() {
            return expiration;
        }

        public void setExpiration(Long expiration) {
            this.expiration = expiration;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "{"
                    + "\"id\":"
                    + id
                    + ",\"token\":\""
                    + token + '\"'
                    + ",\"refreshToken\":\""
                    + refreshToken + '\"'
                    + ",\"expiration\":"
                    + expiration
                    + ",\"data\":"
                    + data
                    + "}";
        }
    }

    /**
     * 凭证模型
     * @author lidashuang
     * @version 1.0
     */
    public static class Voucher {
        private String id;
        private String token;
        private String refreshToken;
        private Long expiration;

        public Voucher() {
        }

        public Voucher(String id, String token, String refreshToken, Long expiration) {
            this.id = id;
            this.token = token;
            this.refreshToken = refreshToken;
            this.expiration = expiration;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public Long getExpiration() {
            return expiration;
        }

        public void setExpiration(Long expiration) {
            this.expiration = expiration;
        }

        @Override
        public String toString() {
            return "{"
                    + "\"id\":\""
                    + id + '\"'
                    + ",\"token\":\""
                    + token + '\"'
                    + ",\"refreshToken\":\""
                    + refreshToken + '\"'
                    + ",\"expiration\":"
                    + expiration
                    + "}";
        }
    }

    /**
     * 合同模型
     * @author lidashuang
     * @version 1.0
     */
    public static class Contract {
        private Integer id;
        private String token;
        private String device;

        public Contract() {
        }

        public Contract(Integer id, String token, String device) {
            this.id = id;
            this.token = token;
            this.device = device;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        @Override
        public String toString() {
            return "{"
                    + "\"id\":"
                    + id
                    + ",\"token\":\""
                    + token + '\"'
                    + ",\"device\":\""
                    + device + '\"'
                    + "}";
        }
    }

}
