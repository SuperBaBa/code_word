package org.jarvis.sqltask.controller;

import org.jarvis.sqltask.annotation.ChangeDS;
import org.jarvis.sqltask.entity.User;
import org.jarvis.sqltask.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marcus
 * @date 2020/11/16-17:09
 */
@RestController
@RequestMapping(value = "dao")
public class UserController {
    @Autowired
    private UserMapper mapper;

    @RequestMapping(value = "mall")
    @ChangeDS(value = "mall")
    public String savePrivateUser() {
        return mapper.selectUserAllList().toString();
    }

    @RequestMapping(value = "public")
    @ChangeDS(value = "default")
    public String savePublicUser() {
        User user = new User();
        user.setId(1);
        user.setName("这是公有库");
        return "success" + mapper.insertUser(user);
    }
}
