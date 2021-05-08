# spring-data系列

翻阅spring-data文档，发现spring-data系列分为了两个模块：

- [spring-framework-data-access](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html)
  模块
- [spring-data](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html) 模块

根据依赖关系可以查看到`spring-data`模块是依赖于`spring-framework-data`的，并且进行了增强，本工程的spring-data模块通过使用这些不同的数据持久层访问工具模块，对比出有何不同

```xml
<!--spring-framework框架的版本-->
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.3.6</version>
    </dependency>
<!--spring-framework框架的版本-->
    <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-jdbc</artifactId>
        <version>2.2.0</version>
    </dependency>
</dependencies>
```
