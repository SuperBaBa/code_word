package org.jarvis.consumer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * author:tennyson date:2020/6/22
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /*静态资源访问路径*/
    private String[] staticPathPattern = {"/js/**","css/**"};
    /*静态资源本地路径*/
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/**",
            "/classpath:/resources/**",
            "classpath:/static/**"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*handler中是服务器虚拟路径，jsp访问的目录，location中是相对应的本地路径*/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/images/");
        registry.addResourceHandler("/html/**")
                .addResourceLocations("classpath:/html/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteClassName, /*是否在生成的JSON中输出类名*/
                SerializerFeature.WriteMapNullValue, /*是否输出value为null的数据*/
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullListAsEmpty, /*空集合输出[]并非null*/
                SerializerFeature.WriteNullStringAsEmpty /*空字符串输出“”并非null*/
        );
        converter.setFastJsonConfig(fastJsonConfig);
        converters.add(converter);
    }
}
