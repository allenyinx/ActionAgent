package com.airta.action.agent.utility.parser;

import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.handler.URLAction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
//import org.thymeleaf.util.StringUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {

    private static final Logger logger = LoggerFactory.getLogger(HtmlParser.class);

    private Document doc = null;

    /**
     * @param pageContent target URL
     */
    public List<String> parseChildLinks(String pageContent) {
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

    public List<String> getImageLinks(String pageContent) {
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
                srcStr = DriverConfig.ENTRY_PAGE + srcStr;
            }
            System.out.println("## Image link: " + srcStr);
            imageUrlList.add(srcStr);
        }

        return imageUrlList;
    }

    public List<String> getForms(String pageContent) {
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

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        String response =" <Resultant> " +
                " <exceptionDetails> " +
                " <exceptionList> " +
                " <code> ABC</code> " +
                " <message> Invalid Value</message> " +
                " </exceptionList> " +
                " <exceptionList> " +
                " <code> ABZ</code> " +
                " <message> Invalid Structure</message> " +
                " </exceptionList> " +
                " </exceptionDetails> " +
                " <Result> " +
                " <code> 1234</code> " +
                " <Details> " +
                " <Detail> " +
                " <System type=\"A\"> Admin</System> " +
                " <Type> full</Type> " +
                " <Date> 2010-02-08</Date> " +
                " </Detail> " +
                " <Detail> " +
                " <System type=\"B\"> Beneficiary</System> " +
                " <Type> full</Type> " +
                " <Date>2015-10-05</Date> " +
                " </Detail> " +
                " <Detail> " +
                " <System type=\"C\"> Customer</System> " +
                " <Type> Partial</Type> " +
                " <Date>2010-11-01</Date> " +
                " </Detail> " +
                " </Details> " +
                " </Result> " +
                "</Resultant> " ;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = dBuilder.parse(new InputSource(new StringReader(response)));
        ((org.w3c.dom.Document) doc).getDocumentElement().normalize();

        NodeList nList;
        nList = doc.getElementsByTagName("exceptionList");
        System.out.println("nList " + nList.getLength());
        nList = doc.getElementsByTagName("Details");
        System.out.println("nList " + nList.getLength());


    }

}