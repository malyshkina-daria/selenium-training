package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static ru.stqa.training.selenium.Utils.getRandomString;
import static ru.stqa.training.selenium.Utils.getRandomValue;

public class StorePage extends OpenStorePageTest {

    @Test
    public void exercise8Test() {
        List<WebElement> webElements = driver.findElements(By.xpath("//div[@class='image-wrapper']"));
        for (WebElement webElement : webElements) {
            Assert.assertEquals(1, webElement.findElements(By.xpath(".//div[starts-with(@class,'sticker')]")).size());
        }
    }

    @Test
    public void exercise10Test() {
        String campaignsProductOnGeneralPageSelector = "//h3[contains(text(),'Campaigns')]/following-sibling::div//a[@class='link'][1]";
        WebElement campaignsProductOnGeneralPage = driver.findElement(By.xpath(campaignsProductOnGeneralPageSelector));
        Product productOnGeneralPage = getProductOnGeneralPage(campaignsProductOnGeneralPageSelector);
        Assert.assertTrue(checkStyleForRegularPrice(productOnGeneralPage));
        Assert.assertTrue(checkColorForRegularPriceOnGeneralPage(productOnGeneralPage));
        Assert.assertTrue(checkSizeForRegularPriceOnGeneralPage(productOnGeneralPage));
        Assert.assertTrue(checkStyleForCampaignPrice(productOnGeneralPage));
        Assert.assertTrue(checkSizeForCampaignPriceOnGeneralPage(productOnGeneralPage));
        campaignsProductOnGeneralPage.click();
        wait.until(titleIs("Yellow Duck | Subcategory | Rubber Ducks | My Store"));
        Product productOnViewProductPage = getProductOnViewProductPage();
        Assert.assertTrue(checkProductsIsEquals(productOnGeneralPage, productOnViewProductPage));
        Assert.assertTrue(checkStyleForRegularPrice(productOnViewProductPage));
        Assert.assertTrue(checkColorForRegularPriceOnViewProductPage(productOnViewProductPage));
        Assert.assertTrue(checkSizeForRegularPriceOnViewProductPage(productOnViewProductPage));
        Assert.assertTrue(checkStyleForCampaignPrice(productOnViewProductPage));
        Assert.assertTrue(checkSizeForCampaignPriceOnViewProductPage(productOnViewProductPage));
    }

    private boolean checkStyleForRegularPrice(Product product) {
        return product.getRegularPrice().getCssValue("text-decoration").contains("line-through");
    }

    private boolean checkSizeForRegularPriceOnGeneralPage(Product product) {
        return !product.getRegularPrice().getCssValue("font-size").contains("18px");
    }

    private boolean checkSizeForRegularPriceOnViewProductPage(Product product) {
        return !product.getRegularPrice().getCssValue("font-size").contains("22px");
    }

    private boolean checkColorForRegularPriceOnGeneralPage(Product product) {
        return product.getRegularPrice().getCssValue("color").contains("rgb") &&
                product.getRegularPrice().getCssValue("color").contains("119, 119, 119");
    }

    private boolean checkColorForRegularPriceOnViewProductPage(Product product) {
        return product.getRegularPrice().getCssValue("color").contains("rgb") &&
                product.getRegularPrice().getCssValue("color").contains("102, 102, 102");
    }

    private boolean checkStyleForCampaignPrice(Product product) {
        return product.getCampaignPrice().getCssValue("font-weight").contains("bold") &&
                product.getCampaignPrice().getCssValue("color").contains("rgb") &&
                product.getCampaignPrice().getCssValue("color").contains("204, 0, 0");
    }

    private boolean checkSizeForCampaignPriceOnGeneralPage(Product product) {
        return product.getCampaignPrice().getCssValue("font-size").contains("18px");
    }

    private boolean checkSizeForCampaignPriceOnViewProductPage(Product product) {
        return product.getCampaignPrice().getCssValue("font-size").contains("22px");
    }

    private Product getProductOnGeneralPage(String selector) {
        Product product = new Product();
        product.setProductName(driver.findElement(By.xpath(selector + "/div[@class='name']")).getText());
        product.setRegularPrice(driver.findElement(By.xpath(selector + "//s[@class='regular-price']")));
        product.setCampaignPrice(driver.findElement(By.xpath(selector + "//strong[@class='campaign-price']")));
        product.setRegularPriceValue(product.getRegularPrice().getText());
        product.setCampaignPriceValue(product.getCampaignPrice().getText());
        return product;
    }

    private Product getProductOnViewProductPage() {
        Product product = new Product();
        product.setProductName(driver.findElement(By.xpath("//h1[@class='title']")).getText());
        product.setRegularPrice(driver.findElement(By.xpath("//div[@class='price-wrapper']/s[@class='regular-price']")));
        product.setCampaignPrice(driver.findElement(By.xpath("//div[@class='price-wrapper']/strong[@class='campaign-price']")));
        product.setRegularPriceValue(product.getRegularPrice().getText());
        product.setCampaignPriceValue(product.getCampaignPrice().getText());
        return product;
    }

    private boolean checkProductsIsEquals(Product productOnGeneralPage, Product productOnViewProductPage) {
        return productOnGeneralPage.getProductName().equals(productOnViewProductPage.getProductName()) &&
                productOnGeneralPage.getRegularPriceValue().equals(productOnViewProductPage.getRegularPriceValue()) &&
                productOnGeneralPage.getCampaignPriceValue().equals(productOnViewProductPage.getCampaignPriceValue());
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
}
