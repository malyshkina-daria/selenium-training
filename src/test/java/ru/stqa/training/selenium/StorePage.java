package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class StorePage extends OpenStorePageTest {

    @Test
    public void exercise8Test() {
        List<WebElement> webElements = driver.findElements(By.xpath("//div[@class='image-wrapper']"));
        for (WebElement webElement : webElements) {
            Assert.assertEquals(1, webElement.findElements(By.xpath(".//div[starts-with(@class,'sticker')]")).size());
        }
    }
}
