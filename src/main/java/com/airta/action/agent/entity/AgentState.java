package com.airta.action.agent.entity;

import java.io.Serializable;

/**
 * @author allenyin
 */
public class AgentState implements Serializable {

    private String currentUrl;
    private String currentTitle;
    private String state;
    private String pageSource;
    private String screenshot;
    private String jslog;

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPageSource() {
        return pageSource;
    }

    public void setPageSource(String pageSource) {
        this.pageSource = pageSource;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getJslog() {
        return jslog;
    }

    public void setJslog(String jslog) {
        this.jslog = jslog;
    }
}
