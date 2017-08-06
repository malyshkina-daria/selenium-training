package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
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

    @Test
    public void exercise9Test() {
        clickOnMenuByText("Countries");
        Assert.assertTrue(countriesIsSortedOnCountryPage());
        clickOnMenuByText("Geo Zones");
        countriesIsSortedOnGeoZonesPage();
    }

    private boolean countriesIsSortedOnCountryPage() {
        List<String> countriesList = new ArrayList<>();
        List<WebElement> countriesWebElementList = driver.findElements(By.xpath("//tr[@class='row']"));
        for (int i = 0; i < countriesWebElementList.size(); i++) {
            WebElement row = driver.findElement(By.xpath("//tr[@class='row'][" + (i + 1) + "]"));
            WebElement currentElement = row.findElement(By.xpath(".//td[5]/a"));
            countriesList.add(currentElement.getText());
            if (Integer.parseInt(row.findElement(By.xpath(".//td[6]")).getText()) != 0) {
                currentElement.click();
                List<String> zonesList = new ArrayList<>();
                List<WebElement> zonesWebElement = driver.findElements(By.xpath("//td[3]/input[starts-with(@name,'zone')]"));
                for (WebElement webElementZone : zonesWebElement) {
                    zonesList.add(webElementZone.getText());
                }
                Assert.assertTrue(listIsSorted(zonesList));
                driver.findElement(By.xpath("//button[@name='cancel']")).click();
            }
        }
        return listIsSorted(countriesList);
    }

    private void countriesIsSortedOnGeoZonesPage() {
        List<WebElement> countriesWebElementList = driver.findElements(By.xpath("//tr[@class='row']"));
        for (int i = 0; i < countriesWebElementList.size(); i++) {
            WebElement row = driver.findElement(By.xpath("//tr[@class='row'][" + (i + 1) + "]"));
            row.findElement(By.xpath(".//td[3]/a")).click();
            List<String> zonesList = new ArrayList<>();
            List<WebElement> zonesWebElement = driver.findElements(By.xpath("//td[3]/select[starts-with(@name,'zones')]"));
            for (WebElement webElementZone : zonesWebElement) {
                zonesList.add(webElementZone.findElement(By.xpath(".//option[@selected='selected']")).getText());
            }
            Assert.assertTrue(listIsSorted(zonesList));
            driver.findElement(By.xpath("//button[@name='cancel']")).click();
        }
    }

    private boolean listIsSorted(List<String> list) {
        List<String> sortableList = new ArrayList<>(list);
        Collections.sort(sortableList);
        return list.size() == sortableList.size() && list.containsAll(sortableList);
    }

    private void clickOnMenuByText(String text) {
        driver.findElement(By.xpath(String.format("//*[contains(text(),'%s')]", text))).click();
    }
}
