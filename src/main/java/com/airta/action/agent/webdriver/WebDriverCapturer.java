package com.airta.action.agent.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

public class WebDriverCapturer extends WebDriverWrapUp {

    public WebDriverCapturer(WebDriver webDriver) {
        super(webDriver);
    }

    public String displayJSErrosLog(WebDriver webDriver) {
        LogEntries jserrors;
        String jsErrorLogContent = "JS error: [\n";
        try {
            jserrors = webDriver.manage().logs().get(LogType.BROWSER);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        for (LogEntry error : jserrors) {
            jsErrorLogContent += (error.getMessage() + "\n");
        }
        return jsErrorLogContent + "]";
    }
}
