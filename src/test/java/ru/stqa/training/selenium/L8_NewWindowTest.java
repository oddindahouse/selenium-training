package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import java.util.Set;

public class L8_NewWindowTest extends TestBase {
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
    public void newWindowTest(){
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.xpath("//a[@class='button' and contains(.,'Country')]")).click();
        for ( WebElement aLinkElement : driver.findElements(By.xpath("//a[.//i[contains(@class,'fa-external-link')]]")) ){
            String originalWindow = driver.getWindowHandle();
            Set<String> existingWindows = driver.getWindowHandles();
            aLinkElement.click();
            String newWindow = wait.until(thereIsOtherWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            wait.until(d->d.findElements(By.tagName("html")));
            driver.close();
            driver.switchTo().window(originalWindow);
        }

    }

    public ExpectedCondition<String> thereIsOtherWindowOtherThan(Set<String> oldWindows){
        return new ExpectedCondition<String>() {
            @Override
            public String apply( WebDriver input) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0 ? handles.iterator().next():null;
            }
        };
    }
}
