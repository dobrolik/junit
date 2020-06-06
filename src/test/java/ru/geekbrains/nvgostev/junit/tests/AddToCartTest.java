package ru.geekbrains.nvgostev.junit.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.geekbrains.nvgostev.junit.common.Account;
import ru.geekbrains.nvgostev.junit.common.AccountDataGenerator;
import ru.geekbrains.nvgostev.junit.pom.Header;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("http://automationpractice.com - sign in and add to cart test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddToCartTest extends BaseUITest {
    private static List<Account> accounts;

    private static Stream<Account> createAccountsDataSupplier() {
        AccountDataGenerator accountDataGenerator = new AccountDataGenerator(3);
        accounts = accountDataGenerator.genAccounts(3);
        return accounts.stream();
    }

    @Order(1)
    @DisplayName("Order 1 - Creating accounts for next test")
    @ParameterizedTest(name = "{index} ==> Creating {0}")
    @MethodSource("createAccountsDataSupplier")
    public void createAccounts(Account acc) {
        mainPage.home();
        mainPage.goToSignIn();
        signInPage.enterEmailAndGoToRegistration(acc.getEmail());
        registrationPage.fillFormAndSubmit(acc);
    }

    private static Stream<Arguments> addItemsAndCheckCartDataSupplier() {
        return Stream.of(
                Arguments.of(accounts.get(0), "Faded Short Sleeve T-shirts"),
                Arguments.of(accounts.get(1), "Blouse"),
                Arguments.of(accounts.get(2), "Printed Dress")
        );
    }

    @Order(2)
    @DisplayName("Order 2 - Checks: main page - Sign in - Add to cart - view cart")
    @ParameterizedTest(name = "{index} ==> {0} Adding to cart: {1}")
    @MethodSource("addItemsAndCheckCartDataSupplier")
    public void addItemsAndCheckCart(Account account, String product) {
        mainPage.home();
        mainPage.goToSignIn();
        signInPage.signIn(account.getEmail(), account.getPassword());
        accountPage.selectShoppingCategory(Header.ShoppingCategory.WOMEN);
        shopPage.addProductToCart(product);
        shopPage.continueShopping();
        shopPage.viewCart();
        assertTrue(shoppingCart.isItemInCart(product));
        assertEquals(1, shoppingCart.getItemQty(product));
    }
}
