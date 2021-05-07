package org.jarvis.consumer.model;

import java.util.List;

/**
 * @author marcus
 * @date 2020/8/19-14:26
 */
public class GrafanaJsonRoot {

    private int dashboardId;
    private List<org.jarvis.consumer.model.EvalMatches> evalMatches;
    private String imageUrl;
    private String message;
    private int orgId;
    private int panelId;
    private int ruleId;
    private String ruleName;
    private String ruleUrl;
    private String state;
    private Tags tags;
    private String title;

    public int getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(int dashboardId) {
        this.dashboardId = dashboardId;
    }

    public List<org.jarvis.consumer.model.EvalMatches> getEvalMatches() {
        return evalMatches;
    }

    public void setEvalMatches(List<org.jarvis.consumer.model.EvalMatches> evalMatches) {
        this.evalMatches = evalMatches;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleUrl() {
        return ruleUrl;
    }

    public void setRuleUrl(String ruleUrl) {
        this.ruleUrl = ruleUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
