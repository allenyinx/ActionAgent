package com.airta.action.agent.webdriver;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.entity.html.ElementType;
import com.airta.action.agent.entity.html.PageElementPath;
import com.airta.action.agent.utility.parser.HtmlParser;
import com.airta.action.agent.utility.parser.WebElementParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author allenyin
 */
public class WebDriverCapturer extends WebDriverWrapUp {

    protected HtmlParser htmlParser;
    protected WebElementParser webElementParser;
    protected PageElementPath pageElementPath;

    public WebDriverCapturer(WebDriver webDriver) {
        super(webDriver);
        htmlParser = new HtmlParser();
        webElementParser = new WebElementParser();
        pageElementPath = new PageElementPath();
    }

    public String displayJSErrosLog() {
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
        if (rawAction!=null && rawAction.getPathId() != null) {
            rootElement.setElementId(rawAction.getPathId());
        } else {
            rootElement.setId("root_0");
            rootElement.setClassName("page");
            rootElement.setElementId("root_" + (rawAction!=null?rawAction.getId():"0"));
        }
        rootElement.setActionable(true);
        rootElement.setUrl(webDriver.getCurrentUrl());
        rootElement.setType(ElementType.page);
        rootElement.setWorkingOn(true);

        RawActionContext rawActionContext = null;
        if(rawAction!=null) {
            rawActionContext = rawAction.getContext();
            if (rawActionContext != null) {
                rootElement.setPathPath(rawActionContext.getPagePath());
            }
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

        List<Element> linkElementList = buildLinkElementList(rawContent, rawActionContext, rootElement);
        logger.info("## fetch link children: [" + linkElementList.size() + "] ..");
        List<Element> formElementList = buildFormElementList(rawContent, rawActionContext, rootElement);
        logger.info("## fetch form children: [" + formElementList.size() + "] ..");

        childrenList.addAll(linkElementList);
        childrenList.addAll(formElementList);

        return childrenList;
    }

    private List<Element> buildLinkElementList(String content, RawActionContext rawActionContext, Element parentElement) {

        List<WebElement> linkElements = webDriver.findElements(pageElementPath.linkPathSyntax());
        return webElementParser.parseChildElements(linkElements, ElementType.link, "//a", rawActionContext, parentElement);
//        return htmlParser.parseChildLinkElements(content, rawActionContext, parentElement);
    }

    private List<Element> buildFormElementList(String content, RawActionContext rawActionContext, Element parentElement) {

        List<WebElement> formElements = webDriver.findElements(pageElementPath.formPathSyntax());
        return webElementParser.parseChildElements(formElements, ElementType.form, "//form", rawActionContext, parentElement);
//        return htmlParser.parseChildFormElements(content, rawActionContext, parentElement);
    }

    public String readCurrentPageSource() {

        String pageSource = webDriver.getPageSource();


        return pageSource;
    }
}
