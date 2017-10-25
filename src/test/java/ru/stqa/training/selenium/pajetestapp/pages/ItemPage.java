package ru.stqa.training.selenium.pajetestapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class ItemPage extends Page {

    public ItemPage(WebDriver driver) {
        super(driver);
    }


    public void buyItem(){
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
        //buying completed
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

    private String randomName(int minLength){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        int length = rand.nextInt(6) + minLength;
        for (int i = 0; i < length; i++) {
            if ( (sb.length()> 0) && (rand.nextInt(10)%2 == 0 ) ){
                sb.append(rand.nextInt(10));
            } else {
                sb.append((char) (rand.nextInt(22) + 97));
            }
        }
        return sb.toString();
    }
}
