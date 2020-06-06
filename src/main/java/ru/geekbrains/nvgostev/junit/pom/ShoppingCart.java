package ru.geekbrains.nvgostev.junit.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingCart extends BaseActions {

    private static final By PAGE_HEADER = By.cssSelector(".page-heading");
    private static final By CART_ITEMS_LIST = By.cssSelector("#order-detail-content > table > tbody > tr ");
    private static final By CART_ITEM_NAME = By.cssSelector(".product-name");
    private static final By CART_ITEM_QTY = By.cssSelector("[class=\"cart_quantity_input form-control grey\"]");

    private static final By SUMMARY_STEP_ACTIVE = By.cssSelector("[class=\"step_current  first\"]");
    private static final By ADDRESS_STEP_ACTIVE = By.cssSelector("[class=\"step_current third\"]");
    private static final By SHIPPING_STEP_ACTIVE = By.cssSelector("[class=\"step_current four\"]");
    private static final By PAYMENT_STEP_ACTIVE = By.cssSelector("[class=\"step_current last\"]");

    private static final By PROCEED_TO_ADDR_BTN = By.cssSelector("[class=\"button btn btn-default standard-checkout button-medium\"]");
    private static final By PROCEED_TO_SHIPPING_BTN = By.cssSelector("button[name=\"processAddress\"]");
    private static final By PROCEED_TO_PAYMENT_BTN = By.cssSelector("button[name=\"processCarrier\"]");

    private static final By AGREE_TERMS_CHECKBOX = By.cssSelector("#cgv");

    private static final By BANK_WIRE_PAY_BTN = By.cssSelector(".bankwire");
    private static final By CHECK_PAY_BTN = By.cssSelector(".cheque");

    private static final By PAYMENT_METHOD = By.cssSelector(".page-subheading");
    private static final By CONFIRM_ORDER_BTN = By.cssSelector("[class=\"button btn btn-default button-medium\"]");

    private static final By ALERT_SUCCESS = By.cssSelector("[class=\"alert alert-success\"]");

    private CheckoutStep currentStep;

    public ShoppingCart(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        currentStep = CheckoutStep.SUMMARY;
    }

    public boolean isPageHeaderPresent() {
        return isElementPresent(PAGE_HEADER);
    }

    public String getPageHeaderText() {
        return driver.findElement(PAGE_HEADER).getText();
    }

    public boolean isItemInCart(String itemName) {
        return findElement(CART_ITEMS_LIST, CART_ITEM_NAME, itemName).isDisplayed();
    }

    public int getItemQty(String itemName) {
        return Integer.parseInt(
                findElement(CART_ITEMS_LIST, CART_ITEM_NAME, itemName)
                        .findElement(CART_ITEM_QTY).getAttribute("value")
        );
    }

    public void proceedToNextStep() {
        switch (currentStep) {
            case SUMMARY:
                click(PROCEED_TO_ADDR_BTN);
                currentStep = CheckoutStep.ADDRESS;
                break;
            case ADDRESS:
                click(PROCEED_TO_SHIPPING_BTN);
                currentStep = CheckoutStep.SHIPPING;
                break;
            case SHIPPING:
                click(PROCEED_TO_PAYMENT_BTN);
                currentStep = CheckoutStep.PAYMENT;
                break;
            default:
                throw new IllegalStateException("Cannot proceed to next step from current: " + currentStep);
        }
    }

    public void agreeTermsOfService() {
        if (currentStep == CheckoutStep.SHIPPING) {
            driver.findElement(AGREE_TERMS_CHECKBOX).click();
        } else {
            throw new RuntimeException("Cannot agree terms of service on current step: " + currentStep);
        }
    }

    public void selectPaymentMethod(PaymentMethod paymentMethod) {
        if (currentStep != CheckoutStep.PAYMENT) {
            throw new RuntimeException("Cannot select payment method on current step: " + currentStep);
        }
        switch (paymentMethod) {
            case BANK_WIRE:
                click(BANK_WIRE_PAY_BTN);
                break;
            case CHECK:
                click(CHECK_PAY_BTN);
                break;
        }
    }

    public String getPaymentMethodText() {
        if (!isElementPresent(PAYMENT_METHOD)) {
            throw new RuntimeException("Payment method not presented. Possible reason: payment method not selected");
        }
        return driver.findElement(PAYMENT_METHOD).getText();
    }

    public void confirmOrder() {
        if (currentStep != CheckoutStep.PAYMENT) {
            throw new RuntimeException("Cannot confirm order on current step: " + currentStep);
        }
        if (!isElementPresent(CONFIRM_ORDER_BTN)) {
            throw new RuntimeException("Cannot confirm order. Possible reason: payment method not selected");
        }
        click(CONFIRM_ORDER_BTN);
    }

    public boolean isAlertSuccessPresented() {
        return isElementPresent(ALERT_SUCCESS);
    }

    public String getAlertSuccessText() {
        return driver.findElement(ALERT_SUCCESS).getText();
    }

    public boolean isStepActive(CheckoutStep step) {
        By stepSelector;
        switch (step) {
            case SUMMARY:
                stepSelector = SUMMARY_STEP_ACTIVE;
                break;
            case ADDRESS:
                stepSelector = ADDRESS_STEP_ACTIVE;
                break;
            case SHIPPING:
                stepSelector = SHIPPING_STEP_ACTIVE;
                break;
            case PAYMENT:
                stepSelector = PAYMENT_STEP_ACTIVE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + step);
        }
        return isElementPresent(stepSelector);
    }

    public enum CheckoutStep {
        SUMMARY,
        ADDRESS,
        SHIPPING,
        PAYMENT
    }

    public enum PaymentMethod {
        BANK_WIRE("BANK-WIRE PAYMENT."),
        CHECK("CHECK PAYMENT");

        private String text;

        PaymentMethod(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
