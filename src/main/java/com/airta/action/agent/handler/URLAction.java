package com.airta.action.agent.handler;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.List;

public class URLAction {

    public static URLAction urlAction = null;

    public static URLAction actionBuilder() {
        if (urlAction == null) {
            urlAction = new URLAction();
        }
        return urlAction;
    }

    public static void main(String[] args) {

        System.out.println(URLAction.actionBuilder().isAccessibleURLAddress("www.asa.com/Article/List/891-889"));
    }

    public boolean isAccessibleURLAddress(String originalUrl) {

//        List<String> knownPageList = fileUtil.readPredefinedUrlList("config/knownpage.txt");
//        List<String> errorPageList = fileUtil.readPredefinedUrlList("config/errorpage.txt");

//        if(isInKnownPageList(knownPageList, originalUrl) || isInKnownPageList(errorPageList, originalUrl)) {
//            return false;
//        }
        return !isScriptUrl(originalUrl);
    }

    private boolean isInKnownPageList(List<String> pageList, String originalUrl) {

        if(pageList.isEmpty()) {
            return false;
        }
        for(String page: pageList) {
            if(originalUrl.contains(page)) {
                return true;
            }
        }
        return false;
    }

    private boolean isScriptUrl(String originUrl) {
        return originUrl.contains("javascript:") || originUrl.endsWith(".do") || originUrl.endsWith("/javascript:") || originUrl.endsWith(".js");
    }

    public boolean isURLSchemaValid(String originalUrl) {

        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (urlValidator.isValid(originalUrl)) {
            return true;
        } else {
            return false;
        }
    }
}
