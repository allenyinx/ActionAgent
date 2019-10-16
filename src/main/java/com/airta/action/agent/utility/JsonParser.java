package com.airta.action.agent.utility;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class JsonParser {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public Object resolveIncomingMessage(String topic, String key, String value, Class objectClass) {

        log.info("topic {}, key {}, message {} resolved. ", topic, key, value);

        /**
         * may incoming JSON message
         */
        if (isJSONValid(value)) {
            log.info("## valid json input, convert to jenkinsObject");

            if (value.contains("\\")) {
                log.info("## payload contains escape chars: {}", value);
                value = value.replaceAll("\\\\", "##");
            }

            return new Gson().fromJson(value, objectClass);
        } else {
            log.warn("## not standard payloads, abort");
            return null;
        }
    }

    /**
     * check if input JSON payload valid
     *
     * @param inputValue
     * @return boolean
     */
    public boolean isJSONValid(String inputValue) {
        try {
            new JSONObject(inputValue);
        } catch (JSONException ex) {
            try {
                new JSONArray(inputValue);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public boolean isJSONArray(String inputValue) {

        try {
            new JSONArray(inputValue);
        } catch (JSONException ex1) {
            return false;
        }
        return true;
    }
}
