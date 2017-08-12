package ru.stqa.training.selenium;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class StorePage extends OpenStorePageTest {

    @Test
    public void exercise8Test() {
        List<WebElement> webElements = driver.findElements(By.xpath("//div[@class='image-wrapper']"));
        for (WebElement webElement : webElements) {
            Assert.assertEquals(1, webElement.findElements(By.xpath(".//div[starts-with(@class,'sticker')]")).size());
        }
    }

    @Test
    public void exercise11Test() {
        User user = new User();
        user.setEmail(getRandomString(7) + "@mail.ru");
        user.setPassword(getRandomString(15));
        createNewAccount(user);
        logout();
        login(user);
        logout();
    }

    private void login(User user) {
        driver.findElement(By.name("email")).sendKeys(user.getEmail());
        driver.findElement(By.name("password")).sendKeys(user.getPassword());
        driver.findElement(By.name("login")).click();
    }

    private void logout() {
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
    }

    private void createNewAccount(User user) {
        driver.findElement(By.xpath("//a[contains(text(),'New customers click here')]")).click();
        wait.until(titleIs("Create Account | My Store"));
        driver.findElement(By.name("firstname")).sendKeys(getRandomString(5));
        driver.findElement(By.name("lastname")).sendKeys(getRandomString(12));
        driver.findElement(By.name("address1")).sendKeys(getRandomString(10));
        driver.findElement(By.name("postcode")).sendKeys(getRandomValue(5));
        driver.findElement(By.name("city")).sendKeys(getRandomString(7));
        driver.findElement(By.className("select2-selection__rendered")).click();
        driver.findElement(By.xpath("//li[@class='select2-results__option'][contains(text(), 'United States')]")).click();
        driver.findElement(By.name("email")).sendKeys(user.getEmail());
        driver.findElement(By.name("phone")).sendKeys(getRandomValue(11));
        driver.findElement(By.name("password")).sendKeys(user.getPassword());
        driver.findElement(By.name("confirmed_password")).sendKeys(user.getPassword());
        driver.findElement(By.name("create_account")).click();
    }

    private String getRandomValue(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    private String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }
}
