package org.jarvis.dao;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import org.jarvis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/4/29-10:42
 */

public interface UserMapper extends JpaRepository<User, Integer> {
    @Cached(name = "userCache-Name", key = "#name", expire = 3600, cacheType = CacheType.BOTH)
    User findByName(String name);

    @Cached(name = "userCache-Id", key = "#id", expire = 3600, cacheType = CacheType.BOTH)
    User getUserById(Long id);
}
