package org.jarvis.consumer.jenkins.mojo.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author marcus
 * @date 2020/12/17-20:30
 */
public class GeneratorEngine {
    private final static String templateDirectory = "E:\\workspace\\marcus-spring-practice\\maven-jenkinsfile-generator\\src\\main\\resources\\template";
    private final static String templateName = "JenkinsfileTemplate.ftl";
    private final static String outputPath = "E:\\workspace\\marcus-spring-practice\\maven-jenkinsfile-generator\\src\\main\\resources\\Jenkinsfile";

    private static Configuration getConfiguration(String templateDirectory) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }

    public static void main(String[] args) {
        try (Writer writer = new FileWriter(new File(outputPath))) {
            Map dataModel = new HashMap();
            dataModel.put("sit", "111");
            dataModel.put("uat", "222");
            Template template = getConfiguration(templateDirectory).getTemplate(templateName, "UTF-8");
            template.process(null, writer);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
