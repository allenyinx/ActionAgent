package com.airta.action.agent.utility;

import com.airta.action.agent.action.raw.fields.ElementLocation;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.entity.html.ElementType;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverMagical {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebDriver webDriver;

    public WebDriverMagical(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public ElementLocation buildElementLocator(Element element, ElementType elementType) {

        logger.info("## Construct element: {} locator from WebDriver and current context ..", element.getElementId());
        ElementLocation elementLocation = new ElementLocation();

        /**
         *
         * function getContainsTextPath(element){
         *
         *     if(!childHasText(element)){
         *
         *             var content = element.innerText;
         *             if(content){
         *                 content = content.trim();
         *                     if(content.length > 0){
         *
         *                         var xpath = '//*[text()[contains(.,"' + content + '")]]';
         *
         *                         xpath = specify(xpath, element);
         *
         *                         return xpath;
         *                     }
         *             }
         *     }
         *     return '';
         * }
         *
         * JavascriptExecutor js = (JavascriptExecutor) driver;
         *                 String retVal = (String) js.executeScript(JS_GENERATE_LOCATORS, element);
         *                 System.out.println("retVal: " + retVal);
         *
         *
         * String headingClass = heading.getAttribute("class");
         *
         * String headingPath = "//div[@class='"+ headingClass + "']"
         *
         *
         *
         *
         * private String generateXPATH(WebElement childElement, String current) {
         *     String childTag = childElement.getTagName();
         *     if(childTag.equals("html")) {
         *         return "/html[1]"+current;
         *     }
         *     WebElement parentElement = childElement.findElement(By.xpath(".."));
         *     List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
         *     int count = 0;
         *     for(int i=0;i<childrenElements.size(); i++) {
         *         WebElement childrenElement = childrenElements.get(i);
         *         String childrenElementTag = childrenElement.getTagName();
         *         if(childTag.equals(childrenElementTag)) {
         *             count++;
         *         }
         *         if(childElement.equals(childrenElement)) {
         *             return generateXPATH(parentElement, "/" + childTag + "[" + count + "]"+current);
         *         }
         *     }
         *     return null;
         * }
         *
         * https://scottizu.wordpress.com/2014/05/12/generating-unique-ids-for-webelements-via-xpath/
         *
         *
         *
         */

        return elementLocation;
    }

}
