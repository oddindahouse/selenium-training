package ru.stqa.training.selenium.pajetestapp.tests;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LiteCartBuyTest extends TestBase {

    @Test
    public void BuyAndDeleteTest() {
        Integer oldCount =  app.getItemInCartCount();
        //buying 3 items
        for (int i = 0; i < 3; i++) {
            app.buyNewItem();
        }
        Integer newCount = app.getItemInCartCount();

        assertTrue(newCount == oldCount + 3 );

        //deleting all items
        for (int i = 0; i < newCount; i++) {
            Double oldValue = app.getSummaryPrice();
            app.deleteItemFromCart();

            assertFalse(app.getSummaryPrice().equals(oldValue));
        }

        assertFalse(app.isSummaryTablePresent());




    }

}
