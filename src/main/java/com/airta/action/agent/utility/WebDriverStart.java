package com.airta.action.agent.utility;

import com.airta.action.agent.entity.DeviceType;
import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.entity.html.ElementType;
import com.airta.action.agent.browser.ChromeConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDriverStart {

    private static final String PAGE_EXPECTED_TITLE = "";

    /**
     * if should limit the image depth in current web page.
     */
    public static final boolean CHROME_CONTENT_IMAGE_LIMIT = false;

    /**
     * if limit enabled, how many depth should set.
     */
    public static final int PROFILE_CONTENT_IMAGE_LAYER = 3;

    /**
     * the idle sleep time after last page accessed.
     */
    public static final int BROWSER_LAST_PAGE_IDLE_SECOND = 3;

    private static ChromeOptions chromeOptions = new ChromeOptions();

    /**
     * if should run in headless mode.
     */
    public static final boolean CHROME_HEADLESS = false;

    /**
     * if should display debug and console log from browser.
     */
    public static final boolean CHROME_LOG_ANALYSIS = true;

    /**
     * Client device type
     */
    public static final DeviceType DEVICE_TYPE = DeviceType.Android;
    public static final boolean CLIENT_MOCK = false;

    public static int PAGE_DEPTH = 0;

    public static int NavigateCount = 0;

    public static void main(String[] args) {

        browserEntry();
    }

    public static WebDriver browserEntry() {

        ChromeConfig.prepareEnvironment();

        if (CHROME_HEADLESS) {
            headless_normal();
        } else {
            headless_smartest();
        }

        if (CHROME_LOG_ANALYSIS) {
            logConfigure(chromeOptions);
        }


        Element rootElement = new Element();
        rootElement.setDepth(0);
        rootElement.setUrl(DriverConfig.ENTRY_PAGE);
        rootElement.setElementId(ElementType.link.name() + "_0");
        rootElement.setType(ElementType.link.name());

        WebDriver webDriver = launchBrowser(chromeOptions);

        sampleValidate(webDriver);
//        startIterate(webDriver, rootElement);


//        tearDownBrowser(webDriver);
        return webDriver;
    }

    private static void startIterate(WebDriver webDriver, Element rootElement) {

        if (CHROME_LOG_ANALYSIS) {
            debugViewPeriod(webDriver);
        }
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(6, TimeUnit.SECONDS);

        scanAllChildrenLinks(webDriver, PAGE_DEPTH, rootElement);
    }

    private static void scanAllChildrenLinks(WebDriver webDriver, int depth, Element parentElement) {

        if (NavigateCount > DriverConfig.MAX_PAGE_COUNT) {
            return;
        }
        if (depth++ > DriverConfig.CHILDPAGE_DEPTH) {
            info("Out of Depth Range, jump out ..");
            return;
        }

        displayJSErrosLog(webDriver);
        anchorCurrentPageNavi(webDriver);

        String currentPageSource = capturePageSource(webDriver);
        List<String> childrenLinkList = parsePageContent(currentPageSource);
        List<String> childrenImageLinkList = parseImagePageContent(currentPageSource);
        List<String> childrenFormList = parseFormPageContent(currentPageSource);

        TreeMapUtil.addElement(parentElement, childrenImageLinkList, depth, ElementType.image);
        TreeMapUtil.addElement(parentElement, childrenFormList, depth, ElementType.form);

        if (!childrenLinkList.isEmpty()) {
            for (int index = 0; index < childrenLinkList.size(); index++) {
                info("-- [DEPTH: " + depth + "] on: " + childrenLinkList.get(index));
                if (depth > DriverConfig.CHILDPAGE_DEPTH) {
                    info("Out of Depth Range, jump out ..");
                    return;
                }
                if (FileUtil.isCommonFileType(childrenLinkList.get(index))) {
                    info("File type link, jump out ..");
                    return;
                }

                Element childElement = TreeMapUtil.addElement(parentElement, depth, ElementType.link, childrenLinkList.get(index), index);

                try {
                    navigateURL(webDriver, childrenLinkList.get(index));
                } catch (TimeoutException e1) {
                    e1.printStackTrace();
                }

                scanAllChildrenLinks(webDriver, depth, childElement);
            }
        } else {
            info("-- [DEPTH: " + depth + "] done");
        }
    }

    private static void scanChildrenLink(String pageSource, WebDriver webDriver) {

        List<String> childrenLinkList = parsePageContent(pageSource);

        for (String childLink : childrenLinkList) {
            navigateURL(webDriver, childLink);
        }
    }

    public static void headless_normal() {

        chromeOptions.setHeadless(true);
        chromeOptions.setAcceptInsecureCerts(true);
    }

    public static void headless_smartest() {

        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
//        chromeOptions.addArguments(createChromeArgs());
        chromeOptions.addArguments(createUselessChromeArgs());

        chromeOptions.setPageLoadStrategy(DriverConfig.PAGE_LOAD_STRATEGY);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        if (CHROME_CONTENT_IMAGE_LIMIT) {
            setImageDisplayLayers(chromeOptions);
        }
    }

    private static WebDriver launchBrowser(ChromeOptions chromeOptions) {

        WebDriver driver = new ChromeDriver(chromeOptions);
        navigateURL(driver, DriverConfig.ENTRY_PAGE);

        return driver;
    }

    private static void setImageDisplayLayers(ChromeOptions chromeOptions) {

        Map no_Image_loading = new HashMap();
        no_Image_loading.put("profile.managed_default_content_settings.images", PROFILE_CONTENT_IMAGE_LAYER);

        no_Image_loading.put("profile.default_content_settings.popups", 0);
        no_Image_loading.put("download.prompt_for_download", false);
        no_Image_loading.put("password_manager_enabled", false);

        chromeOptions.setExperimentalOption("prefs", no_Image_loading);
    }

    private static void sampleValidate(WebDriver webDriver) {

        String actualTitle = webDriver.getTitle();
        info("Actual Title: " + actualTitle);

        /*
         * compare the actual title of the page with the expected one and print
         * the result as "Passed" or "Failed"
         */
        if (actualTitle.contentEquals(PAGE_EXPECTED_TITLE)) {
            info("Test Passed!");
        } else {
            info("Test Failed");
        }
        blockSeperator();
    }

    private static void debugViewPeriod(WebDriver webDriver) {

        analyzeLog(webDriver);

        try {
            Thread.sleep(BROWSER_LAST_PAGE_IDLE_SECOND * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void anchorCurrentPageNavi(WebDriver webDriver) {
        try {
            String currentUrl = webDriver.getCurrentUrl();
            info("[DEPTH: " + PAGE_DEPTH++ + "] URL: " + webDriver.getCurrentUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        blockSeperator();
    }

    private static void logConfigure(ChromeOptions chromeOptions) {

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    }

    /**
     * Capture all JSerrors and print In console.
     *
     * @param webDriver
     */
    public static void displayJSErrosLog(WebDriver webDriver) {
        LogEntries jserrors = null;
        try {
            jserrors = webDriver.manage().logs().get(LogType.BROWSER);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (LogEntry error : jserrors) {
            record("JS error: " + error.getMessage());

            String imageName = NavigateCount + "_" + System.currentTimeMillis();
            if (DriverConfig.TAKE_SCREENSHOT_ON_FAILURE) {
//                ScreenshotAction.actionBuilder().takeScreenshot(imageName, String.valueOf(PAGE_DEPTH), webDriver);
            }
        }
    }

    public static void analyzeLog(WebDriver webDriver) {
        LogEntries logEntries = webDriver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            record("BROWSER LOG: " + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            //do something useful with the data
        }
    }

    private static void tearDownBrowser(WebDriver webDriver) {

        info("");
        info("");
        info("");
        info(" ==================== total accessed: " + NavigateCount + " ===================");
        dismissAlert(webDriver);
        try {
            webDriver.close();
        } catch (WebDriverException e) {
            e.printStackTrace();
            return;
        }

    }

    private static List<String> createChromeArgs() {
        final List<String> args = new ArrayList<>();
        args.add("--start-maximized");
        args.add("--disable-extensionsno-sandbox");
//        args.add("--test-type");
        args.add("--disable-popup-blocking");
        args.add("--no-first-run");
        args.add("--disable-infobars");
        args.add("--no-sandbox");

        if (CLIENT_MOCK) {
            addUserAgent(args);
        }

        return args;
    }

    private static List<String> createUselessChromeArgs() {

        final List<String> args = new ArrayList<>();
        args.add("--start-maximized");
        args.add("--no-sandbox");
        args.add("--disable-infobars");
        args.add("--disable-dev-shm-usage");
        args.add("--disable-browser-side-navigation");
        args.add("--disable-gpu");
        args.add("--no-first-run");

        return args;
    }

    private static void addHeadlessArgs(List<String> args) {

        args.add("--headless");
        args.add("--disable-gpu");
    }

    private static String capturePageSource(WebDriver webDriver) {
        try {
            return webDriver.getPageSource();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static List<String> parsePageContent(String content) {

        return HtmlParser.parseChildLinks(content);
    }

    private static List<String> parseImagePageContent(String content) {

        return HtmlParser.getImageLinks(content);
    }

    private static List<String> parseFormPageContent(String content) {

        return HtmlParser.getForms(content);
    }

    private static List<String> getHref(String str) {
        List<String> urlLink = new ArrayList();
        Pattern pattern = Pattern.compile("<a href=.*</a>");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            info("URL count: " + matcher.groupCount());
            for (int index = 0; index < matcher.groupCount(); index++) {
                String url = matcher.group(0);
                info("find URL: " + url);
                urlLink.add(url);
            }

        }
        return null;
    }

    private static void addUserAgent(List<String> args) {

        String agentContent = "--user-agent=\"";
        if (DeviceType.Android == DEVICE_TYPE) {
            agentContent += mockUserAgent_Android();
        } else if (DeviceType.iPhone == DEVICE_TYPE) {
            agentContent += mockUserAgent_iPhone();
        }
        args.add(agentContent + "\"");
    }

    private static String mockUserAgent_Android() {

        String userAgentContent =
                "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

        return userAgentContent;
    }

    private static String mockUserAgent_iPhone() {

        String userAgentContent =
                "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";

        return userAgentContent;
    }

    private static void navigateURL(WebDriver webDriver, String url) {
        try {
            webDriver.get(url);
            info(" ==== " + NavigateCount++ + " ===");
//            DuplicateAction.visitedList.add(url);

        } catch (TimeoutException e) {
            e.printStackTrace();
            webDriver.navigate().refresh();
        } catch (Exception e1) {
            e1.printStackTrace();
            try {
                webDriver.navigate().refresh();
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }

        }
    }

    private static void info(String info) {
        System.out.println("## " + info);
    }

    private static void record(String info) {
        System.out.println("## [RECORD] " + info);
        if (DriverConfig.RECORD_JS_LOG_ON_FAILURE) {
//            LogAction.actionBuilder().storeLogToFile(info, String.valueOf(PAGE_DEPTH));
        }
    }

    private static void blockSeperator() {
        System.out.println("-------------------------------------");
    }

    private static ChromeOptions setProxy() {

        Proxy proxy = new Proxy();
        proxy.setHttpProxy("");
        proxy.setProxyType(Proxy.ProxyType.AUTODETECT);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setProxy(proxy);
        return chromeOptions;
    }

    private static void dismissAlert(WebDriver webDriver) {

        try {
            Alert alert = webDriver.switchTo().alert();
            alert.accept();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }
}
