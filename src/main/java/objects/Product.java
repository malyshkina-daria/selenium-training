package objects;

import org.openqa.selenium.WebElement;

public class Product {

    private String productName;
    private WebElement regularPrice;
    private WebElement campaignPrice;
    private String regularPriceValue;
    private String campaignPriceValue;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public WebElement getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(WebElement regularPrice) {
        this.regularPrice = regularPrice;
    }

    public WebElement getCampaignPrice() {
        return campaignPrice;
    }

    public void setCampaignPrice(WebElement campaignPrice) {
        this.campaignPrice = campaignPrice;
    }

    public String getRegularPriceValue() {
        return regularPriceValue;
    }

    public void setRegularPriceValue(String regularPriceValue) {
        this.regularPriceValue = regularPriceValue;
    }

    public String getCampaignPriceValue() {
        return campaignPriceValue;
    }

    public void setCampaignPriceValue(String campaignPriceValue) {
        this.campaignPriceValue = campaignPriceValue;
    }

}
