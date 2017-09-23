package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by dima2 on 23.09.2017.
 */

public class firstTest {
    private WebDriver driver;

    @Before
    public void start(){
        driver = new ChromeDriver();
    }

    @Test
    public void firstTest(){
        driver.get("http://ya.ru/");
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }

}
