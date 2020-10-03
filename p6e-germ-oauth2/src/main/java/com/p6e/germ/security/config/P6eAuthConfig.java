package com.p6e.germ.security.config;

import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.security.cache.P6eCacheAuth;
import com.p6e.germ.starter.config.P6eConfig;
import com.p6e.germ.starter.oauth2.P6eAopOauth2Abstract;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Oauth2 认证的配置
 * 下面为集成 spring-boot-starter(p6e) TEST 的代码
 * @author lidashuang
 * @version 1.0
 */
@Aspect
@Order(10)
@Component
public class P6eAuthConfig extends P6eAopOauth2Abstract<P6eAuthModel> {

    /** 认证头的名称 */
    private static final String AUTH_HEADER = "AUTHENTICATION";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    @Resource
    private P6eCacheAuth cacheAuth;

    /**
     * 构造方法注入配置文件
     * @param config 配置文件对象
     */
    @Autowired
    public P6eAuthConfig(final P6eConfig config) {
        super(config);
    }

    /**
     * 配置需要拦截的包的路径
     */
    @Override
    @Pointcut("execution(public * com.p6e.germ.security.oauth2.*.*(..))")
    public void interceptor() {
    }

    /**
     * 通过 HTTP 请求对象，用来处理的获取的用户信息的方法回调
     * @param httpServletRequest HTTP 对象
     * @return P6eAuthModel 对象
     */
    @Override
    @SuppressWarnings("all")
    public P6eAuthModel execute(final HttpServletRequest httpServletRequest) {
        final String data = httpServletRequest.getHeader(AUTH_HEADER);
        if (data != null) {
            if (data.startsWith(AUTH_HEADER_PREFIX)) {
                final P6eAuthModel result = new P6eAuthModel();
                try {
                    final Map<String, Object> param
                            = this.cacheAuth.getDataByToken(data.substring(AUTH_HEADER_PREFIX.length()));
                    if (param != null) {
                        result.setSex(String.valueOf(param.get("sex")));
                        result.setStatus(Integer.valueOf(String.valueOf(param.get("status"))));
                        result.setAvatar(String.valueOf(param.get("avatar")));
                        result.setName(String.valueOf(param.get("name")));
                        result.setBirthday(String.valueOf(param.get("birthday")));
                        result.setNickname(String.valueOf(param.get("nickname")));
                        result.setId(Integer.valueOf(String.valueOf(param.get("uid"))));
                        result.setRole(String.valueOf(param.get("role")));
                        result.setGroupList((List<Map<String, String>>) param.get("groupList"));
                        result.setJurisdictionList((List<Map<String, String>>) param.get("jurisdictionList"));
                        result.setToken(String.valueOf(param.get("token")));
                        result.setRefreshToken(String.valueOf(param.get("refreshToken")));
                        result.setExpirationTime(Long.valueOf(Double.valueOf(String.valueOf(param.get("expirationTime"))).intValue()));
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 如果认证失败，回调返回输出的方法
     * @param httpServletRequest HTTP 对象
     * @return 回调返回结果
     */
    @Override
    public Object unauthorized(final HttpServletRequest httpServletRequest) {
        return P6eResultModel.build(P6eResultConfig.ERROR_AUTH_NO_EXISTENCE);
    }
}
