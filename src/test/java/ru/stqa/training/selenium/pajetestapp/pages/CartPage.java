package ru.stqa.training.selenium.pajetestapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void open(){
        driver.navigate().to("http://localhost/litecart/en/checkout");
    }


    public Double getTotalPrice(){
        By valueXPathSelector = By.xpath("//table[contains(@class,'dataTable')]//tr[@class='footer']/*[2]/strong");
        try {
            return Double.parseDouble(driver.findElement(valueXPathSelector).getText().replace("$", ""));
        }catch (NoSuchElementException e){
            //NOP
        }
        return new Double(0);
    }


    public boolean isSummaryTablePresent(){
        return driver.findElements(By.cssSelector("table.dataTable")).size()>0;
    }



    public void deleteItem(){
        List<WebElement> liList = driver.findElements(By.cssSelector("div#checkout-cart-wrapper li.item"));
        //fixing at same place
        if (liList.size()>1) {
            driver.findElement(By.cssSelector("div#main ul.shortcuts li.shortcut")).click();
        }

        if (liList.size()>0){
            WebElement table = driver.findElement(By.cssSelector("table.dataTable"));
            WebElement input = driver.findElement(By.cssSelector("div#checkout-cart-wrapper li.item form[name='cart_form'] input[name='quantity']"));
            input.clear();
            input.sendKeys("1");
            driver.findElement(By.cssSelector("div#checkout-cart-wrapper li.item button[name=remove_cart_item]")).click();
            wait.until(stalenessOf(table));
            if (liList.size()>1)
                wait.until(d -> d.findElement(By.cssSelector("table.dataTable")));
        }
    }
}
