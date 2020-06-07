package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShopPage extends ContentPage {

    private static final By PRODUCTS_LIST = By.cssSelector("ul[class=\"product_list grid row\"] > li ");

    private static final By PRODUCT_NAME = By.cssSelector("a.product-name");
    private static final By PRODUCT_ADD_TO_CART_BTN = By.cssSelector("a[title=\"Add to cart\"]");

    private static final By CONTINUE_SHOPPING_BTN = By.cssSelector("[title=\"Continue shopping\"]");

    public ShopPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public ShopPage addProductToCart(String productName) {
        WebElement product = findElement(PRODUCTS_LIST, PRODUCT_NAME, productName);
        hoverElement(product);
        click(product.findElement(PRODUCT_ADD_TO_CART_BTN));
        return this;
    }

    public ShopPage addProductsToCart(String... productNames) {
        for (String productName : productNames) {
            addProductToCart(productName);
            continueShopping();
        }
        return this;
    }

    public ShopPage continueShopping() {
        click(CONTINUE_SHOPPING_BTN);
        return this;
    }
}
