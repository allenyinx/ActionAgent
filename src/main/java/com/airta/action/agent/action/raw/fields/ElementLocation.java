package com.airta.action.agent.action.raw.fields;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ElementLocation implements Serializable {

    private String type = "";
    private String value = "";

    public String toString() {

        return "\n    [" + "\n" +
                "     type: " + getType() + "\n" +
                "     value: " + getValue() + "\n" +
                "    ]\n";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
