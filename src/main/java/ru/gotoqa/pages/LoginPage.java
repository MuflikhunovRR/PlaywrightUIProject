package ru.gotoqa.pages;

import com.microsoft.playwright.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * LoginPage class contains all the locators and methods related to login page
 */
public class LoginPage {
    protected static final Logger LOGGER = LogManager.getLogger(LoginPage.class);
    private Page page;

    private final String emailId = "//input[@id='input-email']";
    private final String passwordId = "//input[@id='input-password']";
    private final String loginButton = "//button[@type='submit']";
    private final String forgotPwdLink = "//div[@class='mb-3']//a[normalize-space()='Forgotten Password']";
    private final String accountTitleText = "//h2[normalize-space()='My Account']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public String getLoginPageTitle() {
        return page.title();
    }

    public boolean isForgotPwdLinkExist() {
        return page.isVisible(forgotPwdLink);
    }

    public boolean doLogin(String appUserName, String appUserPassword) {
        LOGGER.info("Logging in with username " + appUserName);
        page.fill(emailId, appUserName);
        page.fill(passwordId, appUserPassword);
        page.click(loginButton);
        page.waitForURL("**/account**");
        if (page.isVisible(accountTitleText)) {
            LOGGER.info("User login successful.");
            return true;
        }
        return false;
    }

}
