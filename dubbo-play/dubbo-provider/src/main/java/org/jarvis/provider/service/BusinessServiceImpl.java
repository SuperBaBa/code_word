package org.jarvis.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.jarvis.web.service.BusinessService;

/**
 * @author Marcus
 * @date 2021/2/10-17:41
 * @description this is function
 */
@Service(group = "dubbo", version = "${demo.service.version}", loadbalance = "roundrobin", weight = 100)
public class BusinessServiceImpl implements BusinessService {
    @Override
    public int add(String num) {
        return Integer.valueOf(num);
    }
}
