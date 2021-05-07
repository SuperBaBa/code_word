package org.jarvis.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * author:tennyson date:2020/6/18
 **/
@Component
public class DynamicEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            InputStreamReader in = new FileReader(new File("E:\\workspace\\marcus-spring-practice\\elasticjob_play\\src\\main\\resources\\application.properties"));
            Properties properties = new Properties();
            properties.load(in);
            PropertiesPropertySource propertySource = new PropertiesPropertySource("dynamic", properties);
            environment.getPropertySources().addLast(propertySource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
