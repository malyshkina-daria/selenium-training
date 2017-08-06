package ru.stqa.training.selenium;

import org.junit.BeforeClass;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LoginAdminPageTest extends BaseTest {

    @BeforeClass
    public static void loginTest() {
        driver.navigate().to("http://localhost/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }
}
