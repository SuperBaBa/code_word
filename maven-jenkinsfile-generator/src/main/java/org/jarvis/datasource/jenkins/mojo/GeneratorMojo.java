package org.jarvis.consumer.jenkins.mojo;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author marcus
 * @date 2020/12/13-16:09
 */
@Mojo(name = "generator-JenkinsFile")
public class GeneratorMojo extends AbstractMojo {
    /**
     *
     */
    @Parameter(property = "deployInfo")
    private org.jarvis.consumer.jenkins.mojo.DeployInfo deployInfo;
    /**
     * Jenkinsfile需要输出的路径
     */
    @Parameter(property = "outputPath", defaultValue = "src/main/resources/Jenkinsfile")
    private String outputPath;
    /**
     * 钉钉ID,需要在jenkins上配置
     */
    @Parameter(property = "dingDingId", defaultValue = "dingDingId")
    private String dingDingId;
    /**
     * eolinker自动换测试分组ID，需要对应测试人员提供
     */
    @Parameter(property = "eolinkerGroupId", defaultValue = "000")
    private String eolinkerGroupId;
    @Parameter(property = "eolinkerServerUrl", defaultValue = "http://10.130.10.230/index.php//v2/api_studio/automated_test/test_case/execute_all")
    private String eolinkerServerUrl;
    /**
     * 应用jar包存放相对路径
     */
    @Parameter(property = "appTargetHome", defaultValue = "${project.artifactId}/target/")
    private String appTargetHome;
    /**
     * 应用jar包名称
     */
    @Parameter(property = "appName", defaultValue = "${project.build.finalName}")
    private String appName;
    /**
     * 部署机器的jdk绝对路径，默认不需要修改
     */
    @Parameter(property = "remoteJavaHome", defaultValue = "/opt/jdk1.8.0_45/bin/java")
    private String remoteJavaHome;
    /**
     * 部署机器的应用部署绝对路径，默认不需要修改
     */
    @Parameter(property = "remoteAppHome", defaultValue = "/opt/application/")
    private String remoteAppHome;
    /**
     * 需要安全控制的部署环境，安全控制的环境必须在jenkins配置机器访问密码凭证
     */
    @Parameter(property = "securityEnv", defaultValue = "pre,prd")
    private String securityEnv;

    @Parameter(property = "groupId", defaultValue = "${project.groupId}")
    private String groupId;
    @Parameter(property = "artifactId", defaultValue = "${project.artifactId}")
    private String artifactId;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        List<String> fileContents = readFile("/JenkinsfileTemplate.txt");
        File jenkinsFile = new File(outputPath);
        try (FileWriter fileWriter = new FileWriter(jenkinsFile)) {
            for (String fileContent : fileContents) {
                fileWriter.write(fileContent + "\n");
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLog().info("Hello My customized Plugin");
        getLog().info(deployInfo.toString());
        getLog().info(groupId);
        getLog().info(artifactId);
    }

    /**
     * 读取文件内容
     *
     * @param path
     * @return
     */
    private List<String> readFile(String path) {
        List<String> result = new ArrayList<>();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(this.getClass().getResourceAsStream(path), StandardCharsets.UTF_8);
            //创建字符流缓冲区
            BufferedReader bufr = new BufferedReader(reader);
            List<String> collect = bufr.lines().map(this::replaceVariable).collect(Collectors.toList());
            result.addAll(collect);
        } catch (Exception e) {
            getLog().error("read JenkinsfileTemplate error", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    getLog().error("close InputStreamReader  error", e);
                }
            }
        }
        return result;
    }

    private String replaceVariable(String str) {
        String result = "";
        if (StringUtils.isNotBlank(str)) {
            result = str.replace("#{dingdingId}", dingDingId);
            result = result.replace("#{eolinkerGroupId}", eolinkerGroupId);
            result = result.replace("#{eolinkerServerUrl}", eolinkerServerUrl);
            result = result.replace("#{appTargetHome}", appTargetHome);
            result = result.replace("#{appName}", appName);
            result = result.replace("#{remoteJavaHome}", remoteJavaHome);
            result = result.replace("#{remoteAppHome}", remoteAppHome);
            result = result.replace("#{securityEnv}", securityEnv);
            result = result.replace("#{env.sitBranch}", deployInfo.getSitBranch());
            result = result.replace("#{env.sitCredentialsId}", deployInfo.getSitCredentialsId());
            result = result.replace("#{env.sitSpringProfilesActive}", deployInfo.getSitSpringProfilesActive());
            result = result.replace("#{sitIpList}", Arrays.toString(deployInfo.getSitIpList()));
        }
        return result;
    }
}
