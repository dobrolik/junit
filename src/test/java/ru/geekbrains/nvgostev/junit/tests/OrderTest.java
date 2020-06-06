package ru.geekbrains.nvgostev.junit.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.geekbrains.nvgostev.junit.common.Account;
import ru.geekbrains.nvgostev.junit.common.AccountDataGenerator;
import ru.geekbrains.nvgostev.junit.pom.Header;
import ru.geekbrains.nvgostev.junit.pom.ShoppingCart;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("http://automationpractice.com - checkout order test")
@Execution(ExecutionMode.CONCURRENT)
public class OrderTest extends BaseUITest {
    private static Iterator<Account> accountIterator;

    @BeforeAll
    public static void setAccounts() {
        AccountDataGenerator accountDataGenerator = new AccountDataGenerator(3);
        List<Account> accounts = accountDataGenerator.genAccounts(3);
        accountIterator = accounts.iterator();
    }

    @BeforeEach
    public void createAccount() {
        Account account = accountIterator.next();
        mainPage.home();
        mainPage.goToSignIn();
        signInPage.enterEmailAndGoToRegistration(account.getEmail());
        registrationPage.fillFormAndSubmit(account);
    }

    private static Stream<Arguments> addToCartAndCheckoutDataSupplier() {
        return Stream.of(
                Arguments.of((Object) new String[]{"Faded Short Sleeve T-shirts", "Blouse"}),
                Arguments.of((Object) new String[]{"Blouse", "Printed Dress"}),
                Arguments.of((Object) new String[]{"Faded Short Sleeve T-shirts", "Blouse", "Printed Dress"})
        );
    }


    @DisplayName("Checks: main page - Create account - Add to cart - Checkout order")
    @ParameterizedTest(name = "{index} ==> checkout order test")
    @MethodSource("addToCartAndCheckoutDataSupplier")
    public void addToCartAndCheckout(String... products) {
        accountPage.selectShoppingCategory(Header.ShoppingCategory.WOMEN);
        for (String product:products) {
            shopPage.addProductToCart(product);
            shopPage.continueShopping();
        }
        shopPage.viewCart();
        assertTrue(shoppingCart.isStepActive(ShoppingCart.CheckoutStep.SUMMARY));
        shoppingCart.proceedToNextStep();
        assertTrue(shoppingCart.isStepActive(ShoppingCart.CheckoutStep.ADDRESS));
        shoppingCart.proceedToNextStep();
        assertTrue(shoppingCart.isStepActive(ShoppingCart.CheckoutStep.SHIPPING));
        shoppingCart.agreeTermsOfService();
        shoppingCart.proceedToNextStep();
        assertTrue(shoppingCart.isStepActive(ShoppingCart.CheckoutStep.PAYMENT));
        shoppingCart.selectPaymentMethod(ShoppingCart.PaymentMethod.CHECK);
        assertEquals(ShoppingCart.PaymentMethod.CHECK.getText(), shoppingCart.getPaymentMethodText());
        shoppingCart.confirmOrder();
        assertTrue(shoppingCart.isAlertSuccessPresented());
        assertEquals("Your order on My Store is complete.", shoppingCart.getAlertSuccessText());
    }
}
