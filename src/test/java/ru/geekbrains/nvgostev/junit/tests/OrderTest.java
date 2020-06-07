package ru.geekbrains.nvgostev.junit.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.geekbrains.nvgostev.junit.common.Account;
import ru.geekbrains.nvgostev.junit.common.AccountDataGenerator;
import ru.geekbrains.nvgostev.junit.pom.AccountPage;
import ru.geekbrains.nvgostev.junit.pom.Header;
import ru.geekbrains.nvgostev.junit.pom.MainPage;
import ru.geekbrains.nvgostev.junit.pom.ShoppingCart;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class OrderTest extends BaseUITest {
    private static Iterator<Account> accountIterator;
    private static final ThreadLocal<AccountPage> accountPage = new ThreadLocal<>();

    @BeforeClass
    public static void prepareAccounts() {
        AccountDataGenerator accountDataGenerator = new AccountDataGenerator(3);
        List<Account> accounts = accountDataGenerator.genAccounts(3);
        accountIterator = accounts.iterator();
    }

    @BeforeMethod
    public void createAccount() {
        Account account = accountIterator.next();
        accountPage.set(new MainPage(getDriver(), getWait())
                .home()
                .getHeader().sigIn()
                .enterEmailAndGoToRegistration(account.getEmail())
                .fillFormAndSubmit(account));
    }

    @DataProvider(name = "addToCartAndCheckoutDataSupplier")
    private static Object[][] addToCartAndCheckoutDataSupplier() {
        return new Object[][]{
                {"Faded Short Sleeve T-shirts", "Blouse"},
                {"Blouse", "Printed Dress"},
                {"Faded Short Sleeve T-shirts", "Blouse", "Printed Dress"}
        };
    }

    private void checkStep(ShoppingCart shoppingCart, ShoppingCart.CheckoutStep checkoutStep) {
        assertThat("Current checkout step is correct", shoppingCart.isStepActive(checkoutStep), equalTo(true));
    }

    @Test(
            description = "Checks: Add to cart - Checkout order",
            dataProvider = "addToCartAndCheckoutDataSupplier"
    )
    public void addToCartAndCheckout(String... products) {
        ShoppingCart shoppingCart = accountPage.get()
                .getHeader().openCategory(Header.ShoppingCategory.WOMEN)
                .addProductsToCart(products)
                .getHeader().viewCart();
        checkStep(shoppingCart, ShoppingCart.CheckoutStep.SUMMARY);
        shoppingCart.proceedToNextStep();
        checkStep(shoppingCart, ShoppingCart.CheckoutStep.ADDRESS);
        shoppingCart.proceedToNextStep();
        checkStep(shoppingCart, ShoppingCart.CheckoutStep.SHIPPING);
        shoppingCart.agreeTermsOfService();
        shoppingCart.proceedToNextStep();
        checkStep(shoppingCart, ShoppingCart.CheckoutStep.PAYMENT);
        shoppingCart.selectPaymentMethod(ShoppingCart.PaymentMethod.CHECK);
        assertThat("Payment method text is correct", shoppingCart.getPaymentMethodText(), equalTo(ShoppingCart.PaymentMethod.CHECK.getText()));
        shoppingCart.confirmOrder();
        assertThat("Alert-success is presented", shoppingCart.isAlertSuccessPresented(), equalTo(true));
        assertThat("Alert-success text is coreect", shoppingCart.getAlertSuccessText(), equalTo("Your order on My Store is complete."));
    }
}
