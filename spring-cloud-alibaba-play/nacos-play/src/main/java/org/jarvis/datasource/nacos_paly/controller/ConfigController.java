package org.jarvis.consumer.nacos_paly.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:tennyson date:2020/6/18
 **/
@RestController
public class ConfigController {

    @NacosValue(value = "${hello:false}", autoRefreshed = true)
    private String hello;

    @GetMapping(value = "/get")
    @ResponseBody
    public String get() {
        return hello;
    }
}
