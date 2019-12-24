package com.airta.action.agent.config;

import org.openqa.selenium.PageLoadStrategy;

public final class DriverConfig {

    public static final String ENTRY_PAGE = "http://www.bing.com";
    public static final int CHILDPAGE_DEPTH = 2;
    public static final int MAX_PAGE_COUNT = 200;

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

    public static final String SCREENSHOT_FOLDER = "record/screenshot/";
    public static final String ERROR_LOG_FOLDER = "record/jslogs/";
    public static final String DUPLICATE_URL_FOLDER = "record/duplicate/";
    public static final String SITEMAP_PATH = "record/sitemap.xml";

    public static final String WebDriverSessionKey = "webDriverSession";
    public static final String WebDriverSessionStatus = "webDriverSessionStatus";
    public static final String WebDriverPagePathID = "webDriverPagePathID";

}
