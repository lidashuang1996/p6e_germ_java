package com.p6e.germ.oauth2.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copy 的帮助类
 * 作用是的帮助我们 VO <-> DTO <-> DB 互相转换
 * @author LiDaShuang
 * @version 1.0
 */
public final class CopyUtil {


    @SuppressWarnings("all")
    public static <T> T run(Object data, Class<T> c) {
        if (data == null) return null; // 判断是否为 null
        try {
            boolean b1 = true, b2 = true;
            Class<?> dataClass = data.getClass();
            // 判断是否接口于 java.io.Serializable
            for (Class<?> anInterface : dataClass.getInterfaces())
                if (anInterface == Serializable.class) { b1 = false; break; }
            for (Class<?> anInterface : c.getInterfaces())
                if (anInterface == Serializable.class) { b2 = false; break; }
            if (b1 || b2) throw new RuntimeException("copy no interface java.io.Serializable");
            Field[] dataFields = obtainFields(dataClass); // 获取自类和父类里面的 Field
            Field[] cFields = obtainFields(c); // 获取自类和父类里面的 Field
            T t = c.newInstance(); // 创建一个源 class 对象
            for (Field f1 : dataFields) {
                for (Field f2 : cFields) {
                    // 根据名字匹配
                    if (f1.getName().equals(f2.getName())) {
                        f1.setAccessible(true);
                        f2.setAccessible(true);
                        Object o = f1.get(data); // 读取源数据对象
                        boolean bool = f1.getGenericType().equals(f2.getGenericType()); // 判断类型是否相同
                        if (bool && o != null) f2.set(t, o); // 对象不为 null 且类型相同，执行赋值操作
                        else if (!bool && o != null) {
                            System.out.println(f1.getGenericType().getTypeName());
                            System.out.println(f2.getGenericType().getTypeName());
                            if (f1.getGenericType().getTypeName().startsWith("java.util.List") &&
                                    f2.getGenericType().getTypeName().startsWith("java.util.List")) {
                                String genericF1 = "";
                                String genericF2 = "";
                                List<Object> list = new ArrayList<>();
                                switch (genericF2) {
                                    case "java.lang.String":
                                        ((List) o).forEach(item -> list.add(String.valueOf(item)));
                                        f2.set(t, list);
                                        break;
                                    case "java.lang.Character":
                                        ((List) o).forEach(item -> list.add(Character.valueOf(String.valueOf(item).charAt(0))));
                                        f2.set(t, list);
                                        break;
                                    case "java.lang.Short":
                                        ((List) o).forEach(item -> list.add(Short.valueOf(String.valueOf(item))));
                                        f2.set(t, list);
                                        break;
                                    case "java.lang.Integer":
                                        ((List) o).forEach(item -> list.add(Integer.valueOf(String.valueOf(item))));
                                        f2.set(t, list);
                                        break;
                                    case "java.lang.Double":
                                        ((List) o).forEach(item -> list.add(Double.valueOf(String.valueOf(item))));
                                        f2.set(t, list);
                                        break;
                                    case "java.lang.Long":
                                        ((List) o).forEach(item -> list.add(Long.valueOf(String.valueOf(item))));
                                        f2.set(t, list);
                                        break;
                                    case "java.lang.Float":
                                        ((List) o).forEach(item -> list.add(Float.valueOf(String.valueOf(item))));
                                        f2.set(t, list);
                                        break;
                                    case "java.lang.Boolean":
                                        ((List) o).forEach(item -> list.add(Boolean.valueOf(String.valueOf(item))));
                                        f2.set(t, list);
                                        break;
                                    case "java.lang.Byte":
                                        ((List) o).forEach(item -> list.add(Byte.valueOf(String.valueOf(item))));
                                        f2.set(t, list);
                                        break;
                                    default:
                                        // 如果类型不同，我们尝试再转换一次
                                        f2.set(t, run(o, Class.forName(f2.getGenericType().getTypeName())));
                                        break;
                                }

                            } else f2.set(t, run(o, Class.forName(f2.getGenericType().getTypeName()))); // 如果类型不同，我们尝试再转换一次
                        }
                        break;
                    }
                }
            }
            return t;
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Copy List<W> -> List<T>
     * @param data 源数据对象
     * @param c Copy 对象的 class
     * @param <W> 源数据对象的泛型
     * @param <T> Copy 数据对象的泛型
     * @return List<T>
     */
    public static <W, T> List<T> run(List<W> data, Class<T> c) {
        if (data == null) return null; // 判断是否为 null
        List<T> list = new ArrayList<>();
        // 读取每一个对象， COPY 后返回写入
        data.forEach(item -> list.add(run(item, c)));
        return list;
    }

    private static Field[] obtainFields(Class<?> cl) {
        List<Field> fields = new ArrayList<>(Arrays.asList(cl.getDeclaredFields()));
        Class<?> cls = cl.getSuperclass();
        while (cls != null && cls != Object.class) {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }

}


