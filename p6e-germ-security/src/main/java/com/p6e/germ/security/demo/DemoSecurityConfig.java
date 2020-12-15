package com.p6e.germ.security.demo;

import com.p6e.germ.security.annotation.P6eSecurityConfig;
import com.p6e.germ.security.aspect.P6eSecurityAspectAbstract;
import com.p6e.germ.security.config.P6eConfig;
import com.p6e.germ.security.http.P6eHttpServlet;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lidashuang
 * @version 1.0
 */
@Aspect
@P6eSecurityConfig
public class DemoSecurityConfig extends P6eSecurityAspectAbstract<DemoModel> {

    /** 注入日志系统 */
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoSecurityConfig.class);
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    /**
     * 构造方法
     * 读取的配置文件的数据
     *
     * @param p6eConfig 配置文件对象
     */
    @Autowired
    public DemoSecurityConfig(P6eConfig p6eConfig) {
        super(p6eConfig);
    }

    @Override
    @Pointcut("execution(public * com.p6e.germ.security.demo.DemoController.* (..))")
    public void interceptor() { }

    @Override
    public DemoModel authentication(P6eHttpServlet p6eHttpServlet, Object[] args) throws Throwable {
        // 通过读取 REDIS 的参数或者发送请求的方法
        // 获取用户的数据信息，这里省略
        // ....  获取信息
        // ....  判断是否认证成功
        final DemoModel model = new DemoModel();
        model.setId(1);
        model.setName("张三");
        model.setSex("男");
        model.setAge(12);
        LOGGER.info("args ==> " + Arrays.toString(args));
        LOGGER.info("HttpServletRequest ==> " + p6eHttpServlet.getHttpServletRequest().toString());
        LOGGER.info("HttpServletResponse ==> " + p6eHttpServlet.getHttpServletResponse().toString());
        // 模拟两次请求，一个成功一个错误
        return ATOMIC_INTEGER.incrementAndGet() % 2 == 0 ? model : null;
    }
}
