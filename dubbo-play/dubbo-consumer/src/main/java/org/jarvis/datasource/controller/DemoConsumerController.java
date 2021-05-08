package org.jarvis.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.jarvis.web.service.BusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcus
 * @date 2021/2/10-17:00
 * @description this is function
 */
@RestController
@RequestMapping(value = "/consumer")
public class DemoConsumerController {
    @Reference(version = "${demo.service.version}", loadbalance = "roundrobin")
    private BusinessService businessService;

    @GetMapping("/add/{num}")
    public String add(@PathVariable("num") String num) {
        return String.valueOf(businessService.add(num));
    }

}

