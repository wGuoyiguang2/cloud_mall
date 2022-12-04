package com.hqtc.gateway.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makuan on 18-8-9.
 */
@Configuration
public class RouterFilter implements EnvironmentAware {

    @Value("${server.ignoreRouters}")
    private String routers;

    public List getRouterList() {
        return routerList;
    }

    public void setRouterList(List routerList) {
        this.routerList = routerList;
    }

    private List routerList = new ArrayList();

    @Override
    public void setEnvironment(Environment environment) {
        String[] rts = routers.split(",");
        for (String r:rts){
            routerList.add(r);
        }
    }
}
