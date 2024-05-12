package tests.entities;
import entities.Account;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import tests.factory.AccountFactory;


public class AccountTests {

    @Test
    public void depositShouldIncreaseBalanceAndDiscountFeeWhenPositiveAmount () {

        //Arrange
        double amount = 200.0;
        double expectedValue = 196.0;
        Account acc = AccountFactory.createEmptyAccount();

        //Act
        acc.deposit(amount);

        //Assert
        Assertions.assertEquals(expectedValue, acc.getBalance());
    }

    @Test
    public void depositShouldDoNothingWhenNegativeAmount () {

        //Arrange
        double expectedValue = 100.0;
        double amount = -200.0;
        Account acc = AccountFactory.createAccount(expectedValue);

        //Act
        acc.deposit(amount);

        //Assert
        Assertions.assertEquals(expectedValue, acc.getBalance());

    }

    @Test
    public void fullWithDrawShouldClearBalanceAndReturnFullBalance() {

        double expectedValue = 0.0;
        double initialBalance = 800.0;
        Account acc = AccountFactory.createAccount(initialBalance);

        double result = acc.fullwithdraw();

        Assertions.assertTrue(expectedValue == acc.getBalance());
        Assertions.assertTrue(result == initialBalance);

    }

    @Test
    public void withDrawShouldDecreaseBalanceWhenSufficientBalance () {
        Account acc = AccountFactory.createAccount(800.0);
        acc.withdraw(500);
        Assertions.assertEquals(300.0, acc.getBalance());
    }

    @Test
    public void withDrawShouldThrowExceptionWhenInSufficientBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account acc = AccountFactory.createAccount(800.0);
            acc.withdraw(900.0);
        });
    }
}
