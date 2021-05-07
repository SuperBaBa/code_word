package org.jarvis.controller;

import org.jarvis.entity.User;
import org.jarvis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/4/29-16:31
 * @description this is function description
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "getUserById/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }
}
