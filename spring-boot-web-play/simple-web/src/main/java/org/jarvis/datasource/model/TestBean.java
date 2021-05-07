package org.jarvis.consumer.model;

/**
 * @author marcus
 * @date 2020/9/6-16:19
 */
public class TestBean {
    private String contextName;

    public TestBean(String contextName) {
        this.contextName = contextName;
    }

    public void hello() {
        System.out.println(contextName + "say hello");
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }
}
