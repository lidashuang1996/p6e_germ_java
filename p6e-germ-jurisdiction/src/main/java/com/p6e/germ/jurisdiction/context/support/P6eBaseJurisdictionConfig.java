package com.p6e.germ.jurisdiction.context.support;

import com.p6e.germ.common.http.P6eHttpServlet;
import com.p6e.germ.jurisdiction.aspect.P6eJurisdictionAspectAbstract;
import com.p6e.germ.jurisdiction.aspect.P6eJurisdictionAspectModel;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
@Aspect
@Order(20)
@Component
public class P6eBaseJurisdictionConfig extends P6eJurisdictionAspectAbstract<P6eJurisdictionAspectModel> {

    @Override
    @Pointcut("execution(* com.p6e.germ.jurisdiction.context.controller.*.* (..))")
    public void interceptor() {}

    @Override
    public P6eJurisdictionAspectModel authentication(P6eHttpServlet httpServlet, Object[] args) throws Throwable {


        return null;
    }
}
