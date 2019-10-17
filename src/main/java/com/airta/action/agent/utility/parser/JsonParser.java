package com.airta.action.agent.utility.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;

public class JsonParser {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Object resolveIncomingMessage(String value, Class objectClass) {

        log.info("message {} resolved. ", value);
        try {
            log.info(OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
            log.warn("# Not standard JSON payloads, try formatting..");

            try {

                value = OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(format(value));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                return null;
            }
            if (isJSONValid(value)) {
                log.info("## valid json input, convert to jenkinsObject");

                return new Gson().fromJson(value, objectClass);
            } else {
                log.warn("## not standard payloads, abort");
                return null;
            }
        }
    }

    /**
     * check if input JSON payload valid
     *
     * @param inputValue
     * @return boolean
     */
    public boolean isJSONValid(String inputValue) {
        inputValue = format(inputValue);
        try {
            new JSONObject(inputValue);
        } catch (JSONException ex) {
            try {
                new JSONArray(inputValue);
            } catch (JSONException ex1) {
                return false;
            }
        }
        log.info("## verified JSON input.");
        return true;
    }

    public boolean isJSONArray(String inputValue) {

        inputValue = format(inputValue);
        try {
            new JSONArray(inputValue);
        } catch (JSONException ex1) {
            return false;
        }
        log.info("## verified JSON Array input.");
        return true;
    }

    private String format(String input) {

        return StringUtils.trimAllWhitespace(input);
    }
}
