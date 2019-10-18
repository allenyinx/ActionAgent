package com.airta.action.agent.utility.parser;

import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.handler.URLAction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser {

    private static final Logger logger = LoggerFactory.getLogger(HtmlParser.class);

    private static Document doc = null;

    /**
     * @param pageContent target URL
     */
    public static List<String> parseChildLinks(String pageContent) {
        List<String> urlList = new ArrayList<>();

        try {
            doc = Jsoup.parse(pageContent);
        } catch (Exception e) {
            // Received failure 404
            logger.error(e.getLocalizedMessage());
            return urlList;
        }
        Elements aLinks = doc.select("a[href]");
        for (Element element : aLinks) {
            String url = element.attr("href");
            if (!URLAction.actionBuilder().isAccessibleURLAddress(url)) {
                continue;
            }
            if (!url.contains("http://") && !url.contains("https://")) {
                if (url.startsWith("/")) {
                    url = url.substring(1);
                }
                url = DriverConfig.ENTRY_PAGE + url;
            }
//            if (DuplicateAction.actionBuilder().isDuplicateURL(url)) {
//                continue;
//            }

            if (!URLAction.actionBuilder().isURLSchemaValid(url)) {
                System.out.println("## Invalid URL: " + url);
                continue;
            }

            System.out.println(" >>: " + url);
            urlList.add(url);
        }

        return urlList;
    }

    public static List<String> getImageLinks(String pageContent) {
        List<String> imageUrlList = new ArrayList<>();

        try {
            doc = Jsoup.parse(pageContent);
        } catch (Exception e) {
            // Received failure 404
            logger.error(e.getLocalizedMessage());
            return imageUrlList;
        }
        Elements imgLinks = doc.select("img[src]");
        for (Element element : imgLinks) {
            String srcStr = element.attr("src");

            if (!srcStr.contains("http://") && !srcStr.contains("https://")) {//没有这两个头
//                srcStr = DriverConfig.ENTRY_PAGE + srcStr;
            }
            System.out.println("## Image link: " + srcStr);
            imageUrlList.add(srcStr);
        }

        return imageUrlList;
    }

    public static List<String> getForms(String pageContent) {
        List<String> formList = new ArrayList<>();

        try {
            doc = Jsoup.parse(pageContent);
        } catch (Exception e) {
            // Received failure 404
            logger.error(e.getLocalizedMessage());
            return formList;
        }
        Elements imgLinks = doc.select("form[method]");
        for (Element element : imgLinks) {
            String formAction = element.attr("action");
            String formId = element.attr("id");

//            if (!StringUtils.isEmpty(formAction)) {
//                System.out.println("## formAction link: " + formAction);
//                formList.add(formId + "_" + formAction);
//            }
        }

        return formList;
    }

}