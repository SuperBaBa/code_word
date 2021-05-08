package org.jarvis.consumer.ssl.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;

/**
 * @author marcus @date 2020/8/7
 **/
@Data
@Component
public class MatInterfaceUtil {
    @Value("${mat-url}")
    private String url;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UriComponents uriComponents;

    public HashMap accessMatInterface(String param, String channel, String paramDigest) {
        URI uri = uriComponents.expand(param, channel, paramDigest).toUri();
        return  restTemplate.postForObject(uri, null, HashMap.class);
    }

    @Bean
    public UriComponents buildURLTemplate() {
        return UriComponentsBuilder.fromUriString(url)
                .queryParam("param", "{param}")
                .queryParam("channel", "{paramDigest}")
                .queryParam("paramDigest", "{paramDigest}")
                .encode()
                .build();
    }
}
