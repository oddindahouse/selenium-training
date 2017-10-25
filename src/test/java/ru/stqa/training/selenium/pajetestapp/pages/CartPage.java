package ru.stqa.training.selenium.pajetestapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

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
        return Double.valueOf(0);
    }


    public boolean isSummaryTablePresent(){
        return driver.findElements(By.cssSelector("table.dataTable")).size()>0?true:false;
    }



    public void deleteItem(){
        List<WebElement> liList = driver.findElements(By.cssSelector("div#checkout-cart-wrapper li.item"));
        if (liList.size()>0){
            WebElement table = driver.findElement(By.cssSelector("table.dataTable"));
            driver.findElement(By.cssSelector("div#checkout-cart-wrapper li.item button[name=remove_cart_item]")).click();
            wait.until(stalenessOf(table));
            if (liList.size()>1)
                wait.until(d -> d.findElement(By.cssSelector("table.dataTable")));
        }
    }
}
