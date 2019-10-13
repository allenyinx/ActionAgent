package com.airta.action.agent.webdriver.chrome;

import com.airta.action.agent.webdriver.DriverConfig;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class ChromeConfig {

    public static ChromeOptions initiate() {

        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setHeadless(true);
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
//        chromeOptions.addArguments(createChromeArgs());
        chromeOptions.addArguments(createUselessChromeArgs());

        chromeOptions.setPageLoadStrategy(DriverConfig.PAGE_LOAD_STRATEGY);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        logConfigure(chromeOptions);

        return chromeOptions;
    }

    private static List<String> createUselessChromeArgs() {

        final List<String> args = new ArrayList<>();
        //AGRESSIVE: options.setPageLoadStrategy(PageLoadStrategy.NONE); // https://www.skptricks.com/2018/08/timed-out-receiving-message-from-renderer-selenium.html
        args.add("--start-maximized");
//        args.add("enable-automation");
        args.add("--no-sandbox");
//        args.add("disable-infobars");
        args.add("--disable-infobars");
        args.add("--disable-dev-shm-usage");
        args.add("--disable-browser-side-navigation");
        args.add("--disable-gpu");
        args.add("--no-first-run");
//        args.add("--headless");

        return args;
//        options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
//        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
//        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
//        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
//        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
    }

    private static void logConfigure(ChromeOptions chromeOptions) {

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    }
}
