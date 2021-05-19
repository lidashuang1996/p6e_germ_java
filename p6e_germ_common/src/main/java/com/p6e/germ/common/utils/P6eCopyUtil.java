package com.p6e.germ.common.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Copy 的帮助类
 * 作用是的帮助我们 VO <-> DTO <-> DB 互相转换
 * @author lidashuang
 * @version 1.0
 */
public final class P6eCopyUtil {

    /** 默认 list 参数 */
    private static final List<?> DEFAULT_LIST = new ArrayList<>();

    /** 默认 map 参数 */
    private static final Map<?, ?> DEFAULT_MAP = new HashMap<>();

    /**
     * 对象转换
     * @param data 待转换的数据
     * @param c 转换的类型
     * @param <T> 转换的类型泛型
     * @return 执行结果
     */
    public static <T> T run(Object data, Class<T> c) {
        return run(data, c, null);
    }

    /**
     * 对象转换
     * @param data 待转换的数据
     * @param c 转换的类型
     * @param def 默认的参数
     * @param <T> 转换的类型泛型
     * @return 执行结果
     */
    public static <T> T run(Object data, Class<T> c, T def) {
        try {
            if (data == null || c == null) {
                return deepClone(def);
            } else {
                // 创建对象
                final T t = c.newInstance();
                // 执行复制操作
                return run(data, t, def);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return deepClone(def);
        }
    }

    /**
     * 对象转换
     * @param data 待转换的数据
     * @param t 转换的对象
     * @param <T> 转换的类型泛型
     * @return 执行结果
     */
    public static <T> T run(Object data, T t) {
        return run(data, t, null);
    }

    /**
     * 对象转换
     * @param data 待转换的数据
     * @param t 转换的对象
     * @param def 默认的参数
     * @param <T> 转换的类型泛型
     * @return 执行结果
     */
    public static <T> T run(Object data, T t, T def) {
        try {
            if (data == null || t == null) {
                return deepClone(def);
            } else {
                final Class<?> a = data.getClass();
                final Class<?> b = t.getClass();
                if (!isSerializable(a)) {
                    throw new RuntimeException(a + " copy no interface java.io.Serializable");
                }
                if (!isSerializable(b)) {
                    throw new RuntimeException(b + " copy no interface java.io.Serializable");
                }
                // 获取自类和父类里面的 field
                final Field[] aFields = getFields(a);
                // 获取自类和父类里面的 field
                final Field[] bFields = getFields(b);
                for (final Field f1 : aFields) {
                    for (final Field f2 : bFields) {
                        // 根据名字匹配
                        if (f1.getName().equals(f2.getName())) {
                            // 赋予权限访问
                            f1.setAccessible(true);
                            f2.setAccessible(true);
                            // 读取源数据对象
                            final Object o = f1.get(data);
                            if (o != null) {
                                if (f1.getType() == f2.getType()) {
                                    if (isListType(f1.getType()) && isListType(f2.getType())) {
                                        final Class<?>[] g1 = getGenericClass(f1.getGenericType());
                                        final Class<?>[] g2 = getGenericClass(f2.getGenericType());
                                        if (g1.length == 1 && g2.length == 1) {
                                            // 执行 list 的复制
                                            final List<?> ol = (List<?>) o;
                                            f2.set(t, runList(ol, g2[0], null));
                                        }
                                    } else if (isMapType(f1.getType()) && isMapType(f2.getType())) {
                                        final Class<?>[] g1 = getGenericClass(f1.getGenericType());
                                        final Class<?>[] g2 = getGenericClass(f2.getGenericType());
                                        if (g1.length == 2 && g2.length == 2 && g1[0] == g2[0]
                                                && isSerializable(g1[1]) && isSerializable(g2[1])) {
                                            // 执行 map 的复制
                                            final Map<?, ?> om = (Map<?, ?>) o;
                                            f2.set(t, runMap(om, g2[1], null));
                                        }
                                    } else {
                                        // 对象不为 null 且类型相同且为基础类型，执行赋值操作
                                        f2.set(t, deepClone(o));
                                    }
                                } else {
                                    if (isSerializable(f1.getType())
                                            && isSerializable(f2.getType())
                                            && !isMapType(f1.getType()) && !isMapType(f2.getType())
                                            && !isListType(f1.getType()) && !isListType(f2.getType())) {
                                        // 类型不同且接口 serializable 不为 list
                                        f2.set(t, run(o, f2.getType(), null));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * list 数据复制
     * @param data 数据
     * @param cClass 类型
     * @param <E> 数据类型
     * @param <T> 结果类型
     * @return 执行结果
     */
    @SuppressWarnings("all")
    public static <E, T> List<T> runList(List<E> data, Class<T> cClass) {
        return runList(data, cClass, (List<T>) DEFAULT_LIST);
    }

    /**
     * list 数据复制
     * @param data 数据
     * @param cClass 类型
     * @param def 默认
     * @param <E> 数据类型
     * @param <T> 结果类型
     * @return 复制 map 对象
     */
    @SuppressWarnings("all")
    public static <E, T> List<T> runList(List<E> data, Class<T> cClass, List<T> def) {
        try {
            if (data == null || cClass == null || !isListType(data.getClass())) {
                return deepClone(def);
            } else {
                final List<T> result = new ArrayList<>();
                for (Object datum : data) {
                    // 获取当前数据的类型
                    final Class<?> dClass = datum.getClass();
                    if (dClass == cClass) {
                        if (isListType(dClass) || isListType(cClass)
                                || isMapType(dClass) || isMapType(cClass)) {
                            throw new RuntimeException("data list copy to " + cClass + ", does not support nested list/map");
                        } else {
                            // 写入数据
                            result.add(deepClone((T) datum));
                        }
                    } else {
                        if (isSerializable(dClass)
                                && isSerializable(cClass)
                                && !isMapType(dClass) && !isMapType(cClass)
                                && !isListType(dClass) && !isListType(cClass)) {
                            // 类型不同且接口 serializable 不为 list
                            result.add(run(datum, cClass, null));
                        }
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return deepClone(def);
        }
    }

    /**
     * map 数据复制
     * @param data 数据
     * @param wClass 类型
     * @param <K> KEY 的类型
     * @param <V> VALUE 的类型
     * @param <W> VALUE 的类型
     * @return 复制的 map 对象
     */
    @SuppressWarnings("all")
    public static <K, V, W> Map<K, W>  runMap(Map<K, V> data, Class<W> wClass) {
        return runMap(data, wClass, (Map<K, W>) DEFAULT_MAP);
    }

    /**
     * map 数据复制
     * @param data 数据
     * @param wClass
     * @param def 默认
     * @param <K> KEY 的类型
     * @param <V> VALUE 的类型
     * @param <W> VALUE 的类型
     * @return 复制的 map 对象
     */
    @SuppressWarnings("all")
    public static <K, V, W> Map<K, W> runMap(Map<K, V> data, Class<W> wClass, Map<K, W> def) {
        try {
            if (data == null || wClass == null || !isMapType(data.getClass())) {
                return deepClone(def);
            } else {
                final Map<K, W> result = new HashMap<>();
                for (K k : data.keySet()) {
                    // 获取当前数据的类型
                    final V v = data.get(k);
                    final Class<?> dClass = v.getClass();
                    if (dClass == wClass) {
                        if (isListType(dClass) || isListType(wClass)
                                || isMapType(dClass) || isMapType(wClass)) {
                            throw new RuntimeException("data map copy to " + wClass + ", does not support nested list/map");
                        } else {
                            // 写入数据
                            result.put(k, deepClone((W) v));
                        }
                    } else {
                        if (isSerializable(dClass)
                                && isSerializable(wClass)
                                && !isMapType(dClass) && !isMapType(wClass)
                                && !isListType(dClass) && !isListType(wClass)) {
                            // 类型不同且接口 serializable 不为 list
                            result.put(k, run(v, wClass, null));
                        }
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return deepClone(def);
        }
    }

    /**
     * 将对象转换为 map 对象
     * @param o 参数对象
     * @return 转换的对象
     */
    public static Map<String, Object> toMap(final Object o) {
        return toMap(o, null);
    }

    /**
     * 将对象转换为 map 对象
     * @param o 参数对象
     * @param def 如果异常或者 null 返回的对象
     * @return 转换的对象
     */
    public static Map<String, Object> toMap(final Object o, final Map<String, Object> def) {
        if (o == null) {
            return deepClone(def);
        } else {
            try {
                // 读取的参数的类型
                final Class<?> oClass = o.getClass();
                // 判断是否接口于 java.io.Serializable
                if (isSerializable(oClass)) {
                    // 创建返回对象
                    final Map<String, Object> rMap;
                    if (isMapType(oClass)) {
                        final Map<?, ?> mo = (Map<?, ?>) o;
                        final Class<?>[] go = getGenericClass(oClass.getTypeName());
                        // 只处理 key 为基础变量且不为 object 的 map 数据
                        if (go.length == 2 && go[0] != Object.class && isBaseType(go[0])) {
                            rMap = new HashMap<>(mo.size());
                            for (final Object key : mo.keySet()) {
                                final Object value = mo.get(key);
                                // 判断是否为基础类型
                                if (isBaseType(value.getClass())) {
                                    rMap.put(String.valueOf(key), deepClone(value));
                                } else {
                                    // 判断是否为 list 类型
                                    if (isListType(value.getClass())) {
                                        rMap.put(String.valueOf(key), toList(value, null));
                                    } else {
                                        rMap.put(String.valueOf(key), toMap(value, null));
                                    }
                                }
                            }
                        } else {
                            // 错误情况下赋值
                            rMap = deepClone(def);
                        }
                    } else {
                        final Field[] fields = getFields(oClass);
                        rMap = new HashMap<>(fields.length);
                        for (Field field : fields) {
                            field.setAccessible(true);
                            final Class<?> fieldClass = field.getType();
                            // 判断是否为基础类型
                            if (isBaseType(fieldClass)) {
                                rMap.put(field.getName(), deepClone(field.get(o)));
                            } else {
                                // 判断是否为 list 类型
                                if (isListType(fieldClass)) {
                                    rMap.put(field.getName(), toList(o, null));
                                } else {
                                    rMap.put(field.getName(), toMap(o, null));
                                }
                            }
                        }
                    }
                    return rMap;
                }
                // 如果参数没有接口 java.io.Serializable 就抛出运行异常
                throw new RuntimeException(oClass.getName() + " copy no interface java.io.Serializable");
            } catch (Exception e) {
                e.printStackTrace();
                return deepClone(def);
            }
        }
    }

    /**
     * 将 list<object> 转换为 list<map<string, object> 对象
     * @param o 待转换对象
     * @return 转换的对象
     */
    public static List<Object> toList(final Object o) {
        return toList(o, null);
    }

    /**
     * 将 list<object> 转换为 list<map<string, object> 对象
     * @param o 待转换对象
     * @param def 如果异常或者 null 返回的对象
     * @return 转换的对象
     */
    @SuppressWarnings("all")
    public static List<Object> toList(final Object o, final List<Object> def) {
        try {
            if (o == null || isListType(o.getClass())) {
                return deepClone(def);
            } else {
                final List<Object> list = (List<Object>) o;
                // 创建结果返回对象
                final List<Object> result = new ArrayList<>();
                for (final Object item : list) {
                    // 获取当前数据的类型
                    final Class<?> iClass = item.getClass();
                    if (isBaseType(iClass)) {
                        result.add(deepClone(item));
                    } else {
                        if (isListType(iClass)) {
                            result.add(toList(item, null));
                        } else {
                            result.add(toMap(item, null));
                        }
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return deepClone(def);
        }
    }

    /**
     * 判断是否为基础类型
     * @param cl class 类型
     * @return 是否为基础类型
     */
    private static boolean isBaseType(Class<?> cl) {
        final String clName = cl.getTypeName();
        switch (clName) {
            case "java.lang.Object":
            case "java.lang.String":
            case "java.lang.Character":
            case "char":
            case "java.lang.Short":
            case "short":
            case "java.lang.Integer":
            case "int":
            case "java.lang.Double":
            case "double":
            case "java.lang.Long":
            case "long":
            case "java.lang.Float":
            case "float":
            case "java.lang.Boolean":
            case "boolean":
            case "java.lang.Byte":
            case "byte":
                return true;
            default:
                return false;
        }
    }

    /**
     * 判断是否为 list 类型
     * @param cl class 类型
     * @return 是否为 list 类型
     */
    private static boolean isListType(Class<?> cl) {
        final String clName = cl.getTypeName();
        final Class<?>[] cls = cl.getInterfaces();
        if (cls.length > 0) {
            for (Class<?> clazz : cls) {
                if (clazz == List.class) {
                    return true;
                }
            }
        }
        return "java.util.List".equals(clName);
    }

    /**
     * 判断是否为 map 类型
     * @param cl class 类型
     * @return 是否为 map 类型
     */
    private static boolean isMapType(Class<?> cl) {
        final String clName = cl.getTypeName();
        return "java.util.Map".equals(clName);
    }

    /**
     * 判断是否接口 java.io.Serializable
     * @param cl class 类型
     * @return 是否接口 java.io.Serializable
     */
    private static boolean isSerializable(Class<?> cl) {
        final Class<?>[] cls = cl.getInterfaces();
        if (cls.length > 0) {
            for (Class<?> clazz : cls) {
                if (clazz == Serializable.class) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 深度复制
     * @return 深度复制的对象
     */
    @SuppressWarnings("all")
    public static <T> T deepClone(final T t) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ByteArrayOutputStream bos = null;
        try {
            if (t == null) {
                return null;
            } else {
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(t);
                bis = new ByteArrayInputStream(bos.toByteArray());
                ois = new ObjectInputStream(bis);
                return (T) ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(ois);
            close(bis);
            close(oos);
            close(bos);
        }
    }

    /**
     * 关闭字节流方法
     * @param closeable 需要关闭的对象
     */
    private static void close(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取 field 数据
     * @param cl 读取的 class 对象
     * @return field 数据
     */
    private static Field[] getFields(final Class<?> cl) {
        // 读取数据
        Class<?> cls = cl;
        final List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            // 获取父类对象
            cls = cls.getSuperclass();
        } while (cls != null && cls != Object.class && isSerializable(cls));
        // 转换为数组返回
        // 排除 static 和 transient 修饰符，修饰的 field 的对象
        return fields.stream().filter(f ->
                !(Modifier.isStatic(f.getModifiers()) || Modifier.isTransient(f.getModifiers()))
        ).toArray(Field[]::new);
    }

    /**
     * 读取泛型的类型
     * @param type 类型
     * @return 泛型的类型
     */
    private static Class<?>[] getGenericClass(final Type type) {
        return getGenericClass(type.getTypeName());
    }

    /**
     * 读取泛型的类型
     * @param typeName 类型名称
     * @return 泛型的类型
     */
    private static Class<?>[] getGenericClass(final String typeName) {
        try {
            final int a = typeName.indexOf("<");
            final int b = typeName.lastIndexOf(">");
            if (a != -1 && b != -1 && a < b) {
                final String[] genericNames = typeName.substring(a + 1, b).split(",");
                final Class<?>[] genericClass = new Class<?>[genericNames.length];
                for (int i = 0; i < genericNames.length; i++) {
                    genericClass[i] = Class.forName(genericNames[i].trim());
                }
                return genericClass;
            } else {
                return new Class<?>[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Class<?>[0];
        }
    }
}


