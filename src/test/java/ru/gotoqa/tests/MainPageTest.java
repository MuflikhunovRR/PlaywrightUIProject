package ru.gotoqa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.gotoqa.base.BaseTest;
import ru.gotoqa.constants.AppConstants;
import ru.gotoqa.constants.DataProviderClass;

/**
 * MainPageTest class contains all the tests related to Main page test site
 */
public class MainPageTest extends BaseTest {

    @Test
    public void testHomePageTitle() {
        String actualTitle = mainPage.getHomePageTitle();
        Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
    }

    @Test
    public void testHomePageURL() {
        String actualUrl = mainPage.getHomePageURL();
        Assert.assertEquals(actualUrl, properties.getProperty("url"));
    }

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "testDataStuffSearch")
    public void testSearch(String productName) {
        String actualSearchHeader = mainPage.searchForProduct(productName);
        Assert.assertEquals(actualSearchHeader, "Search - " + productName);
    }

}
