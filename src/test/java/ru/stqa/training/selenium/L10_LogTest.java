package ru.stqa.training.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class L10_LogTest {
    public static ThreadLocal<EventFiringWebDriver> tlDriver = new ThreadLocal<>();
    public EventFiringWebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }
        driver = new EventFiringWebDriver(new ChromeDriver());
        tlDriver.set(driver);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        //System.out.println(  ((HasCapabilities)driver).getCapabilities()  );
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }

    @Test
    public void test1(){
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("button[name=login]")).click();
        By linksXPathLocator = By.xpath("//table[contains(@class,'dataTable')]//a[contains(@href,'product') and not(contains(@title,'Edit'))]");

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        int productsCount = driver.findElements(linksXPathLocator).size();

        for (int i = 0; i < productsCount; i++) {
            driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
            driver.findElements(linksXPathLocator).get(i).click();
            wait.until(d->d.findElement(By.cssSelector("h1")));
            driver.manage().logs().get("browser").forEach(l->System.out.println(l));
        }
    }


}
