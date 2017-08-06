package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AdminPage extends LoginAdminPageTest {

    @Test
    public void exercise7Test() {
        List<WebElement> menuWebElements = driver.findElements(By.xpath("//li[@id='app-']"));
        for (int i = 1; i < menuWebElements.size() + 1; i++) {
            driver.findElement(By.xpath("//li[@id='app-'][" + i + "]/a/span[@class='name']")).click();
            Assert.assertTrue(isElementPresent(By.xpath("//td[@id='content']/h1")));
            if (isElementPresent(By.cssSelector(".docs"))) {
                List<WebElement> subMenuWebElements = driver.findElements(By.xpath("//li[starts-with(@id,'doc')]"));
                for (int j = 1; j < subMenuWebElements.size() + 1; j++) {
                    driver.findElement(By.xpath("//li[starts-with(@id,'doc')][" + j + "]/a/span[@class='name']")).click();
                    Assert.assertTrue(isElementPresent(By.xpath("//td[@id='content']/h1")));
                }
            }
        }
    }
}
