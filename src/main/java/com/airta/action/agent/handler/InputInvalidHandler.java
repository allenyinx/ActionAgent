package com.airta.action.agent.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputInvalidHandler {

    private static InputInvalidHandler inputInvalidHandler = null;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static InputInvalidHandler getInstance() {

        if(inputInvalidHandler ==null) {
            inputInvalidHandler = new InputInvalidHandler();
        }
        return inputInvalidHandler;
    }

    public void run(Object key, Object value) {


    }
}
