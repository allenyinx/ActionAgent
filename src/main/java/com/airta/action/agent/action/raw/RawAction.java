package com.airta.action.agent.action.raw;

import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.action.raw.fields.RawActionData;
import com.airta.action.agent.action.raw.fields.RawActionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RawAction implements Serializable {

    private RawActionType action;
    private RawActionData data;
    private RawActionContext context;

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
}
