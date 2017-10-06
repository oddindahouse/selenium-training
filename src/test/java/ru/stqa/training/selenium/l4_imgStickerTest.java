package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

public class l4_imgStickerTest extends TestBase{
    @Test
    public void test1(){
        driver.navigate().to("http://localhost/litecart/");
        List<WebElement> liList = driver.findElements(By.cssSelector("li.product"));
        for (WebElement li : liList) {

            if ( li.findElements(By.cssSelector("div.sticker")).size() != 1){
                throw new NoSuchElementException();
            }

        }

    }
}
