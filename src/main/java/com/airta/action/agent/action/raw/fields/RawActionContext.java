package com.airta.action.agent.action.raw.fields;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RawActionContext implements Serializable {

    @JsonProperty("pagePath")
    private String pagePath = "";

    @JsonProperty("session")
    private String session = "";

    private boolean shouldFetchChildren = false;
    private boolean shouldUpdateSiteMap = false;
    private boolean shouldKeptAlive = true;

    public String toString() {

        return "\n  [" + "\n" +
                "   pagePath: " + getPagePath() + "\n" +
                "   session: " + getSession().toString() + "\n" +
                "   shouldFetchChildren: " + isShouldFetchChildren() + "\n" +
                "   shouldUpdateSiteMap: " + isShouldUpdateSiteMap() + "\n" +
                "   shouldKeptAlive: " + isShouldKeptAlive() + "\n" +
                "  ]\n";
    }

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
