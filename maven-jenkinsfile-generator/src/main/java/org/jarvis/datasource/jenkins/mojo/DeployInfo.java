package org.jarvis.consumer.jenkins.mojo;

import org.apache.maven.plugins.annotations.Parameter;

import java.util.Arrays;

/**
 * @author marcus
 * @date 2020/12/14-10:27
 */
public class DeployInfo {
    /**
     *
     */
    private String sitBranch;
    /**
     *
     */
    @Parameter(property = "sitCredentialsId")
    private String sitCredentialsId;
    /**
     *
     */
    @Parameter(property = "sitSpringProfilesActive")
    private String sitSpringProfilesActive;
    /**
     *
     */
    @Parameter(property = "sitIpList")
    private org.jarvis.consumer.jenkins.mojo.Machine[] sitIpList;
    /**
     *
     */
    @Parameter(property = "uatBranch")
    private String uatBranch;
    /**
     *
     */
    @Parameter(property = "uatCredentialsId")
    private String uatCredentialsId;
    /**
     *
     */
    @Parameter(property = "uatSpringProfilesActive")
    private String uatSpringProfilesActive;
    /**
     *
     */
    @Parameter(property = "uatIpList")
    private org.jarvis.consumer.jenkins.mojo.Machine[] uatIpList;
    /**
     *
     */
    @Parameter(property = "preBranch")
    private String preBranch;
    /**
     *
     */
    @Parameter(property = "preCredentialsId")
    private String preCredentialsId;
    /**
     *
     */
    @Parameter(property = "preSpringProfilesActive")
    private String preSpringProfilesActive;
    /**
     *
     */
    @Parameter(property = "preIpList")
    private org.jarvis.consumer.jenkins.mojo.Machine[] preIpList;
    /**
     *
     */
    @Parameter(property = "proBranch")
    private String proBranch;
    /**
     *
     */
    @Parameter(property = "proCredentialsId")
    private String proCredentialsId;
    /**
     *
     */
    @Parameter(property = "proSpringProfilesActive")
    private String proSpringProfilesActive;
    /**
     *
     */
    @Parameter(property = "proIpList")
    private org.jarvis.consumer.jenkins.mojo.Machine[] proIpList;

    public String getSitBranch() {
        return sitBranch;
    }

    public void setSitBranch(String sitBranch) {
        this.sitBranch = sitBranch;
    }

    public String getSitCredentialsId() {
        return sitCredentialsId;
    }

    public void setSitCredentialsId(String sitCredentialsId) {
        this.sitCredentialsId = sitCredentialsId;
    }

    public String getSitSpringProfilesActive() {
        return sitSpringProfilesActive;
    }

    public void setSitSpringProfilesActive(String sitSpringProfilesActive) {
        this.sitSpringProfilesActive = sitSpringProfilesActive;
    }

    public org.jarvis.consumer.jenkins.mojo.Machine[] getSitIpList() {
        return sitIpList;
    }

    public void setSitIpList(org.jarvis.consumer.jenkins.mojo.Machine[] sitIpList) {
        this.sitIpList = sitIpList;
    }

    public String getUatBranch() {
        return uatBranch;
    }

    public void setUatBranch(String uatBranch) {
        this.uatBranch = uatBranch;
    }

    public String getUatCredentialsId() {
        return uatCredentialsId;
    }

    public void setUatCredentialsId(String uatCredentialsId) {
        this.uatCredentialsId = uatCredentialsId;
    }

    public String getUatSpringProfilesActive() {
        return uatSpringProfilesActive;
    }

    public void setUatSpringProfilesActive(String uatSpringProfilesActive) {
        this.uatSpringProfilesActive = uatSpringProfilesActive;
    }

    public org.jarvis.consumer.jenkins.mojo.Machine[] getUatIpList() {
        return uatIpList;
    }

    public void setUatIpList(org.jarvis.consumer.jenkins.mojo.Machine[] uatIpList) {
        this.uatIpList = uatIpList;
    }

    public String getPreBranch() {
        return preBranch;
    }

    public void setPreBranch(String preBranch) {
        this.preBranch = preBranch;
    }

    public String getPreCredentialsId() {
        return preCredentialsId;
    }

    public void setPreCredentialsId(String preCredentialsId) {
        this.preCredentialsId = preCredentialsId;
    }

    public String getPreSpringProfilesActive() {
        return preSpringProfilesActive;
    }

    public void setPreSpringProfilesActive(String preSpringProfilesActive) {
        this.preSpringProfilesActive = preSpringProfilesActive;
    }

    public org.jarvis.consumer.jenkins.mojo.Machine[] getPreIpList() {
        return preIpList;
    }

    public void setPreIpList(org.jarvis.consumer.jenkins.mojo.Machine[] preIpList) {
        this.preIpList = preIpList;
    }

    public String getProBranch() {
        return proBranch;
    }

    public void setProBranch(String proBranch) {
        this.proBranch = proBranch;
    }

    public String getProCredentialsId() {
        return proCredentialsId;
    }

    public void setProCredentialsId(String proCredentialsId) {
        this.proCredentialsId = proCredentialsId;
    }

    public String getProSpringProfilesActive() {
        return proSpringProfilesActive;
    }

    public void setProSpringProfilesActive(String proSpringProfilesActive) {
        this.proSpringProfilesActive = proSpringProfilesActive;
    }

    public org.jarvis.consumer.jenkins.mojo.Machine[] getProIpList() {
        return proIpList;
    }

    public void setProIpList(org.jarvis.consumer.jenkins.mojo.Machine[] proIpList) {
        this.proIpList = proIpList;
    }

    @Override
    public String toString() {
        return "DeployInfo{" +
                "sitBranch='" + sitBranch + '\'' +
                ", sitCredentialsId='" + sitCredentialsId + '\'' +
                ", sitSpringProfilesActive='" + sitSpringProfilesActive + '\'' +
                ", sitIpList=" + Arrays.toString(sitIpList) +
                ", uatBranch='" + uatBranch + '\'' +
                ", uatCredentialsId='" + uatCredentialsId + '\'' +
                ", uatSpringProfilesActive='" + uatSpringProfilesActive + '\'' +
                ", uatIpList=" + Arrays.toString(uatIpList) +
                ", preBranch='" + preBranch + '\'' +
                ", preCredentialsId='" + preCredentialsId + '\'' +
                ", preSpringProfilesActive='" + preSpringProfilesActive + '\'' +
                ", preIpList=" + Arrays.toString(preIpList) +
                ", proBranch='" + proBranch + '\'' +
                ", proCredentialsId='" + proCredentialsId + '\'' +
                ", proSpringProfilesActive='" + proSpringProfilesActive + '\'' +
                ", proIpList=" + Arrays.toString(proIpList) +
                '}';
    }
}
