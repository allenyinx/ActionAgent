package com.airta.action.agent.webdriver;

import org.openqa.selenium.PageLoadStrategy;

public class DriverConfig {

    public static final String ENTRY_PAGE = "www.baidu.com";

    /**
     * 1. normal:
     * This strategy causes Selenium to wait for the full page loading (html content and sub resources downloaded and parsed).
     *
     * 2. eager :
     * This strategy causes Selenium to wait for the DOMContentLoaded event (html content downloaded and parsed only).
     *
     * 3. none :
     * This strategy causes Selenium to return immediately after the initial page content is fully received (html content downloaded).
     */
    public static final PageLoadStrategy PAGE_LOAD_STRATEGY = PageLoadStrategy.NONE;

    public static final boolean TAKE_SCREENSHOT_ON_FAILURE = true;
    public static final boolean RECORD_JS_LOG_ON_FAILURE = true;
}
