package ru.gotoqa.constants;

import org.testng.annotations.DataProvider;

/**
 * DataProviderClass class contains all the constants used in the test framework
 */
public class DataProviderClass {
    @DataProvider
    public static Object[][] testDataStuffSearch() {
        return new Object[][]{
                {"Macbook"},
                {"iMac"},
                {"Samsung"}
        };
    }
}
