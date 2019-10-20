package com.airta.action.agent.webdriver;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.entity.html.ElementType;
import com.airta.action.agent.utility.parser.HtmlParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.ArrayList;
import java.util.List;

public class WebDriverCapturer extends WebDriverWrapUp {

    protected HtmlParser htmlParser = new HtmlParser();

    public WebDriverCapturer(WebDriver webDriver) {
        super(webDriver);
    }

    public String displayJSErrosLog(WebDriver webDriver) {
        LogEntries jserrors;
        String jsErrorLogContent = "JS error: [\n";
        try {
            jserrors = webDriver.manage().logs().get(LogType.BROWSER);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return "";
        }
        for (LogEntry error : jserrors) {
            jsErrorLogContent += (error.getMessage() + "\n");
        }
        return jsErrorLogContent + "]";
    }

    public Element readPageElementsRightNow(RawAction rawAction) {

        Element rootElement = new Element();
        rootElement.setElementId(rawAction.getId());
        rootElement.setActionable(true);
        rootElement.setUrl(webDriver.getCurrentUrl());

        RawActionContext rawActionContext = rawAction.getContext();
        if(rawActionContext!=null) {
            rootElement.setPathPath(rawActionContext.getPagePath());
        }

        /**
         * read children elements.
         */
        List<Element> childrenElementList = parserChildrenElements(rawActionContext, rootElement);
        rootElement.setChildren(childrenElementList);
        rootElement.setChildrenCount(childrenElementList.size());

        return rootElement;
    }

    private List<Element> parserChildrenElements(RawActionContext rawActionContext, Element rootElement) {

        List<Element> childrenList = new ArrayList<>();

        String rawContent = readCurrentPageSource();
        List<String> linkList = parsePageContent(rawContent);
        List<String> imageList = parseImagePageContent(rawContent);
        List<String> formList = parseFormPageContent(rawContent);

        List<Element> linkElementList = buildLinkElementList(linkList, rawActionContext, rootElement);
        List<Element> imageElementList = buildImageElementList(imageList, rawActionContext, rootElement);
        List<Element> formElementList = buildFormElementList(formList, rawActionContext, rootElement);

        childrenList.addAll(linkElementList);
        childrenList.addAll(imageElementList);
        childrenList.addAll(formElementList);

        return childrenList;
    }

    private List<Element> buildLinkElementList(List<String> linkURLList, RawActionContext rawActionContext, Element parentElement) {

        List<Element> urlElementList = new ArrayList<>();
        for(int index=0; index< linkURLList.size(); index++) {
            String linkUrl = linkURLList.get(index);
            Element linkElement = buildLinkElement(linkUrl, rawActionContext, parentElement, index);
            if(linkElement!=null && linkElement.isActionable()) {
                urlElementList.add(linkElement);
            }
        }

        return urlElementList;
    }

    private List<Element> buildImageElementList(List<String> imageURLList, RawActionContext rawActionContext, Element parentElement) {

        List<Element> imageElementList = new ArrayList<>();
        for(int index=0; index< imageURLList.size(); index++) {
            String linkUrl = imageURLList.get(index);
            Element linkElement = buildImageElement(linkUrl, rawActionContext, parentElement, index);
            if(linkElement!=null && linkElement.isActionable()) {
                imageElementList.add(linkElement);
            }
        }

        return imageElementList;
    }

    private List<Element> buildFormElementList(List<String> formList, RawActionContext rawActionContext, Element parentElement) {

        List<Element> formElementList = new ArrayList<>();
        for(int index=0; index< formList.size(); index++) {
            String linkUrl = formList.get(index);
            Element linkElement = buildFormElement(linkUrl, rawActionContext, parentElement, index);
            if(linkElement!=null && linkElement.isActionable()) {
                formElementList.add(linkElement);
            }
        }

        return formElementList;
    }

    private Element buildElement(String linkUrl, RawActionContext rawActionContext, ElementType elementType, Element parentElement, int index) {

        Element linkElement = new Element();
        linkElement.setUrl(linkUrl);
        linkElement.setType(elementType);
        linkElement.setParentId(parentElement.getElementId());
        linkElement.setElementId(parentElement.getElementId()+"_"+elementType+"_"+index);
        if(rawActionContext!=null) {
            linkElement.setPathPath(rawActionContext.getPagePath()+"_"+elementType.toString()+"_"+index);
        }

        return linkElement;
    }

    private Element buildLinkElement(String linkUrl, RawActionContext rawActionContext, Element parentElement, int index) {

        return buildElement(linkUrl, rawActionContext, ElementType.link, parentElement, index);
    }

    private Element buildImageElement(String linkUrl, RawActionContext rawActionContext, Element parentElement, int index) {

        return buildElement(linkUrl, rawActionContext, ElementType.image, parentElement, index);
    }

    private Element buildFormElement(String linkUrl, RawActionContext rawActionContext, Element parentElement, int index) {

        return buildElement(linkUrl, rawActionContext, ElementType.form, parentElement, index);
    }

    private String readCurrentPageSource() {

        String pageSource = webDriver.getPageSource();


        return pageSource;
    }

    private List<String> parsePageContent(String content) {

        return htmlParser.parseChildLinks(content);
    }

    private List<String> parseImagePageContent(String content) {

        return htmlParser.getImageLinks(content);
    }

    private List<String> parseFormPageContent(String content) {

        return htmlParser.getForms(content);
    }
}
