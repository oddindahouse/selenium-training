package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.Locator;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }

        driver = new ChromeDriver();
        tlDriver.set(driver);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        System.out.println(  ((HasCapabilities)driver).getCapabilities()  );
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }

    public String randomName(int minLength){
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