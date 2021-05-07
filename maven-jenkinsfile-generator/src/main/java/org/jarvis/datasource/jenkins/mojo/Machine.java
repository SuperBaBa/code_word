package org.jarvis.consumer.jenkins.mojo;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author marcus
 * @date 2020/12/14-11:08
 */
public class Machine {
    @Parameter(property = "hostName")
    private String hostName;
    @Parameter(property = "username")
    private String username;
    @Parameter(property = "password")
    private String password;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "'" + hostName + "|" + username + "|" + password + "'";
    }
}
