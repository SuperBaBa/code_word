package org.jarvis.consumer.ssl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.security.MD5Encoder;
import org.jarvis.consumer.ssl.dto.MatQueryParameterDTO;
import org.jarvis.consumer.ssl.util.MatInterfaceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author marcus @date 2020/8/7
 **/
@Service
public class ProcessParamService {

    @Autowired
    private MatInterfaceUtil matInterfaceUtil;
    @Value("${parentId:12345}")
    private String parentId;


    public HashMap processParam(String waybillNo, String channel) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MatQueryParameterDTO parameterDTO = MatQueryParameterDTO.builder().waybillNo(waybillNo).build();
        String jsongString = mapper.writeValueAsString(parameterDTO);
        return matInterfaceUtil.accessMatInterface(jsongString, channel, MD5Encoder.encode((jsongString + parentId).getBytes()));
    }
}

