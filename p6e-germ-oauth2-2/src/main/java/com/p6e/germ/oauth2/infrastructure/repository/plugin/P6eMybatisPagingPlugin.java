package com.p6e.germ.oauth2.infrastructure.repository.plugin;

import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.oauth2.model.base.P6eBaseDb;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MyBatis 拦截器
 * 1. 处理默认参数
 * 2. 设置查询的最大值
 *
 * 强制定义所有到 db 层做 CRUD 的操作，只能用对象且用 "DB" 作为参数的别名且必须继承基础的 db 类
 * 这样做的目的是为了更好的管理和操作 db 层的数据
 *
 * 1. mybatis-config.xml 文件配置
 * 2. spring boot 注解注入方式配置 @Component
 * 3. 采用 spring boot 的 yml 配置文件配置
 * 但是三者只能选择其中一个，不能重复配置，否者会重复加载拦截器
 *
 * @author lidashuang
 * @version 1.0
 */
@Component
@Intercepts(@Signature(type = Executor.class, method = "query",
        args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }))
public class P6eMybatisPagingPlugin implements Interceptor {

    /**
     * 默认的查询页码数
     *
     * 这里是可以采用注解方式注入参数
     * 这样就可以读取 spring uml 配置文件信息了
     * 也就是表示可以在配置文件中，配置这些你可能需要动态配置的参数了
     */
    private final int DEFAULT_PAGE;
    /** 默认的查询长度 */
    private final int DEFAULT_SIZE;
    /** 默认的查询最大长度 */
    private final int DEFAULT_MAX_SIZE;

    @Autowired
    public P6eMybatisPagingPlugin(P6eConfig p6eConfig) {
        DEFAULT_PAGE = p6eConfig.getDatabase().getDefaultPage();
        DEFAULT_SIZE = p6eConfig.getDatabase().getDefaultSize();
        DEFAULT_MAX_SIZE = p6eConfig.getDatabase().getDefaultMaxSize();
    }

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
        if (param == null) {
            final P6eBaseDb p6eBaseDb = new P6eBaseDb();
            execute(p6eBaseDb);
            invocation.getArgs()[1] = p6eBaseDb;
        } else {
            // 初始化一下分页信息
            execute(param);
        }
        return invocation.proceed();
    }

    /**
     * 初始化分页数据的方法
     * @param param db 操作的请求参数
     */
    private void execute(Object param) {
        if (param instanceof P6eBaseDb) {
            P6eBaseDb db = (P6eBaseDb) param;
            if (db.getPage() == null || db.getPage() <= 0) {
                db.setPage(DEFAULT_PAGE);
            }
            if (db.getSize() == null || db.getSize() < 0) {
                db.setSize(DEFAULT_SIZE);
            }
            if (db.getSize() > DEFAULT_MAX_SIZE) {
                db.setSize(DEFAULT_MAX_SIZE);
            }
            db.setEnd(db.getSize());
            db.setStart((db.getPage() - 1) * db.getSize());
        }
    }
}

