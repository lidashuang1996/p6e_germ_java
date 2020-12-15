package com.p6e.germ.security.aspect;

import com.p6e.germ.security.config.P6eConfig;
import com.p6e.germ.security.config.P6eConfigSecurity;
import com.p6e.germ.security.model.P6eSecurityModel;
import com.p6e.germ.security.annotation.P6eSecurity;
import com.p6e.germ.security.http.P6eHttpServlet;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eSecurityAspectAbstract
        <T extends P6eSecurityModel> implements P6eSecurityAspectInterface<T> {

    /** 注入日志系统 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eSecurityAspectAbstract.class);

    /**
     * 判断路径是否属于这个名单中
     * @param path 需要判断的路径
     * @param list 名单数据
     * @return 判断的结果
     */
    private static boolean isList(final String path, final List<String> list) {
        final String[] ps = path.split("\\.");
        for (final String content : list) {
            boolean b = true;
            final String[] cs = content.split("\\.");
            // 规则长度大于路径长度肯定不符合
            if (cs.length > ps.length) {
                break;
            }
            // 长度只能永远是 cs 的长度
            int len = cs.length;
            for (int i = 0; i < len; i++) {
                if (!(ps[i].equals(cs[i])) && !"*".equals(cs[i])) {
                    b = false;
                    break;
                }
            }
            if (b) {
                return true;
            }
        }
        return false;
    }

    /**
     * 读取方法的参数并替换为处理过后的参数
     * @param jp 代理对象
     * @param model 参数对象
     * @return 返回的处理过的参数对象
     */
    private static Object[] injectionData(final JoinPoint jp, final P6eSecurityModel model) {
        // 获取参数参数值
        final Object[] args = jp.getArgs();
        // 参数遍历
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof P6eSecurityModel) {
                args[i] = model;
            }
        }
        return args;
    }

    /** 配置文件 */
    private final P6eConfig p6eConfig;

    /**
     * 构造方法
     * 读取的配置文件的数据
     * @param p6eConfig 配置文件对象
     */
    public P6eSecurityAspectAbstract(P6eConfig p6eConfig) {
        this.p6eConfig = p6eConfig;
        final P6eConfigSecurity p6eConfigSecurity = this.p6eConfig.getSecurity();
        LOGGER.info("========= SECURITY BLOCK LIST (START) =========");
        for (final String black : p6eConfigSecurity.getBlackList()) {
            LOGGER.info("SECURITY BLOCK ==> " + black);
        }
        LOGGER.info("========= SECURITY BLOCK LIST ( END ) =========");
        LOGGER.info("========= SECURITY WHITE LIST (START) =========");
        for (final String white : p6eConfigSecurity.getWhiteList()) {
            LOGGER.info("SECURITY WHITE ==> " + white);
        }
        LOGGER.info("========= SECURITY WHITE LIST ( END ) =========");
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
        if (isBlockList(path)) {
            // 是黑名单
            // 认证没通过
            LOGGER.debug("[ BLOCK LIST ] ==> " + path + ", no auth.");
            return this.unauthorized(P6eHttpServlet.newInstance());
        } else {
            final P6eSecurity security =
                    ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(P6eSecurity.class);
            if (security == null) {
                // 没有开启的认证模式
                LOGGER.debug("[ NO AUTH MODE ] ==> " + path);
                return pjp.proceed(injectionData(pjp, null));
            } else {
                // 执行认证方法
                final P6eSecurityModel p6eSecurityModel =
                        this.authentication(P6eHttpServlet.newInstance(), pjp.getArgs());
                if (isWhiteList(path)) {
                    LOGGER.debug("[ ENABLE AUTH MODE ] ==> " + path);
                    return pjp.proceed(injectionData(pjp, p6eSecurityModel));
                } else {
                    if (p6eSecurityModel == null) {
                        // 认证没通过
                        LOGGER.debug("[ NO AUTH PASSED ] ==> " + path);
                        return this.unauthorized(P6eHttpServlet.newInstance());
                    } else {
                        // 认证通过
                        LOGGER.debug("[ AUTH PASSED ] ==> " + path);
                        return pjp.proceed(injectionData(pjp, p6eSecurityModel));
                    }
                }
            }
        }
    }

    /**
     * 判断请求的路径是否为黑名单的数据
     * @param path 需要判断的路径
     * @return 判断的结果
     */
    private boolean isBlockList(final String path) {
        return isList(path, Arrays.asList(p6eConfig.getSecurity().getBlackList()));
    }

    /**
     * 判断请求的路径是否为白名单的数据
     * @param path 需要判断的路径
     * @return 判断的结果
     */
    private boolean isWhiteList(final String path) {
        return isList(path, Arrays.asList(p6eConfig.getSecurity().getWhiteList()));
    }

}
