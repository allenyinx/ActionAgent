package com.airta.action.agent.entity.html;

import java.util.ArrayList;
import java.util.List;

/**
 * Site map node element.
 * while contains depth, children, parent, status data.
 */
public class Element {

    private String elementId;
    private int depth = 0;
    private String type;
    private boolean actionable = true;
    private String parentId;
    private int childrenCount = 0;
    private List<Element> children = new ArrayList<>();
    private int updateCount = 0;
    private int accessedCount = 0;
    private boolean isWorkingOn = false;
    private String url;

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActionable() {
        return actionable;
    }

    public void setActionable(boolean actionable) {
        this.actionable = actionable;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public void increaseChildrenCount() {
        this.childrenCount++;
    }

    public List<Element> getChildren() {
        return children;
    }

    public void setChildren(List<Element> children) {
        this.children = children;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public int getAccessedCount() {
        return accessedCount;
    }

    public void setAccessedCount(int accessedCount) {
        this.accessedCount = accessedCount;
    }

    public boolean isWorkingOn() {
        return isWorkingOn;
    }

    public void setWorkingOn(boolean workingOn) {
        isWorkingOn = workingOn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
