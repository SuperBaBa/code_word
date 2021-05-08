package org.jarvis.consumer.ssl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 通过前缀读取资源目录下的文件
 * 然后将值赋值给对应的属性
 * 分别有 yml 和 properties 两种文件
 * author:marcus date:2020/7/16
 **/

@ConfigurationProperties(prefix = "prefixname")
@Configuration
@PropertySource(value = {"classpath:person.properties"})

//@PropertySource(value = {"classpath:person.yml"})
//@ImportResource(locations = {"classpath*:person.properties"})
public class LoadPropertyConfiguration {
    private String second;
    private String first = "initialized.value";

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

   /* @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(new ClassPathResource("myconfig.yml"));
        yaml.setResources(new FileSystemResource("E:\\workspace\\marcus-spring-practice\\spring-family_play\\datasource-demo\\src\\main\\resources\\person.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }*/
}
