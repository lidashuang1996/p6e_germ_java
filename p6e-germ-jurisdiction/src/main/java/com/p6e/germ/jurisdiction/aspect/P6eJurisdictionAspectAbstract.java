package com.p6e.germ.jurisdiction.aspect;

import com.p6e.germ.jurisdiction.annotation.P6eJurisdiction;
import com.p6e.germ.jurisdiction.condition.P6eCondition;
import com.p6e.germ.jurisdiction.http.P6eHttpServlet;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eJurisdictionAspectAbstract
        <T extends P6eJurisdictionAspectModel> implements P6eJurisdictionAspectInterface<T> {

    /** 注入日志系统 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eJurisdictionAspectAbstract.class);

    /**
     * 读取方法的参数并替换为处理过后的参数
     * @param jp 代理对象
     * @param model 参数对象
     * @return 返回的处理过的参数对象
     */
    private static Object[] injectionData(final JoinPoint jp, final P6eJurisdictionAspectModel model) {
        // 获取参数参数值
        final Object[] args = jp.getArgs();
        // 参数遍历
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof P6eJurisdictionAspectModel) {
                args[i] = model;
            }
        }
        return args;
    }

    // 声明拦截器范围的注解
    // @Pointcut("")
    // private void interceptor(){}

    /**
     * 获取拦截的包的路径方法
     */
    public abstract void interceptor();

    /**
     * 前增强
     * 读取 interceptor() 方法上面的注解
     * @param joinPoint 参数对象
     */
    @Before("interceptor()")
    public void beforeInterceptor(final JoinPoint joinPoint) {
        before(joinPoint);
    }

    /**
     * 前运行结束增强
     * 读取 interceptor() 方法上面的注解
     * @param ret 参数对象
     */
    @AfterReturning(returning = "ret", pointcut = "interceptor()")
    public void afterReturningInterceptor(final Object ret) {
        afterReturning(ret);
    }

    /**
     * 后出现异常增强
     * 读取 interceptor() 方法上面的注解
     * @param jp 参数对象
     */
    @AfterThrowing("interceptor()")
    public void afterThrowsInterceptor(final JoinPoint jp){
        afterThrows(jp);
    }

    /**
     * 后增强
     * 读取 interceptor() 方法上面的注解
     * @param jp 参数对象
     */
    @After("interceptor()")
    public void afterInterceptor(final JoinPoint jp){
        after(jp);
    }

    /**
     * 环绕增强
     * 读取 interceptor() 方法上面的注解
     * @param pjp 参数对象
     * @return 环绕增强的结果
     * @throws Throwable 可能出现的异常
     */
    @Around("interceptor()")
    public Object aroundInterceptor(final ProceedingJoinPoint pjp) throws Throwable {
        return around(pjp);
    }

    /**
     * spring aop 切面前置增强
     * @param joinPoint 增强代理对象
     */
    public void before(final JoinPoint joinPoint) {
    }

    /**
     * spring aop 切面后置增强，不管结果如何都执行
     * @param ret 后置增强返回对象
     */
    public void afterReturning(final Object ret) {
    }

    /**
     * 拦截器增强过程中可能出现的异常
     * @param jp 增强代理对象
     */
    public void afterThrows(final JoinPoint jp) {
    }

    /**
     * spring aop 切面后置增强
     * @param jp 增强代理对象
     */
    public void after(final JoinPoint jp) {
    }

    /**
     * spring aop 切面环绕增强
     * @param pjp 切面对象
     * @return 动态代理后续执行的内容
     * @throws Throwable 后续执行过程中可能出现的问题
     */
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        // 获取请求的方法路径
        final String path = pjp.getThis().getClass()
                .getName().split("\\$\\$")[0] + "." + pjp.getSignature().getName() + "()";
        // 读取注解对象
        final P6eJurisdiction jurisdiction =
                ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(P6eJurisdiction.class);
        if (jurisdiction == null) {
            // 没有开启的权限模式
            LOGGER.debug("[ NO JURISDICTION MODE ] ==> " + path);
            return pjp.proceed(injectionData(pjp, null));
        } else {
            // 判断是否具备权限的状态
            boolean bool = false;
            // 执行认证方法
            final P6eJurisdictionAspectModel model =
                    this.execute(P6eHttpServlet.newInstance(), pjp.getArgs());
            // 判断是否具备权限
            final List<String> jurisdictionList = new ArrayList<>();
            // 添加判断的权限条件
            if (!"".equals(jurisdiction.value())) {
                jurisdictionList.add(jurisdiction.value());
            }
            jurisdictionList.addAll(Arrays.asList(jurisdiction.values()));
            // 读取条件
            final P6eCondition condition = jurisdiction.condition();
            switch (condition) {
                case AND:
                    bool = true;
                    for (final String item : jurisdictionList) {
                        if (!model.isHave(item)) {
                            bool = false;
                            break;
                        }
                    }
                    break;
                case OR:
                default:
                    for (final String item : jurisdictionList) {
                        // 其中一个符合条件，就通过
                        if (model.isHave(item)) {
                            bool = true;
                            break;
                        }
                    }
                    break;
            }
            if (bool) {
                LOGGER.debug("[ TRUE JURISDICTION ] ==> " + path);
                return pjp.proceed(injectionData(pjp, model));
            } else {
                LOGGER.debug("[ FALSE JURISDICTION ] ==> " + path);
                return this.error(P6eHttpServlet.newInstance());
            }
        }
    }
}
