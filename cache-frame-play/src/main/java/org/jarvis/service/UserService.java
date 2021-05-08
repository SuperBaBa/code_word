package org.jarvis.service;

import org.jarvis.dao.UserMapper;
import org.jarvis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/4/29-17:30
 * @description this is function description
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    //    @Cached(name = "userCache-Id", key = "#id", expire = 3600, cacheType = CacheType.BOTH)
    public User getUserById(String id) {
        return userMapper.getUserById(Long.parseLong(id));
    }
}
