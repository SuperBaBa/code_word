package org.edwin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author marcus
 * @date 2020/8/11-15:42
 */
@ConfigurationProperties(prefix = "jarvis.log")
@Component
public class AspectLogProperties {
    /**
     * your application name, will present your log
     */
    @NonNull
    private String appName;
    /**
     * officer's name
     */
    @NonNull
    private String captainName;
    @NonNull
    private String captainNumber;

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        Assert.notNull(captainName, "captainName must not be null");
        Assert.isTrue(captainName.length() > 1, "captainName must have length greater than 1");
        this.captainName = captainName;
    }

    public String getCaptainNumber() {
        return captainNumber;
    }

    public void setCaptainNumber(String captainNumber) {
        this.captainNumber = captainNumber;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        Assert.notNull(appName, "appName must not be null");
        Assert.isTrue(appName.length() > 1, "appName must have length greater than 1");
        this.appName = appName;
    }

    /**
     * whether enable aspect log
     */
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public AspectLogProperties(@NonNull String appName) {
        this.appName = appName;
    }

    public AspectLogProperties(@NonNull String appName, @NonNull String captainName, boolean enable) {
        this.appName = appName;
        this.captainName = captainName;
        this.enable = enable;
    }

    public AspectLogProperties() {
//        Assert.notNull(appName, "appName must not be null");
//        Assert.notNull(captainName, "captainName must not be null");
    }
}
