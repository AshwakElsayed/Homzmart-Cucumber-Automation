package org.example.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.homePage;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class home {
    homePage home = new homePage();
    SoftAssert soft = new SoftAssert();
    Actions actions = new Actions(Hooks.driver);
    ArrayList<String> tabs;


    @Given("User Hover on Storage")
    public void userHoverOnStorage() {
        home.closeOverlayIfPresent();
        actions.moveToElement(home.storageMenuItem).perform();
    }

    @When("Confirm that the dropdown menu appears and including Kitchen Storage subcategory.")
    public void confirmThatTheDropdownMenuAppearsAndIncludingKitchenStorageSubcategory() {
        home.closeOverlayIfPresent();
        soft.assertTrue(home.storageMenu.isDisplayed());
        soft.assertTrue(home.kitchenStorageSubcategory.isDisplayed());
        soft.assertAll();
    }

    @And("Click on Kitchen Storage")
    public void clickOnKitchenStorage() {
        home.closeOverlayIfPresent();
        home.kitchenStorageSubcategory.click();
        //to move the mouse away
        actions.moveByOffset(500, 500).perform();
    }

    @And("Choose two random products and add them to the cart")
    public void chooseTwoRandomProductsAndAddThemToTheCart() throws InterruptedException {
        home.closeOverlayIfPresent();
        home.clickOnRandomProducts();
    }

    @And("Open the cart preview")
    public void openTheCartPreview() {
        Hooks.driver.switchTo().newWindow(WindowType.TAB).get("https://homzmart.com/en/c/storage/kitchen-storage");
        tabs = new ArrayList<>(Hooks.driver.getWindowHandles());
        Hooks.driver.switchTo().window(tabs.get(1));
        home.cartIcon.click();
    }

    @Then("Wait until making sure that all elements are added to the cart")
    public void waitUntilMakingSureThatAllElementsAreAddedToTheCart() {
        soft.assertEquals(Hooks.driver.findElements(home.itemsInCart).size(), 2);
        soft.assertAll();
    }

    @Then("Compare every product’s name and price in the cart with product details")
    public void compareEveryProductSNameAndPriceInTheCartWithProductDetails() throws InterruptedException {

        //in the home tab
        System.out.println("first product in home: " + home.firstProductName);
        System.out.println("first product in home: " + home.firstProductPrice.replace(",", "").split("\\.")[0]);
        System.out.println("second product in home: " + home.secondProductName);
        System.out.println("second product in home: " + home.secondProductPrice.replace(",", "").split("\\.")[0]);
        // in the cart tab
        Thread.sleep(5000);
        System.out.println("first product in card: " + home.firstProductTitleInCart.getText());
        System.out.println("first product in card: " + home.firstProductPriceInCart.getText());
        System.out.println("second product in card: " + home.secondProductTitleInCart.getText());
        System.out.println("second product in card: " + home.secondProductPriceInCart.getText());
        // assertions
        soft.assertEquals(home.firstProductName, home.firstProductTitleInCart.getText(), "The names of the two products are not the same.");
        soft.assertEquals(home.firstProductPrice.replace(",", "").split("\\.")[0], home.firstProductPriceInCart.getText(), "The prices of the two products are not equal.");
        soft.assertEquals(home.secondProductName, home.secondProductTitleInCart.getText(), "The names of the two products are not the same.");
        soft.assertEquals(home.secondProductPrice.replace(",", "").split("\\.")[0], home.secondProductPriceInCart.getText(), "The prices of the two products are not equal.");
        soft.assertAll();
    }

    @Then("Check that the total price in the cart is calculated correctly")
    public void checkThatTheTotalPriceInTheCartIsCalculatedCorrectly() {

        int firstOneNum = Integer.parseInt(home.firstProductPriceInCart.getText());
        int SecondOneNum = Integer.parseInt(home.secondProductPriceInCart.getText());
        int sumPrices = firstOneNum + SecondOneNum;
        System.out.println("sumPrices: " + sumPrices);
        int subTotal = Integer.parseInt(home.subTotalInCart.getText().replaceAll("[^0-9]", ""));
        System.out.println("subTotal in cart: " + subTotal);
        soft.assertEquals(sumPrices, subTotal, "subTotal price not correct");
        soft.assertAll();
    }
}
