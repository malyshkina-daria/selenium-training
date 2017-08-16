package ru.stqa.training.selenium;

import org.openqa.selenium.WebElement;

class Product {

    private String productName;
    private WebElement regularPrice;
    private WebElement campaignPrice;
    private String regularPriceValue;
    private String campaignPriceValue;

    String getProductName() {
        return productName;
    }

    void setProductName(String productName) {
        this.productName = productName;
    }

    WebElement getRegularPrice() {
        return regularPrice;
    }

    void setRegularPrice(WebElement regularPrice) {
        this.regularPrice = regularPrice;
    }

    WebElement getCampaignPrice() {
        return campaignPrice;
    }

    void setCampaignPrice(WebElement campaignPrice) {
        this.campaignPrice = campaignPrice;
    }

    String getRegularPriceValue() {
        return regularPriceValue;
    }

    void setRegularPriceValue(String regularPriceValue) {
        this.regularPriceValue = regularPriceValue;
    }

    String getCampaignPriceValue() {
        return campaignPriceValue;
    }

    void setCampaignPriceValue(String campaignPriceValue) {
        this.campaignPriceValue = campaignPriceValue;
    }

}
