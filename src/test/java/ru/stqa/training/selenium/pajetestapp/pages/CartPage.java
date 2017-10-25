package ru.stqa.training.selenium.pajetestapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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


    public Integer getItemCountOnCartPage(){
        if (driver.findElements(By.cssSelector("div#checkout-cart-wrapper li.item")).size() == 0) {
            return 0;
        }
        Integer sum = 0;
        By trsXPathSelector = By.xpath("//table[contains(@class,'dataTable')]/tbody/tr[not(contains(@class,'header')) and not(contains(@class,'footer'))]");
        List<WebElement> trElements = driver.findElements(trsXPathSelector);
        for (int i = 0; i < trElements.size()-3; i++) {
            sum += Integer.parseInt(trElements.get(i).findElement(By.cssSelector("td")).getText());
        }
        return sum;
    }




    public void deleteItem(){
        //creating list of item elements
        List<WebElement> liList = driver.findElements(By.cssSelector("div#checkout-cart-wrapper li.item"));
        //fixing at the same place
        if (liList.size()>1) {
            driver.findElement(By.cssSelector("div#main ul.shortcuts li.shortcut a")).click();
        }

        if (liList.size()>0){
            WebElement table = driver.findElement(By.cssSelector("table.dataTable"));
            driver.findElement(By.cssSelector("div#checkout-cart-wrapper li.item button[name=remove_cart_item]")).click();
            wait.until(stalenessOf(table));
            if (liList.size()>1)
                wait.until(d -> d.findElement(By.cssSelector("table.dataTable")));
        }
    }
}
