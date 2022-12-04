package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.config.Router;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: likai
 * @description description
 * @Date: 18-9-29 上午10:50
 */
@RestController
public class HealthController {

    @GetMapping(Router.API_HEALTHMONITOR)
    Map<String, Object> health() {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("code", 200);
        result.put("error", "ok");
        return result;
    }
}
