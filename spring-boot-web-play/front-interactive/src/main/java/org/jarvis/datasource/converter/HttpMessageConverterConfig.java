package org.jarvis.consumer.converter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;

/**
 * author:tennyson date:2020/6/24
 **/
@Configuration
public class HttpMessageConverterConfig {
    @Bean
    /*项目中没有提供是才是使用*/
    @ConditionalOnMissingBean
    public GsonHttpMessageConverter getGsonMessageConverter() {
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setDateFormat(DateFormat.AM_PM_FIELD);
        gsonBuilder.excludeFieldsWithModifiers(Modifier.PROTECTED);
        Gson gson=gsonBuilder.create();
        converter.setGson(gson);
        return converter;
    }
    @Bean
    /*项目中没有提供是才是使用*/
    @ConditionalOnMissingBean
    public FastJsonHttpMessageConverter getFastJsonMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig=new FastJsonConfig();
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
        return converter;
    }
}
