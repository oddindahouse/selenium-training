package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class tmpExampler {




    @Test
    public void main() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-fullscreen");

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

}

