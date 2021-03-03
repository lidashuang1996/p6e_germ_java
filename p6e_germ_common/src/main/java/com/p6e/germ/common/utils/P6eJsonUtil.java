package com.p6e.germ.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 对 G E O N 的封装
 * @author lidashuang
 * @version 1.0
 */
public final class P6eJsonUtil {

    /** 工具类创建一个 G E O N 对象 */
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * 序列化为 JSON 字符串
     * @param o 序列化对象
     * @return 序列化字符串
     */
    public static String toJson(Object o) {
        return o == null ? null : GSON.toJson(o);
    }

    /**
     * 反序列化为 Class 对象的 T 类型
     * @param json 反序列化内容
     * @param tClass 反序列化类
     * @param <T> 反序列化类型
     * @return 反序列化对象
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }

    /**
     * 反序列化为 Type
     * @param json 反序列化内容
     * @param typeOfT 反序列化类
     * @param <T> 反序列化类型
     * @return 反序列化对象
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    /**
     * 反序列化为 MAP 对象
     * @param json 待反序列化的内容
     * @param keyClass KEY CLASS
     * @param valueClass VALUE CLASS
     * @param <T> KEY 的类型
     * @param <W> VALUE 的类型
     * @return 反序列化的内容
     */
    public static <T, W> Map<T, W> fromJsonToMap(String json, Class<T> keyClass, Class<W> valueClass) {
        return GSON.fromJson(json, new TypeToken<Map<T, W>>() {}.getType());
    }

}
