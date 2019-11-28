package com.airta.action.agent.webdriver;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.ElementLocation;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.entity.html.ElementType;
import com.airta.action.agent.utility.WebDriverMagical;
import com.airta.action.agent.utility.parser.HtmlParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.ArrayList;
import java.util.List;

public class WebDriverCapturer extends WebDriverWrapUp {

    protected HtmlParser htmlParser;
    protected WebDriverMagical webDriverMagical;

    public WebDriverCapturer(WebDriver webDriver) {
        super(webDriver);
        htmlParser = new HtmlParser();
        webDriverMagical = new WebDriverMagical(webDriver);
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
        if (rawAction.getPathId() != null) {
            rootElement.setElementId(rawAction.getPathId());
        } else {
            rootElement.setElementId("root_" + rawAction.getId());
        }
        rootElement.setActionable(true);
        rootElement.setUrl(webDriver.getCurrentUrl());
        rootElement.setType(ElementType.page);
        rootElement.setWorkingOn(true);

        RawActionContext rawActionContext = rawAction.getContext();
        if (rawActionContext != null) {
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

        /**
         * may need to change to js exec for more accurate contents.
         */
        String rawContent = readCurrentPageSource();

//        List<String> linkList = parsePageContent(rawContent);
//        List<String> imageList = parseImagePageContent(rawContent);
//        List<String> formList = parseFormPageContent(rawContent);

        List<Element> linkElementList = buildLinkElementList(rawContent, rawActionContext, rootElement);
        List<Element> imageElementList = buildImageElementList(rawContent, rawActionContext, rootElement);
        List<Element> formElementList = buildFormElementList(rawContent, rawActionContext, rootElement);

        childrenList.addAll(linkElementList);
        childrenList.addAll(imageElementList);
        childrenList.addAll(formElementList);

        return childrenList;
    }

    private List<Element> buildLinkElementList(String content, RawActionContext rawActionContext, Element parentElement) {

        return htmlParser.parseChildLinkElements(content, rawActionContext, parentElement);
    }

    private List<Element> buildImageElementList(String content, RawActionContext rawActionContext, Element parentElement) {

        return htmlParser.parseChildImageElements(content, rawActionContext, parentElement);
    }

    private List<Element> buildFormElementList(String content, RawActionContext rawActionContext, Element parentElement) {

        return htmlParser.parseChildFormElements(content, rawActionContext, parentElement);
    }

    /**
     * properly assign locator for the element.
     *
     * @param element
     * @param elementType
     */
    private void constructElementLocator(Element element, ElementType elementType) {

        ElementLocation elementLocation = webDriverMagical.buildElementLocator(element, elementType);
        element.setElementLocation(elementLocation);
    }

    private String readCurrentPageSource() {

        String pageSource = webDriver.getPageSource();


        return pageSource;
    }
}
