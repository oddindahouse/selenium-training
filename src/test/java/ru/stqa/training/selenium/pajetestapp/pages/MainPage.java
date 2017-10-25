package ru.stqa.training.selenium.pajetestapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MainPage extends Page{

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void open(){
        driver.get("http://localhost/litecart/");
    }

    public void openFirstItemFromList(){
        driver.findElement(By.cssSelector("div.content li.product a.link")).click();
    }



    public Integer getCartQuantity(){
        return Integer.parseInt(driver.findElement(By.cssSelector("div#cart span.quantity")).getText());
    }


}
