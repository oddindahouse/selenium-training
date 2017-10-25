package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
public class L7_CartTest extends TestBase {

    @Test
    public void CartTest(){
        //add 3 items to cart
        for (int i = 0; i < 3 ; i++) {
            driver.get("http://localhost/litecart/");
            driver.findElement(By.cssSelector("div.content li.product a.link")).click();
            String oldQuantity = driver.findElement(By.cssSelector("div#cart a.content span.quantity")).getText();
            //case : REQUIRED FIELDS found
            for (WebElement element : (driver.findElements(By.cssSelector("div.buy_now [required]")))) {
                checkUpRequiredFieldsForElement(element);
            }
            //click add button
            driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
            //wait for quantity change
            wait.until(
                    d ->  (!oldQuantity.equals(d.findElement(By.cssSelector("div#cart span.quantity")).getText()) )
            );
        }

        //going to Cart page
        driver.findElement(By.cssSelector("div#cart a")).click();
        //find all li elements with class "item"
        List<WebElement> liList = driver.findElements(By.cssSelector("div#checkout-cart-wrapper li.item"));

        //find first remove button and click it . repeat until all items gone
        for (int i = 0; i < liList.size(); i++) {
            WebElement table = driver.findElement(By.cssSelector("table.dataTable"));
            driver.findElement(By.cssSelector("div#checkout-cart-wrapper li.item button[name=remove_cart_item]")).click();
            //wait until table refreshing
            wait.until(stalenessOf(table));
            //wait until new table appears. or do not go to if (last element was deleted)
            if (i < liList.size()-1) {
                wait.until(d -> d.findElement(By.cssSelector("table.dataTable")));
            }
        }
    }

    private void checkUpRequiredFieldsForElement(WebElement element){
        if ( "input".equals(element.getTagName()) ) {
            if ( "checkbox".equals(element.getAttribute("type")) ){
                element.click();
            }
            //
            if ( "radio".equals(element.getAttribute("type")) ){
                element.click();
            }
            if ( "text".equals(element.getAttribute("type")) ){
                element.sendKeys(randomName(5));
            }
        }
        if ( "textarea".equals(element.getTagName()) ){
            element.sendKeys(randomName(10));
        }
        if ( "select".equals(element.getTagName()) ){
            new Select(element).selectByIndex(1);
        }
    }
}
