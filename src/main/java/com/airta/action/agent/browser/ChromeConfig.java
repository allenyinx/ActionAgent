package com.airta.action.agent.browser;

public class ChromeConfig {

    public static void prepareEnvironment() {

        System.setProperty("webdriver.chrome.driver", "driver/chromedriver_77");
//        System.setProperty("webdriver.chrome.driver", "/home/airbot/chromedriver");
    }
}
