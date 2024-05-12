package tests.entities;

import entities.Financing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import tests.factory.FinancingFactory;

public class FinancingTests {

    @Test
    public void constructorShouldCreateObjectWhenValidData() {
        //Arrange
        double totalAmount = 100000.0;
        double income = 2000.0;
        int months = 80;

        //Act
        Financing financing1 = new Financing(totalAmount, income, months);

        //Assert
        Assertions.assertEquals(totalAmount, financing1.getTotalAmount());
        Assertions.assertEquals(income, financing1.getIncome());
        Assertions.assertEquals(months, financing1.getMonths());
    }

    @Test
    public void constructShouldThrowExceptionWhenInvalidData() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FinancingFactory.createInvalidFinancing();
        });
    }

    @Test
    public void setTotalAmountShouldUpdateValueWhenValidData() {

        //Arrange
        Financing financing1 = new Financing(100000.0, 2000.0, 80);

        //Act
        financing1.setTotalAmount(90000.0);

        //Assert
        Assertions.assertEquals(90000.0, financing1.getTotalAmount());

    }

    @Test
    public void setTotalAmountShouldThrowIllegalArgumentExceptionWhenInvalidData() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Financing financing1 = new Financing(110000.0, 2000.0, 80);
            financing1.setTotalAmount(90000.0);
        });
    }

    @Test
    public void setIncomeShouldUpdateValueWhenValidData() {

        //Arrange
        double incomeExpected = 3000.0;
        Financing financing1 = new Financing(100000.0, 2000.0, 80);

        //Act
        financing1.setIncome(incomeExpected);

        //Assert
        Assertions.assertEquals(incomeExpected, financing1.getIncome());
    }

    @Test
    public void setIncomeShouldThrowIllegalArgumentExceptionWhenInvalidData() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            double valueExpected = 1000.0;
            Financing financing1 = new Financing(100000.0, 2000.0, 80);
            financing1.setIncome(valueExpected);
        });
    }

    @Test
    public void setMonthsShouldUpdateValueWhenValidData() {

        //Arrange
        int monthsExpected = 90;
        Financing financing1 = new Financing(100000.0, 2000.0, 80);

        //Act
        financing1.setMonths(monthsExpected);

        //Assert
        Assertions.assertEquals(monthsExpected, financing1.getMonths());
    }

    @Test
    public void setMonthsShouldThrowIllegalArgumentExceptionWhenInvalidData() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            int monthsExpected = 70;
            Financing financing1 = new Financing(100000.0, 2000.0, 80);
            financing1.setMonths(monthsExpected);
        });
    }

    @Test
    public void entryShouldCalculateCorrectEntryValue() {

        //Arrange
        double entryExpected = 20000;
        Financing financing1 = new Financing(100000.0, 2000.0, 80);

        //Act
        double entryCalculated = financing1.entry();

        //Assert
        Assertions.assertEquals(entryExpected, entryCalculated);

    }

    @Test
    public void quotaShouldCalculateCorrectQuotaValue() {

        //Arrange
        double quotaExpected = 1000;
        Financing financing1 = new Financing(100000.0, 2000.0, 80);

        //Act
        double quotaCalculated = financing1.quota();

        //Assert
        Assertions.assertEquals(quotaExpected, quotaCalculated);

    }
}

