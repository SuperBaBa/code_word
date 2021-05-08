# what's marcus-spring-practice project
这个模块主要针对spring-web项目进行整合，其中包括ssl，对servlet的操作，辨析model、modelAndView
项目采用`初始化模块`和`数据模型分开`，需要初始化的模块需要资源文件的支持，实体模块可能需要字段映射文件支持，
整个项目可采用一套实体类模块。

本项目采用springboot当前(*2020-08-01*)最新版本，即spring-boot `2.3.2.RELEASE` 版本，替换版本可能存在一些未知的不兼容情况
例：我在进行reactive项目编程时，就遭遇到了Reactor 3 uses a BOM的Bismuth-RELEASE与spring-boot的2.3.2.RELEASE不兼容，always occur No such method.

[![一炮泯恩仇/marcus-spring-practice](https://gitee.com/SuperBaBa/marcus-spring-practice/widgets/widget_card.svg?colors=4183c4,ffffff,ffffff,e3e9ed,666666,9b9b9b)](https://gitee.com/SuperBaBa/marcus-spring-practice)

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
在代码进行测试时常使用`ApplicationRunner`和`CommandLineRunner`在主类的main方法后进行调用，即在Spring的ApplicationContext初始化完成之后执行
二者的区别在于获取环境变量或启动参数，一个是经过封装，另一个可直接获取。


