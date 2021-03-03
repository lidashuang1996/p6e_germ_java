package com.p6e.germ.jurisdiction.aspect;

import com.p6e.germ.common.http.P6eHttpServlet;
import com.p6e.germ.jurisdiction.annotation.P6eJurisdiction;
import com.p6e.germ.jurisdiction.condition.P6eCondition;
import com.p6e.germ.jurisdiction.model.P6eJurisdictionModel;
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
 * 权限验证的抽象类
 * 泛型 T 为用户继承扩展的权限模型类
 * 通过继承该抽象类且定义扫描拦截范围来实现权限验证的功能
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eJurisdictionAspectAbstract
        <T extends P6eJurisdictionModel> implements P6eJurisdictionAspectInterface<T> {

    /** 是否开启 debug */
    private static boolean IS_DEBUG = false;

    /** 注入日志系统 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eJurisdictionAspectAbstract.class);

    /**
     * 打开 debug 模式
     */
    public static void openDebug() {
        IS_DEBUG = true;
    }

    /**
     * 关闭 debug 模式
     */
    public static void closeDebug() {
        IS_DEBUG = true;
    }

    /**
     * 读取方法的参数并替换为处理过后的参数
     * @param jp 代理对象
     * @param model 参数对象
     * @return 返回的处理过的参数对象
     */
    private static Object[] injectionData(final JoinPoint jp, final P6eJurisdictionModel model) {
        // 获取参数参数值
        final Object[] args = jp.getArgs();
        // 参数遍历
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof P6eJurisdictionModel) {
                args[i] = model;
            }
        }
        return args;
    }

    /**
     * 获取请求的路径
     * @param jp 代理对象
     * @return 请求的路径
     */
    private static String getPath(JoinPoint jp) {
        return jp.getThis().getClass().getName().split("\\$\\$")[0] + "." + jp.getSignature().getName() + "()";
    }

    /**
     * 获取注解对象
     * @param jp 代理对象
     * @return 注解的对象
     */
    private static P6eJurisdiction getJurisdiction(JoinPoint jp) {
        final P6eJurisdiction mJurisdiction = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(P6eJurisdiction.class);
        final P6eJurisdiction cJurisdiction = (P6eJurisdiction) jp.getSignature().getDeclaringType().getAnnotation(P6eJurisdiction.class);
        return mJurisdiction == null ? cJurisdiction : mJurisdiction;
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
     * @param jp 参数对象
     */
    @Before("interceptor()")
    public void beforeInterceptor(final JoinPoint jp) {
        before(jp);
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
     * @param jp 增强代理对象
     */
    public void before(final JoinPoint jp) {
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
        final String path = getPath(pjp);
        // 读取注解对象
        final P6eJurisdiction jurisdiction = getJurisdiction(pjp);
        if (jurisdiction == null) {
            // 如果没有权限对象，就表示没有开启的权限模式
            this.log("[ NO JURISDICTION MODE ] ==> " + path + ", direct access.");
            // 修改请求的参数，并返回
            return pjp.proceed(injectionData(pjp, null));
        } else {
            // 执行认证方法
            P6eJurisdictionModel p6eJurisdictionModel;
            try {
                p6eJurisdictionModel = this.authentication(P6eHttpServlet.newInstance(), pjp.getArgs());
            } catch (Exception e) {
                p6eJurisdictionModel = null;
            }
            if (p6eJurisdictionModel == null) {
                // 认证没通过
                this.log("[ JURISDICTION MODE ( NO PASSED ) ] ==> " + path);
                return this.error(P6eHttpServlet.newInstance(), ERROR_NO_JURISDICTION);
            } else {
                // 状态
                boolean bool = false;
                // 判断是否具备权限
                final List<String> jurisdictionList = new ArrayList<>();
                // 添加判断的权限条件
                if (!jurisdiction.value().isEmpty()) {
                    jurisdictionList.add(jurisdiction.value());
                }
                jurisdictionList.addAll(Arrays.asList(jurisdiction.values()));
                // 读取条件
                final P6eCondition condition = jurisdiction.condition();
                switch (condition) {
                    case AND:
                        bool = true;
                        for (final String item : jurisdictionList) {
                            if (!p6eJurisdictionModel.isExist(item)) {
                                bool = false;
                                break;
                            }
                        }
                        break;
                    case OR:
                    default:
                        for (final String item : jurisdictionList) {
                            // 其中一个符合条件，就通过
                            if (p6eJurisdictionModel.isExist(item)) {
                                bool = true;
                                break;
                            }
                        }
                        break;
                }
                if (bool) {
                    this.log("[ JURISDICTION MODE ( PASSED ) ] ==> " + path);
                    return pjp.proceed(injectionData(pjp, p6eJurisdictionModel));
                } else {
                    this.log("[ JURISDICTION MODE ( NO PASSED ) ] ==> " + path);
                    return this.error(P6eHttpServlet.newInstance(), ERROR_NO_JURISDICTION);
                }
            }
        }
    }

    /**
     * 打印日志信息
     * @param content 日志内容
     */
    private void log(String content) {
        if (IS_DEBUG) {
            LOGGER.info(content);
        }
    }
}
