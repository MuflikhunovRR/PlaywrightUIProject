package ru.gotoqa.factory;

import com.microsoft.playwright.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

/**
 * PlaywrightFactory class is used to initialize the Playwright browser
 * and to create a new instance of the browser.
 */
public class PlaywrightFactory {
    protected static final Logger LOGGER = LogManager.getLogger(PlaywrightFactory.class);

    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }


    /**
     * This method is used to initialize the browser
     */
    public Page initBrowser(Properties properties) {
        String browserName = properties.getProperty("browser").trim().toLowerCase();
        LOGGER.info("Browser name is " + browserName);
        tlPlaywright.set(Playwright.create());

        switch (browserName.toLowerCase()) {
            case "chromium":
                tlBrowser.set(getPlaywright().chromium()
                        .launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "firefox":
                tlBrowser.set(getPlaywright().firefox()
                        .launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "safari":
                tlBrowser.set(getPlaywright().webkit()
                        .launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "chrome":
                tlBrowser.set(getPlaywright().chromium()
                        .launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));
                break;
            case "edge":
                tlBrowser.set(getPlaywright().chromium()
                        .launch(new BrowserType.LaunchOptions().setChannel("msedge-beta").setHeadless(false)));
                break;

            default:
                LOGGER.info("Please ensure to input the accurate browser name.");
                break;
        }

        tlBrowserContext.set(getBrowser().newContext());
        tlPage.set(getBrowserContext().newPage());
        getPage().navigate(properties.getProperty("url").trim());
        return getPage();
    }

    /**
     * This method is used to initialize the properties from config file
     */
    public Properties initProperties() {
        try {
            Properties properties = new Properties();
            properties.load(Files.newInputStream(
                    Paths.get("src/test/resources/config.properties")));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    /**
     * This method is used to take the screenshot
     */
    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshot/"
                + System.currentTimeMillis() + ".png";
        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(path))
                .setFullPage(true));
        return Base64.getEncoder().encodeToString(buffer);
    }

}
