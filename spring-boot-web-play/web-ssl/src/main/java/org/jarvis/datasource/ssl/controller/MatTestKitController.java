package org.jarvis.consumer.ssl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.jarvis.consumer.ssl.service.ProcessParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author marcus @date 2020/8/7
 **/
@RestController
@Slf4j
public class MatTestKitController {
    @Autowired
    private ProcessParamService processParamService;
//    @Autowired
//    private LogService logService;

    @GetMapping("/mat")
    public HashMap matTest(@RequestParam String waybill, @RequestParam String channel) throws JsonProcessingException {
//        log.info(logService.getName());
        return processParamService.processParam(waybill, channel);
    }

    @GetMapping("/hey")
    public String sayHello(String name) {
//        log.info(logService.getName());
        return "Hello!" + name + "'s World ";
    }
}
