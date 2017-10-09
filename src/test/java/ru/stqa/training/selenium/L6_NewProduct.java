package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;


public class L6_NewProduct extends TestBase {

    @Before
    public void login(){
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("button[name=login]")).click();
    }

    @After
    public void logout() {
        driver.navigate().to("http://localhost/litecart/admin/logout.php");
    }


    @Test
    public void addProductTest() {

        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        //first tab
        driver.findElement(By.xpath("//td[@id='content']//a[@class='button' and contains(.,'Product')]")).click();
        driver.findElement(By.xpath("//div[@id='tab-general']//label[contains(.,'Enabled')]//input[@name='status']")).click();
        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys("Police Duck");
        driver.findElement(By.cssSelector("input[name=code]")).sendKeys("1234");
        if (driver.findElement(By.cssSelector("input[data-name*='Root']")).isSelected()){
            driver.findElement(By.cssSelector("input[data-name*='Root']")).click();
        }
        driver.findElement(By.cssSelector("input[data-name*='Rubber Ducks']")).click();
        new Select(driver.findElement(By.cssSelector("select[name=default_category_id]"))).selectByValue("1");
        driver.findElement(By.cssSelector("input[value='1-3']")).click();
        driver.findElement(By.cssSelector("input[name=quantity]")).clear();
        driver.findElement(By.cssSelector("input[name=quantity]")).sendKeys("30");
        driver.findElement(By.cssSelector("input[type=file][name='new_images[]']"))
                .sendKeys(new File("img.jpg").getAbsolutePath());
        //only Chrome passed there . ff fails -> other yyyy.mm.dd
        driver.findElement(By.cssSelector("input[name=date_valid_from]")).sendKeys("08","10","2017");
        driver.findElement(By.cssSelector("input[name=date_valid_to]")).sendKeys("08","10","2018");

        //info tab
        driver.findElement(By.xpath("//div[@class='tabs']//ul[@class='index']//a[contains(.,'Information')]")).click();
        new Actions(driver).pause(1000);

        new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]"))).selectByValue("1");
        driver.findElement(By.cssSelector("input[name=keywords]"))
                .sendKeys(randomName(6)+", " + randomName(10));
        driver.findElement(By.cssSelector("input[name='short_description[en]']"))
                .sendKeys("Great Police Duck. Much more better than other police ducks");
        driver.findElement(By.cssSelector("div.trumbowyg-editor"))
                .sendKeys("Once upon a time there was a little duck had lived at the lake. " +
                        "Then it decided to go to study to the police academy. And here it is. New brand Police Duck. ");
        driver.findElement(By.cssSelector("input[name='head_title[en]']"))
                .sendKeys("Great Police Duck");
        driver.findElement(By.cssSelector("input[name='meta_description[en]']"))
                .sendKeys("police duck uniform");
        //prices tab
        driver.findElement(By.xpath("//div[@class='tabs']//ul[@class='index']//a[contains(.,'Prices')]")).click();
        new Actions(driver).pause(1000);
        driver.findElement(By.cssSelector("input[name=purchase_price]")).clear();
        driver.findElement(By.cssSelector("input[name=purchase_price]")).sendKeys("25");
        new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]"))).selectByValue("USD");
        driver.findElement(By.cssSelector("input[name='gross_prices[USD]']")).clear();
        driver.findElement(By.cssSelector("input[name='gross_prices[USD]']")).sendKeys("26");
        //save
        driver.findElement(By.cssSelector("button[name=save]")).click();
        //check
        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        driver.findElement(By.xpath("//table[@class='dataTable']//tr[@class='row']//a[contains(.,'Rubber Ducks')]")).click();
        Assert.assertTrue(
                driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']/td[3]//a[contains(.,'Police Duck')]")).size() == 1
        );
    }
}
