package org.example.pages;

import com.github.javafaker.Faker;
import org.example.stepDefs.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class homePage {
    WebDriverWait wait;
    Faker fake = new Faker();
    Actions actions = new Actions(Hooks.driver);


    public homePage() {
        this.wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
        PageFactory.initElements(Hooks.driver, this);
    }

    //to close the popup
    public void closeOverlayIfPresent() {

        List<WebElement> overlayElements = Hooks.driver.findElements(By.xpath("//*[@class=\"brz-container\"]"));
        if (overlayElements.size() > 0) {
            WebElement overlayCloseButton = Hooks.driver.findElement(By.xpath("//*[@id=\"close-popup\"]"));
            overlayCloseButton.click();
        }
    }

    @FindBy(xpath = "//*[@id=\"HEADER_MENU_BAR_CATEGORY_1\"]//a")
    public WebElement storageMenuItem;

    @FindBy(xpath = "//*[@class=\"siteMenu__linksContainer\"]")
    public WebElement storageMenu;

    @FindBy(xpath = "//*[@id=\"HEADER_MENU_BAR_SUBCATEGORY_5\"]//a")
    public WebElement kitchenStorageSubcategory;

    @FindBy(xpath = "//*[@class=\"listing__card\"]//*[@class=\"productCard__container card-item\"]/a")
    public List<WebElement> AllProducts;

    @FindBy(xpath = "//*[@class=\"button-with-icon default-bg fullWidth\"]/span")
    public WebElement addToCartButton;

    //Add two random products to the cart and make the variables(name and price) public to use them for comparison.
    public String firstProductName;
    public String firstProductPrice;
    public String secondProductName;
    public String secondProductPrice;

    public void clickOnRandomProducts() throws InterruptedException {

        int productsSize = AllProducts.size();
        System.out.println("All products size: " + productsSize);

        int firstProductIndex = fake.number().numberBetween(1, productsSize);
        System.out.println("first Product Index: " + firstProductIndex);
        int secondProductIndex;
        do {
            secondProductIndex = fake.number().numberBetween(1, productsSize);
        } while (secondProductIndex == firstProductIndex);
        System.out.println("second Product Index: " + secondProductIndex);

        WebElement firstProduct = Hooks.driver.findElement(By.xpath("//*[@class=\"listing__card\"]//*[@class=\"productCard__container card-item\"][" + firstProductIndex + "]/a//*[@class=\"productCard__name\"]"));
        actions.moveToElement(firstProduct).perform();
        firstProductName = Hooks.driver.findElement(By.xpath("//*[@class=\"listing__card\"]/div[" + firstProductIndex + "]//h3")).getText();
        firstProductPrice = Hooks.driver.findElement(By.xpath("//*[@class=\"listing__card\"]/div[" + firstProductIndex + "]//*[@class=\"productPrices__prices\"][1]//p")).getText();
        firstProduct.click();
        addToCartButton.click();

        Hooks.driver.navigate().back();
        Thread.sleep(5000);

        WebElement secondProduct = Hooks.driver.findElement(By.xpath("//*[@class=\"listing__card\"]//*[@class=\"productCard__container card-item\"][" + secondProductIndex + "]/a//*[@class=\"productCard__name\"]"));
        actions.moveToElement(secondProduct).perform();
        secondProductName = Hooks.driver.findElement(By.xpath("//*[@class=\"listing__card\"]/div[" + secondProductIndex + "]//h3")).getText();
        secondProductPrice = Hooks.driver.findElement(By.xpath("//*[@class=\"listing__card\"]/div[" + secondProductIndex + "]//*[@class=\"productPrices__prices\"][1]//p")).getText();
        secondProduct.click();
        addToCartButton.click();
        Hooks.driver.navigate().back();
    }

    //cart locators
    @FindBy(xpath = "//*[@id=\"HEADER_CART_BRIEF_ICON\"]//*[@id=\"HEADER_CART_BRIEF_ITEMS_NUMBER\"]")
    public WebElement cartIcon;

    public By itemsInCart = By.xpath("//*[@class=\"cartItem__containerr orders-card-items\"]");

    @FindBy(xpath = "//*[@class=\"cart__detailsScroll\"]//*[@class=\"cartItem__containerr orders-card-items\"][1]//P[@class=\"cartItem__title\"]")
    public WebElement firstProductTitleInCart;

    @FindBy(xpath = "//*[@class=\"cart__detailsScroll\"]//*[@class=\"cartItem__containerr orders-card-items\"][1]//P[@class=\"cartItem__price\"]/span")
    public WebElement firstProductPriceInCart;

    @FindBy(xpath = "//*[@class=\"cart__detailsScroll\"]//*[@class=\"cartItem__containerr orders-card-items\"][2]//P[@class=\"cartItem__title\"]")
    public WebElement secondProductTitleInCart;

    @FindBy(xpath = "//*[@class=\"cart__detailsScroll\"]//*[@class=\"cartItem__containerr orders-card-items\"][2]//P[@class=\"cartItem__price\"]/span")
    public WebElement secondProductPriceInCart;

    @FindBy(xpath = "//*[@class=\"cart__subtotal hz-mb-20\"]//div[@data-v-7d20ee43]//b")
    public WebElement subTotalInCart;

}
