package ru.gotoqa.base;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import ru.gotoqa.factory.PlaywrightFactory;
import ru.gotoqa.pages.MainPage;
import ru.gotoqa.pages.LoginPage;

import java.util.Properties;

/**
 * BaseTest class contains all the common methods and properties used across the tests
 */
public class BaseTest {
    protected static final Logger LOGGER = LogManager.getLogger(BaseTest.class);
    PlaywrightFactory playwrightFactory;
    Page page;
    protected Properties properties;
    protected MainPage mainPage;
    protected LoginPage loginPage;

    @Parameters({"browser"})
    @BeforeTest
    public void setUp(String browserName) {
        playwrightFactory = new PlaywrightFactory();
        properties = playwrightFactory.initProperties();

        if (browserName != null) {
            properties.setProperty("browser", browserName);
        }

        page = playwrightFactory.initBrowser(properties);
        mainPage = new MainPage(page);
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }

}
