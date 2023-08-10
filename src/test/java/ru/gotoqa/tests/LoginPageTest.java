package ru.gotoqa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.gotoqa.base.BaseTest;
import ru.gotoqa.constants.AppConstants;

/**
 * LoginPageTest class contains all the tests related to Login page test site
 */
public class LoginPageTest extends BaseTest {

    @Test(priority = 1)
    public void loginPageNavitationTest() {
        loginPage = mainPage.navigateToLoginPage();
        String actualLoginPageTitle = loginPage.getLoginPageTitle();
        LOGGER.info("The page title: " + actualLoginPageTitle);
        Assert.assertEquals(actualLoginPageTitle, AppConstants.LOGIN_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void forgotPasswordLinkExistTest() {
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Test(priority = 3)
    public void appLorinTest() {
        Assert.assertTrue(loginPage.doLogin(properties.getProperty("username").trim(),
                properties.getProperty("password").trim()));
    }

}
