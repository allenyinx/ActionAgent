package com.airta.action.agent.controller;


import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.config.CommonConfig;
import com.airta.action.agent.handler.RESTActionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author allenyin
 */
@RestController
@RequestMapping("/api")
public class AgentDefaultController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RESTActionRequest restActionRequest;

    @GetMapping(value = "/alive")
    public HttpStatus checkMessageStatus() {
        try {
            String message = "test for flow message";
            return HttpStatus.OK;
        } catch (Exception e) {
            logger.error("sending to kafka fail", e);
        }
        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping(value = "/agentState")
    public Object queryAgentState() {

        logger.info("## querying state of current agent ..");
        return restActionRequest.queryAgentState();
    }

    @PostMapping(value = "/recoverContext", produces = "application/json")
    @ResponseBody
    public Object recoverAgentContent(RawAction action) {
        String pagePath = action.getContext().getPagePath();
        logger.info("## expect to recover to page: {}", pagePath);
        restActionRequest.recoverAgentContext(action);
        return 200;
    }

    @PostMapping(value = "/rawAction", produces = "application/json")
    @ResponseBody
    public Object rawActionInAgent(RawAction action) {
        logger.info("## action {} on current agent ..", action.getAction().name());
        boolean actionExecuted = restActionRequest.runRawActionOnAgent(action);
        return actionExecuted;
    }

    @GetMapping(value = "/version")
    public Object checkVersion() {

        String currentTimestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        return "Phase" + CommonConfig.APP_PHASE + "_" + CommonConfig.APP_VERSION + "." + currentTimestamp;
    }

}
