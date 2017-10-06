package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import java.util.NoSuchElementException;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


public class LitecartTest extends TestBase {


    private void loginF(){
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("button[name=login]")).click();

    }

    @Test
    public void menuTest(){
        loginF();
        String selector;
        String subSelector;
        int listSize = driver.findElements(By.cssSelector("ul#box-apps-menu li#app-")).size();
        //check if ul has no elements throw Exception
        if (listSize == 0) throw new NoSuchElementException();

        for (int currentMenuIndex = 0; currentMenuIndex < listSize; currentMenuIndex++) {
            //selector looks like ul#box-apps-menu > li:nth-child(n) where n is a number 1.. (size of <li> tags list + 1)
            selector = "ul#box-apps-menu > li:nth-child("+ (currentMenuIndex + 1 ) + ")";
            driver.findElement(By.cssSelector(selector) ).click();

            wait.until(presenceOfElementLocated(By.cssSelector("h1")));
            //check for subMenus
            //if true then click every child of ul.docs in parent context
            if (driver.findElements(By.cssSelector(selector + " ul.docs")).size()>0){
                System.out.println(driver.findElement(By.tagName("h1")).getText()+ " --->");
                for (int currentSubMenuIndex = 0; currentSubMenuIndex  < driver.findElements(By.cssSelector(selector + " ul.docs li")).size() ; currentSubMenuIndex++) {
                    //ul#box-apps-menu > li:nth-child(currentMenuIndex) ul.docs li:nth-child(currentSubMenuIndex)
                    subSelector = selector + " ul.docs li:nth-child(" + (currentSubMenuIndex + 1) +")";
                    driver.findElement(By.cssSelector( subSelector ) ).click();
                    wait.until(presenceOfElementLocated(By.cssSelector("h1")));
                }

            }
        }






    }


}
