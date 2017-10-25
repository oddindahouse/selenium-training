package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
public class tmpExampler {

    private WebDriverWait wait;


    @Test
    public void main() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-fullscreen");
        //javaScript using.
        //List<WebElement> liList = (List<WebElement>) ((JavascriptExecutor)driver)
        //      .executeScript("return document.querySelectorAll('div#checkout-cart-wrapper li.item')");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehaviour", "dismiss");
        caps.setCapability(ChromeOptions.CAPABILITY,options);
        WebDriver driver = new ChromeDriver(caps);
        System.out.println(((HasCapabilities) driver).getCapabilities());

        driver.quit();
    }

    @Test
    public void firefoxTestOldScheme1(){
        DesiredCapabilities caps = new DesiredCapabilities();
        FirefoxOptions options = new FirefoxOptions().setBinary(new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox 45 esr\\firefox.exe")));
        caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS,options);
        caps.setCapability(FirefoxDriver.MARIONETTE,false);


        WebDriver driver = new FirefoxDriver(caps);
        driver.quit();
    }

    @Test
    public void firefoxTestOldScheme2(){
        FirefoxOptions options = new FirefoxOptions();
        options.setLegacy(true);
        options.setBinary(new FirefoxBinary(
                                new File("C:\\Program Files\\Mozilla Firefox 45 esr\\firefox.exe")
                            )
        );



        WebDriver driver = new FirefoxDriver(options);
        System.out.println( ((HasCapabilities)driver).getCapabilities() );
        driver.quit();
    }

    @Test
    public void testIE(){

        WebDriver driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver", Keys.ENTER);
        wait.until(titleIs("webdriver - Поиск в Google"));
        driver.quit();
    }

}

