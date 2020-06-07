package ru.geekbrains.nvgostev.junit.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.geekbrains.nvgostev.junit.common.Account;
import ru.geekbrains.nvgostev.junit.common.AccountDataGenerator;
import ru.geekbrains.nvgostev.junit.pom.Header;
import ru.geekbrains.nvgostev.junit.pom.MainPage;
import ru.geekbrains.nvgostev.junit.pom.ShoppingCart;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;


public class AddToCartTest extends BaseUITest {
    private static List<Account> accounts;

    @BeforeClass
    private void prepareAccounts() {
        AccountDataGenerator accountDataGenerator = new AccountDataGenerator(3);
        accounts = accountDataGenerator.genAccounts(3);
    }

    @DataProvider(name = "createAccountDataSupplier")
    private static Object[][] createAccountDataSupplier() {
        return accounts.stream()
                .map(account -> new Object[]{account})
                .toArray(Object[][]::new);
    }

    @Test(
            description = "Creating accounts for next test",
            dataProvider = "createAccountDataSupplier"
    )
    public void createAccount(Account acc) {
        new MainPage(getDriver(), getWait())
                .home().getHeader().sigIn()
                .enterEmailAndGoToRegistration(acc.getEmail())
                .fillFormAndSubmit(acc);
    }

    @DataProvider(name = "addItemsAndCheckCartDataSupplier")
    private static Object[][] addItemsAndCheckCartDataSupplier() {
        return new Object[][]{
                {accounts.get(0), "Faded Short Sleeve T-shirts"},
                {accounts.get(1), "Blouse"},
                {accounts.get(2), "Printed Dress"}
        };
    }

    @Test(
            description = "Checks: main page - Sign in - Add to cart - view cart",
            dataProvider = "addItemsAndCheckCartDataSupplier",
            dependsOnMethods = "createAccount"
    )
    public void addItemsAndCheckCart(Account account, String product) {
        ShoppingCart shoppingCart = new MainPage(getDriver(), getWait())
                .home()
                .getHeader().sigIn()
                .signIn(account.getEmail(), account.getPassword())
                .getHeader().openCategory(Header.ShoppingCategory.WOMEN)
                .addProductToCart(product)
                .continueShopping()
                .getHeader().viewCart();
        assertThat(String.format("Item %s is presented in the cart", product), shoppingCart.isItemInCart(product), equalTo(true));
        assertThat(String.format("Item %s qty is %d", product, 1), shoppingCart.getItemQty(product), equalTo(1));
    }
}
