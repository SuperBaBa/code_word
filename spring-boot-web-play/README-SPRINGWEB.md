# web_play
这个模块主要针对spring-web项目进行整合，其中包括ssl，对servlet的操作，辨析model、modelAndView
项目采用`初始化模块`和`数据模型分开`，需要初始化的模块需要资源文件的支持，实体模块可能需要字段映射文件支持，
整个项目可采用一套实体类模块。
[![Marcus's github stats](https://github-readme-stats.vercel.app/api?username=superbaba)](https://github.com/SuperBaBa/marcus-spring-practice)
```xml
<!--版本资源管理-->
<properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <java.version>1.8</java.version>
    <spring-boot.version>2.3.2.RELEASE</spring-boot.version>
    <spring-cloud.version>Hoxton.SR6</spring-cloud.version>
</properties>
    
<dependencyManagement>
    <dependencies>
        <!--springboot依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <!--springCloud版本管理-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
