package com.p6e.germ.jurisdiction.context.support;

import com.p6e.germ.jurisdiction.annotation.P6eJurisdictionConfig;
import com.p6e.germ.jurisdiction.aspect.P6eJurisdictionAspectAbstract;
import com.p6e.germ.jurisdiction.aspect.P6eJurisdictionAspectModel;
import com.p6e.germ.jurisdiction.http.P6eHttpServlet;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;

/**
 * @author lidashuang
 * @version 1.0
 */
@Aspect
@P6eJurisdictionConfig
public class P6eBaseJurisdictionConfig extends P6eJurisdictionAspectAbstract<P6eJurisdictionAspectModel> {

    @Override
    @Pointcut("execution(public * com.p6e.germ.jurisdiction.context.controller.*.* (..))")
    public void interceptor() {}

    @Override
    public P6eJurisdictionAspectModel execute(P6eHttpServlet p6eHttpServlet, Object[] args) throws Throwable {
        P6eJurisdictionAspectModel p6eJurisdictionAspectModel = new P6eJurisdictionAspectModel(new ArrayList<>());
        return p6eJurisdictionAspectModel;
    }
}
