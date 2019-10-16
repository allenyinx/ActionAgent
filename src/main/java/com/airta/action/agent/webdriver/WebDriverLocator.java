package com.airta.action.agent.webdriver;

import com.airta.action.agent.action.raw.fields.ElementLocation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.util.StringUtils;

import java.util.List;

public class WebDriverLocator extends WebDriverWrapUp {

    public WebDriverLocator(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement findElement(ElementLocation elementLocation) {

        if (StringUtils.isEmpty(elementLocation.getType()) || StringUtils.isEmpty(elementLocation.getValue())) {
            return null;
        }
        switch (elementLocation.getType()) {
            case "id":
                return findElementBy(By.id(elementLocation.getValue()));
            case "name":
                return findElementBy(By.name(elementLocation.getValue()));
            case "xpath":
                return findElementBy(By.xpath(elementLocation.getValue()));
            case "linkText":
                return findElementBy(By.linkText(elementLocation.getValue()));
            case "className":
                return findElementBy(By.className(elementLocation.getValue()));
            case "tagName":
                return findElementBy(By.tagName(elementLocation.getValue()));
            case "cssSelector":
                return findElementBy(By.cssSelector(elementLocation.getValue()));
            case "partialLinkText":
                return findElementBy(By.partialLinkText(elementLocation.getValue()));
            default:
                logger.error("cannot locate the element with input locator.");
                return null;
        }
    }

    public List<WebElement> findElements(ElementLocation elementLocation) {

        if (StringUtils.isEmpty(elementLocation.getType()) || StringUtils.isEmpty(elementLocation.getValue())) {
            return null;
        }
        switch (elementLocation.getType()) {
            case "id":
                return findElementsBy(By.id(elementLocation.getValue()));
            case "name":
                return findElementsBy(By.name(elementLocation.getValue()));
            case "xpath":
                return findElementsBy(By.xpath(elementLocation.getValue()));
            case "linkText":
                return findElementsBy(By.linkText(elementLocation.getValue()));
            case "className":
                return findElementsBy(By.className(elementLocation.getValue()));
            case "tagName":
                return findElementsBy(By.tagName(elementLocation.getValue()));
            case "cssSelector":
                return findElementsBy(By.cssSelector(elementLocation.getValue()));
            case "partialLinkText":
                return findElementsBy(By.partialLinkText(elementLocation.getValue()));
            default:
                logger.error("cannot locate the element with input locator.");
                return null;
        }
    }

    private WebElement findElementBy(By by) {
        return webDriver.findElement(by);
    }

    private List<WebElement> findElementsBy(By by) {
        return webDriver.findElements(by);
    }
}
