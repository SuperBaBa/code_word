package org.jarvis.consumer.concurrent.spring.controller;

import org.jarvis.consumer.concurrent.spring.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author:marcus date:2020/7/28
 **/
@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskService taskService;

    @RequestMapping("/hello")
    public String sayHello() {
        return "666";
    }

    @PostMapping("course")
    public String course(@RequestBody String context, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        logger.info(context);
        for (int i = 0; i < 10; i++) {
            taskService.asyncPrintString();
        }
        return "{\"name\":\"Mock测试2\"}";
    }

}
