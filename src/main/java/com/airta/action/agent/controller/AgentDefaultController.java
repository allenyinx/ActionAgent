package com.airta.action.agent.controller;


import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.config.CommonConfig;
import com.airta.action.agent.handler.RestActionRequest;
import com.airta.action.agent.message.ActionResultProducer;
import com.airta.action.agent.message.ResultProducer;
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
    private RestActionRequest restActionRequest;

    @Autowired
    private ActionResultProducer resultProducer;

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

    @GetMapping(value = "/agentState", produces = {"application/json"})
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

    @PostMapping(value = "/initBrowser")
    @ResponseBody
    public Object initBrowserStart(@RequestParam(name = "url") String startUrl) {

        logger.info("## init browser url to : {}", startUrl);
        if (restActionRequest.initBrowserStartPage(startUrl)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.GONE;
        }
    }

    @PostMapping(value = "/rawAction", produces = "application/json")
    @ResponseBody
    public Object rawActionInAgent(RawAction action) {

        if (action != null && action.getAction() != null) {

            logger.info("## action {} on current agent ..", action.getAction().name());
            boolean actionExecuted = restActionRequest.runRawActionOnAgent(action);
            return actionExecuted;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @PostMapping(value = "/immediateChildren", produces = "application/json")
    @ResponseBody
    public Object fetchImmediateChilds(RawAction action) {

        if (action != null && action.getAction() != null) {

            logger.info("## action {} on current agent ..", action.getAction().name());
            return restActionRequest.fetchImmediateChildPages(action);
        }
        return null;
    }

    @PostMapping(value = "/initSiteMap", produces = "application/json")
    @ResponseBody
    public Object initCrawlerForStartSiteMap() {

        String childElementContents = restActionRequest.fetchImmediateChildPages(null);
        ((ActionResultProducer) resultProducer).sendReportMessage("init", childElementContents);
        return childElementContents;
    }

    @GetMapping(value = "/version")
    public Object checkVersion() {

        String currentTimestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        return "Phase" + CommonConfig.APP_PHASE + "_" + CommonConfig.APP_VERSION + "." + currentTimestamp;
    }

}
