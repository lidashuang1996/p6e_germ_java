package com.p6e.germ.security.config;

import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.P6eJsonUtil;
import com.p6e.germ.starter.config.P6eConfig;
import com.p6e.germ.starter.config.P6eConfigSecurity;
import com.p6e.germ.starter.security.P6eAopSecurityAbstract;
import com.p6e.germ.starter.security.P6eSecurityApplication;
import com.p6e.germ.starter.security.P6eSecurityDataDefaultRequest;
import com.p6e.germ.starter.security.P6eSecurityModel;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Aspect
@Order(20)
@Component
public class P6eSecurityConfig extends P6eAopSecurityAbstract {

    /**
     * 配置需要拦截的包的路径
     */
    @Override
    @Pointcut("execution(public * com.p6e.germ.security.controller.*.*(..))")
    public void interceptor() {
    }


    /**
     * 将从传入的数据中获取 P6eSecurityModel 对象
     * @param httpServletRequest HTTP 对象
     * @param objects 传入的数据对象
     * @return P6eSecurityModel 对象
     */
    @Override
    public P6eSecurityModel execute(HttpServletRequest httpServletRequest, Object[] objects) {
        final Object object = httpServletRequest.getAttribute("AUTH");
        if (object instanceof P6eAuthModel) {
            final P6eSecurityModel securityModel = new P6eSecurityModel();
            final List<P6eSecurityModel.Jurisdiction> jurisdictionList = new ArrayList<>();
            final P6eAuthModel authModel = (P6eAuthModel) object;
            for (final Map<String, String> map : authModel.getJurisdictionList()) {
                try {
                    final String code = map.get("code").toUpperCase();
                    final String param = map.get("param").toUpperCase();
                    final String content = map.get("content").toUpperCase();
                    final Map<String, String> contentMap =
                            P6eJsonUtil.fromJsonToMap(content, String.class, String.class);
                    if (contentMap.get(param) != null) {
                        jurisdictionList.add(P6eSecurityModel.buildJurisdiction(code, param));
                    }
                } catch (Exception e) {
                    // 忽略异常
                }
            }
            securityModel.setJurisdictionList(jurisdictionList);
            return securityModel;
        }
        return null;
    }

    /**
     * 如果认证失败，回调返回输出的方法
     * @param httpServletRequest HTTP 对象
     * @return 回调返回结果
     */
    @Override
    public Object unauthorized(HttpServletRequest httpServletRequest) {
        return P6eResultModel.build(P6eResultConfig.ERROR_JURISDICTION_NO_EXISTENCE);
    }

    /**
     * Spring Boot 执行完成后立马执行的钩子函数
     * @param context 配置文件对象
     * @return 钩子函数对象
     */
    @Bean
    public ApplicationRunner runner(final WebServerApplicationContext context) {
        return args -> {
            final P6eConfig p6eConfig = context.getBean(P6eConfig.class);
            final P6eConfigSecurity.P6eConfigSecurityAchieveGroup group = p6eConfig.getSecurity().getGroup();
            final P6eConfigSecurity.P6eConfigSecurityAchieveJurisdiction jurisdiction = p6eConfig.getSecurity().getJurisdiction();
            final String gUrl;
            final String jUrl;
            if (group.getUrl() != null) {
                gUrl = group.getUrl();
            } else {
                throw new RuntimeException("group param null.");
            }
            if (jurisdiction.getUrl() != null) {
                jUrl = jurisdiction.getUrl();
            } else {
                throw new RuntimeException("jurisdiction param null.");
            }
            // 生成文件后不再生成文件
            P6eSecurityApplication.init(new P6eSecurityDataDefaultRequest(gUrl, jUrl));
            // 生成文件
            // P6eSecurityApplication.init(new P6eSecurityDataDefaultRequest(gUrl, jUrl),
            //         ()-> P6eSecurityApplication.generateJurisdictionFile("com.p6e.germ"));
        };
    }
}
