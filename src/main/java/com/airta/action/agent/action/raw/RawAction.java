package com.airta.action.agent.action.raw;

import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.action.raw.fields.RawActionData;
import com.airta.action.agent.action.raw.fields.RawActionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawAction implements Serializable {

    private String id;

    @JsonProperty("action")
    private RawActionType action;

    @JsonProperty("data")
    private RawActionData data;

    @JsonProperty("context")
    private RawActionContext context;

    public String toString() {

        return "\n[" + "\n" +
                " id: " + getId() + "\n" +
                " action: " + getAction().toString() + "\n" +
                " data: " + getData() + "\n" +
                " context: " + getContext() + "\n" +
                "]\n";
    }

    public RawActionType getAction() {
        return action;
    }

    public void setAction(RawActionType action) {
        this.action = action;
    }

    public RawActionData getData() {
        return data;
    }

    public void setData(RawActionData data) {
        this.data = data;
    }

    public RawActionContext getContext() {
        return context;
    }

    public void setContext(RawActionContext context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
