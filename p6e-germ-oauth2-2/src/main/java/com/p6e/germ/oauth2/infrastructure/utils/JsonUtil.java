package com.p6e.germ.oauth2.infrastructure.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 对 GEON 的封装
 * 为什么的封装 ？？
 * 因为 obj -> stirng , string->obj 的库很多
 * 以后如果由于 boss 说换成 XXX 库，岂不是很多地方都需要修改，如果这样写，可以只用修改一个地方就可了
 * @author LiDaShuang
 * @version 1.0
 */
public final class JsonUtil {

    /** 工具类创建一个 GEON 对象 */
    private static final Gson GSON = new Gson();

    public static String toJson(Object o) {
        return o == null ? null : GSON.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    public static <T, W> Map<T, W> fromJsonToMap(String json, Class<T> keyClass, Class<W> valueClass) {
        return GSON.fromJson(json, new TypeToken<Map<T, W>>() {}.getType());
    }

}
