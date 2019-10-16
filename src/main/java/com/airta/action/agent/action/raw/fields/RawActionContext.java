package com.airta.action.agent.action.raw.fields;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RawActionContext implements Serializable {

    private String pagePath = "";
    private String session = "";
    private boolean shouldFetchChildren = false;
    private boolean shouldUpdateSiteMap = false;
    private boolean shouldKeptAlive = true;

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean isShouldFetchChildren() {
        return shouldFetchChildren;
    }

    public void setShouldFetchChildren(boolean shouldFetchChildren) {
        this.shouldFetchChildren = shouldFetchChildren;
    }

    public boolean isShouldUpdateSiteMap() {
        return shouldUpdateSiteMap;
    }

    public void setShouldUpdateSiteMap(boolean shouldUpdateSiteMap) {
        this.shouldUpdateSiteMap = shouldUpdateSiteMap;
    }

    public boolean isShouldKeptAlive() {
        return shouldKeptAlive;
    }

    public void setShouldKeptAlive(boolean shouldKeptAlive) {
        this.shouldKeptAlive = shouldKeptAlive;
    }
}
