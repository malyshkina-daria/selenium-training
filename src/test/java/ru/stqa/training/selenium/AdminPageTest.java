package ru.stqa.training.selenium;

import objects.Product;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import utils.FileUpload;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static utils.Utils.getRandomString;
import static utils.Utils.getRandomValue;

public class AdminPageTest extends LoginAdminPageTest {

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
    public void exercise14Test() {
        clickOnMenuByText("Countries");
        WebElement row = driver.findElement(By.xpath("//tr[@class='row'][" + (getRandomValue(1) + 1) + "]"));
        row.findElement(By.xpath(".//td[7]//i[@class='fa fa-pencil']")).click();
        wait.until(titleContains("Edit Country | My Store"));
        List<WebElement> externalLinks = driver.findElements(By.xpath("//form[@method='post']//a[@target='_blank']"));
        openAndCloseNewWindow(externalLinks);
    }

    private void openAndCloseNewWindow(List<WebElement> webElementList) {
        for (WebElement element : webElementList) {
            String mainWindow = driver.getWindowHandle();
            Set<String> existingWindows = driver.getWindowHandles();
            element.click();
            String newWindow = wait.until(newWindowIsOpen(existingWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    private ExpectedCondition<String> newWindowIsOpen(Set<String> existingWindows) {
        return webDriver -> {
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(existingWindows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
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

    @Test
    public void exercise12Test() {
        clickOnMenuByText("Catalog");
        addNewProduct();
        Assert.assertTrue(checkAddedProductIsPresent());
    }

    private boolean checkAddedProductIsPresent() {
        return driver.findElement(By.xpath("//a[contains(text(), 'Elephant')]")).isDisplayed();
    }

    private void addNewProduct() {
        driver.findElement(By.xpath("//a[@class='button'][contains(text(), ' Add New Product')]")).click();
        Product product = new Product();
        product.setProductName("Elephant");
        fillGeneralInfoAboutNewProduct(product);
        fillInformationAboutNewProduct();
        fillPricesAboutNewProduct();
        driver.findElement(By.name("save")).click();
    }

    private void fillGeneralInfoAboutNewProduct(Product product) {
        driver.findElement(By.xpath("//strong[contains(text(), 'Status')]")).isDisplayed();
        driver.findElement(By.xpath("//input[@type='radio'][@value='1']")).click();
        driver.findElement(By.name("name[en]")).sendKeys(product.getProductName());
        driver.findElement(By.name("code")).sendKeys(getRandomString(2) + getRandomValue(3));
        enterValueInInputTypeNumber("quantity", "30");
        selectOptionByValue("sold_out_status_id", "2");
        File file = FileUpload.getRandomFileFromFolder(FileUpload.getFolder());
        uploadNewFile(file);
    }

    private void fillInformationAboutNewProduct() {
        driver.findElement(By.xpath("//strong[contains(text(), 'Manufacturer')]")).isDisplayed();
        driver.findElement(By.xpath("//div[@class='tabs']//a[contains(text(), 'Information')]")).click();
        selectOptionByValue("manufacturer_id", "1");
        driver.findElement(By.name("short_description[en]")).sendKeys("Leather mini bag in the shape of elephant");
    }

    private void fillPricesAboutNewProduct() {
        driver.findElement(By.xpath("//h2[contains(text(), 'Prices')]")).isDisplayed();
        driver.findElement(By.xpath("//div[@class='tabs']//a[contains(text(), 'Prices')]")).click();
        enterValueInInputTypeNumber("purchase_price", "350");
        selectOptionByValue("purchase_price_currency_code", "USD");
    }

    private void selectOptionByValue(String nameSelect, String value) {
        WebElement select = driver.findElement(By.name(nameSelect));
        Select dropdown = new Select(select);
        dropdown.selectByValue(value);
    }

    private void enterValueInInputTypeNumber(String nameSelect, String value) {
        WebElement webElement = driver.findElement(By.name(nameSelect));
        webElement.sendKeys(Keys.CONTROL + "a");
        webElement.sendKeys(value);
    }

    private void uploadNewFile(File file) {
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(file.getAbsolutePath());
    }

    @Test
    public void exercise17Test() {
        clickOnMenuByText("Catalog");
        driver.findElement(By.xpath("//a[contains(text(), 'Rubber Ducks')]")).click();
        List<WebElement> ducksList = driver.findElements(By.xpath("//tr[@class='row']/td[3]/a[contains(text(), 'Duck')]"));
        for (int i = 0; i < ducksList.size(); i++) {
            driver.findElement(By.xpath("//tr[@class='row'][" + (i + 4) + "]/td[3]/a[contains(text(), 'Duck')]")).click();
            driver.findElement(By.name("cancel")).click();
//            driver.manage().logs().get(LogType.BROWSER).forEach(System.out::println);
        }
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        if (logEntries.getAll().size() == 0) {
            System.out.println("Log Browser Messages are missing");
        } else {
            logEntries.forEach(System.out::println);
        }
    }
}

