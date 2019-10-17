package com.airta.action.agent.action.raw.fields;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawActionData implements Serializable {

    private String actionData = "";
    private ElementLocation elementPath;

    public String toString() {

        return "\n  [" + "\n" +
                "   actionData: " + getActionData() + "\n" +
                "   elementPath: " + getElementPath().toString() + "\n" +
                "  ]\n";
    }

    public String getActionData() {
        return actionData;
    }

    public void setActionData(String actionData) {
        this.actionData = actionData;
    }

    public ElementLocation getElementPath() {
        return elementPath;
    }

    public void setElementPath(ElementLocation elementPath) {
        this.elementPath = elementPath;
    }
}
