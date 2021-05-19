package com.p6e.germ.security.aspect;

import com.p6e.germ.common.config.P6eSecurityConfig;
import com.p6e.germ.common.http.P6eHttpServlet;
import com.p6e.germ.security.annotation.P6eSecurity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 安全验证的抽象类
 * 泛型 T 为用户继承扩展的安全模型类
 * 通过继承该抽象类且定义扫描拦截范围来实现安全验证的功能
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eSecurityAspectAbstract
        <T extends P6eSecurityAspectModel> implements P6eSecurityAspectInterface<T> {

    /** 是否开启 debug */
    private static boolean IS_DEBUG = false;

    /** 注入日志系统 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eSecurityAspectAbstract.class);

    /** 配置文件 */
    private final P6eSecurityConfig p6eSecurityConfig;

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
    private static Object[] injectionData(final JoinPoint jp, final P6eSecurityAspectModel model) {
        // 获取参数参数值
        final Object[] args = jp.getArgs();
        // 参数遍历
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof P6eSecurityAspectModel) {
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
    private static P6eSecurity getSecurity(JoinPoint jp) {
        final P6eSecurity mSecurity = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(P6eSecurity.class);
        final P6eSecurity cSecurity = (P6eSecurity) jp.getSignature().getDeclaringType().getAnnotation(P6eSecurity.class);
        return mSecurity == null ? cSecurity : mSecurity;
    }

    /**
     * 构造方法
     * 读取的配置文件的数据
     * @param p6eSecurityConfig 配置文件对象
     */
    public P6eSecurityAspectAbstract(P6eSecurityConfig p6eSecurityConfig) {
        this.p6eSecurityConfig = p6eSecurityConfig;
        LOGGER.info("========= SECURITY BLOCK LIST (START) =========");
        for (final String black : p6eSecurityConfig.getBlackList()) {
            LOGGER.info("SECURITY BLOCK ==> " + black);
        }
        LOGGER.info("========= SECURITY BLOCK LIST ( END ) =========");
        LOGGER.info("========= SECURITY WHITE LIST (START) =========");
        for (final String white : p6eSecurityConfig.getWhiteList()) {
            LOGGER.info("SECURITY WHITE ==> " + white);
        }
        LOGGER.info("========= SECURITY WHITE LIST ( END ) =========");
    }

    /*
     *  // 声明拦截器范围的注解
     *  @Pointcut("")
     *  private void interceptor(){}
     */

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
     * 通过 Spring Aop 切面环绕增强
     * @param pjp 切面对象
     * @return 动态代理后续执行的内容
     * @throws Throwable 后续执行过程中可能出现的问题
     */
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        // 获取请求的方法路径
        final String path = getPath(pjp);
        // 判断是否为黑名单
        if (this.isBlockList(path)) {
            // 如果为黑名单，直接拦截，不允许访问
            this.log("[ BLOCK LIST ] ==> " + path + ", cannot access.");
            return this.error(P6eHttpServlet.newInstance(), ERROR_BLACK_LIST_PATH);
        } else {
            // 获取注解对象
            final P6eSecurity security = getSecurity(pjp);
            if (security == null) {
                // 如果没有安全对象，就表示没有开启的认证模式
                this.log("[ NO AUTH MODE ] ==> " + path + ", direct access.");
                // 修改请求的参数，并返回
                return pjp.proceed(injectionData(pjp, null));
            } else {
                // 执行认证方法
                P6eSecurityAspectModel p6eSecurityModel;
                try {
                    p6eSecurityModel = this.authentication(P6eHttpServlet.newInstance(), pjp.getArgs());
                } catch (Exception e) {
                    p6eSecurityModel = null;
                }
                // 判断是否为白名单
                if (isWhiteList(path)) {
                    this.log("[ WHITE LIST ] ==> " + path + ", direct access.");
                    return pjp.proceed(injectionData(pjp, p6eSecurityModel));
                } else {
                    if (p6eSecurityModel == null) {
                        // 认证没通过
                        this.log("[ AUTH MODE ( NO PASSED ) ] ==> " + path);
                        return this.error(P6eHttpServlet.newInstance(), ERROR_AUTH_INFO);
                    } else {
                        // 认证通过
                        this.log("[ AUTH MODE ( PASSED ) ] ==> " + path);
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
        return isList(path, Arrays.asList(this.p6eSecurityConfig.getBlackList()));
    }

    /**
     * 判断请求的路径是否为白名单的数据
     * @param path 需要判断的路径
     * @return 判断的结果
     */
    private boolean isWhiteList(final String path) {
        return isList(path, Arrays.asList(this.p6eSecurityConfig.getWhiteList()));
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
