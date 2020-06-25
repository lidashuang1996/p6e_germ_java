package com.p6e.germ.oauth2.mybatis;

import com.p6e.germ.oauth2.model.base.P6eBaseDb;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 该类为 MyBatis 拦截器
 * 主要对密码参数进行加密处理
 *
 * 强制定义所有到 db 层做 CRUD 的操作，只能用对象且用 "DB" 作为参数的别名且必须继承基础的 db 类
 * 这样做的目的是为了更好的管理和操作 db 层的数据
 *
 * 1. mybatis-config.xml 文件配置
 * 2. spring boot 注解注入方式配置 @Component
 * 3. 采用 spring boot 的 yml 配置文件配置
 * 但是三者只能选择其中一个，不能重复配置，否者会重复加载拦截器
 *
 * @author LiDaShuang
 * @version 1.0
 */
@Intercepts(@Signature(type = Executor.class, method = "query",
        args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }))
public class MybatisPasswordInterceptor implements Interceptor {

    /**
     * 拦截器的核心方法
     * @param invocation 代理的对象
     * @return 继续执行的代理对象
     * @throws Throwable 拦截器可能出现的错误
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取参数
        Object param = invocation.getArgs()[1];
        if (param instanceof HashMap) {
            @SuppressWarnings("all") // 忽略提示
            HashMap hashMap = (HashMap) param;
            for (Object o : hashMap.keySet()) {
                if (o != null && "DB".equals(String.valueOf(o))) {
                    execute(hashMap.get(o));
                }
            }
            // 回写修改后的参数
            invocation.getArgs()[1] = hashMap;
        }
        return invocation.proceed();
    }

    /**
     * 执行关键字段加密的方法
     * @param param db 操作的参数对象
     * @throws IllegalAccessException 处理过程中可能出现的异常
     */
    private void execute(Object param) throws IllegalAccessException {
        if (param instanceof P6eBaseDb) {
            // 如过继承基础的对象表示是符合我们的对象类型的
            Class<?> paramClass = param.getClass();
            List<Field> fields = new ArrayList<>(Arrays.asList(paramClass.getDeclaredFields()));
            for (Field field : fields) {
                // 强制写入和读取字段的内容
                field.setAccessible(true);
                // 判断关键字段是否存在切不为 null
                if ("PASSWORD".equals(field.getName().toUpperCase()) && field.get(param) != null) {
                    // 加密处理并写入加密后的内容
                    field.set(param, MyBatisTool.encryption(field.get(param).toString()));
                }
            }
        }
    }

}
