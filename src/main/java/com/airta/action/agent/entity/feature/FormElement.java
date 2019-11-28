package com.airta.action.agent.entity.feature;

import com.airta.action.agent.entity.html.Element;

public class FormElement extends Element {

    private String action;
    private String onSubmit;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOnSubmit() {
        return onSubmit;
    }

    public void setOnSubmit(String onSubmit) {
        this.onSubmit = onSubmit;
    }
}
