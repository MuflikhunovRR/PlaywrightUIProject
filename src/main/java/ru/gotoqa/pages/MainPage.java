package ru.gotoqa.pages;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * HomePage class contains all the locators and methods related to home page
 */
public class MainPage {
    protected static final Logger LOGGER = LogManager.getLogger(MainPage.class);

    private Page page;
    private final String searchInput = "input[name='search']";
    private final String searchIcon = "div#search button";
    private final String searchPageHeader = "div#content h1";
    private final String loginLink = "a:text('Login')";
    private final String myAccountLink = "//span[normalize-space()='My Account']";

    public MainPage(Page page) {
        this.page = page;
    }

    public String getHomePageTitle() {
        String title = page.title();
        LOGGER.info("Home page title is: " + title);
        return title;
    }

    public String getHomePageURL() {
        String url = page.url();
        LOGGER.info("Home page url is: " + url);
        return url;
    }

    public String searchForProduct(String productName) {
        page.fill(searchInput, productName);
        page.click(searchIcon);
        String header = page.textContent(searchPageHeader);
        LOGGER.info("Search header is " + header);
        return header;
    }

    public LoginPage navigateToLoginPage() {
        page.click(myAccountLink);
        page.click(loginLink);
        return new LoginPage(page);
    }

}
