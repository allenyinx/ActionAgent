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
        List<Element> childrenElementList = parserChildrenElements(rawActionContext);
        rootElement.setChildren(childrenElementList);
        rootElement.setChildrenCount(childrenElementList.size());

        return rootElement;
    }

    private List<Element> parserChildrenElements(RawActionContext rawActionContext) {

        List<Element> childrenList = new ArrayList<>();

        String rawContent = readCurrentPageSource();
        List<String> linkList = parsePageContent(rawContent);
        List<String> imageList = parseImagePageContent(rawContent);
        List<String> formList = parseFormPageContent(rawContent);

        List<Element> linkElementList = buildLinkElementList(linkList, rawActionContext);
        List<Element> imageElementList = buildImageElementList(imageList, rawActionContext);
        List<Element> formElementList = buildFormElementList(formList, rawActionContext);

        childrenList.addAll(linkElementList);
        childrenList.addAll(imageElementList);
        childrenList.addAll(formElementList);

        return childrenList;
    }

    private List<Element> buildLinkElementList(List<String> linkURLList, RawActionContext rawActionContext) {

        List<Element> urlElementList = new ArrayList<>();
        for(String linkUrl: linkURLList) {
            Element linkElement = buildLinkElement(linkUrl, rawActionContext);
            if(linkElement!=null && linkElement.isActionable()) {
                urlElementList.add(linkElement);
            }
        }

        return urlElementList;
    }

    private List<Element> buildImageElementList(List<String> imageURLList, RawActionContext rawActionContext) {

        List<Element> imageElementList = new ArrayList<>();
        for(String linkUrl: imageURLList) {
            Element linkElement = buildImageElement(linkUrl, rawActionContext);
            if(linkElement!=null && linkElement.isActionable()) {
                imageElementList.add(linkElement);
            }
        }

        return imageElementList;
    }

    private List<Element> buildFormElementList(List<String> formList, RawActionContext rawActionContext) {

        List<Element> formElementList = new ArrayList<>();
        for(String form: formList) {
            Element linkElement = buildFormElement(form, rawActionContext);
            if(linkElement!=null && linkElement.isActionable()) {
                formElementList.add(linkElement);
            }
        }

        return formElementList;
    }

    private Element buildElement(String linkUrl, RawActionContext rawActionContext, ElementType elementType) {

        Element linkElement = new Element();
        linkElement.setUrl(linkUrl);
        linkElement.setType(elementType);
        if(rawActionContext!=null) {
            linkElement.setPathPath(rawActionContext.getPagePath()+"/"+elementType.toString());
        }

        return linkElement;
    }

    private Element buildLinkElement(String linkUrl, RawActionContext rawActionContext) {

        return buildElement(linkUrl, rawActionContext, ElementType.link);
    }

    private Element buildImageElement(String linkUrl, RawActionContext rawActionContext) {

        return buildElement(linkUrl, rawActionContext, ElementType.image);
    }

    private Element buildFormElement(String linkUrl, RawActionContext rawActionContext) {

        return buildElement(linkUrl, rawActionContext, ElementType.form);
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
