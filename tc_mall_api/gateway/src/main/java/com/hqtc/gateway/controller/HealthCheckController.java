package com.hqtc.gateway.controller;

import com.netflix.eureka.resources.ApplicationsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-9-14.
 */
@RestController
public class HealthCheckController {

    @RequestMapping(path = "/api/healthMonitor.php")
    public Object healthMonitor(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("error","ok");
        return map;
    }
}
