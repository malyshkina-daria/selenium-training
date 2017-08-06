package ru.stqa.training.selenium;

import org.junit.BeforeClass;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class OpenStorePageTest extends BaseTest {

    @BeforeClass
    public static void openLineTest() {
        driver.get("http://localhost/en/");
        wait.until(titleIs("Online Store | My Store"));
    }
}
