package com.airta.action.agent.utility.parser;

import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.entity.html.ElementType;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebElementParser {

    private static final Logger logger = LoggerFactory.getLogger(WebElementParser.class);

    public List<Element> parseChildElements(List<WebElement> webElementList, ElementType elementType,
                                            String locatorPrefix, RawActionContext rawActionContext, Element parentElement) {
        List<Element> elementList = new ArrayList<>();

        if (webElementList == null || webElementList.size() == 0) {
            return Collections.emptyList();
        } else {
            for (int index = 0; index < webElementList.size(); index++) {

                WebElement webElement = webElementList.get(index);
                Element element = new Element();
                element.setId(webElement.getAttribute("id"));

                attributeCommonElement(element, elementType, locatorPrefix, rawActionContext, parentElement, index);

                elementList.add(element);
            }
        }

        return elementList;
    }

    private Element attributeCommonElement(Element featureElement, ElementType elementType,
                                           String locatorPrefix, RawActionContext rawActionContext, Element parentElement, int index) {

        featureElement.setType(elementType);
        featureElement.setParentId(parentElement.getElementId());
        featureElement.setElementId(parentElement.getParentId() + "_" + featureElement.getId() + "_" + index);
        featureElement.setWorkingOn(false);
        if (rawActionContext != null) {
            if (featureElement.getId() != null && !featureElement.getId().equals("")) {
                featureElement.setPathPath(locatorPrefix + "[@id='" + featureElement.getId() + "']");
            } else {
                featureElement.setPathPath(locatorPrefix + "[" + index + "]");
            }

        }
        return featureElement;
    }

}
