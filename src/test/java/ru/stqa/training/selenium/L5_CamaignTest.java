package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class L5_CamaignTest {
    public WebDriver driver;
    public WebDriverWait wait;



    @Test
    public void IETest(){
        //init
        driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        System.out.println(  ((HasCapabilities)driver).getCapabilities()  );
        testBody();
    }

    @Test
    public void ChromeTest(){
        //init
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        System.out.println(  ((HasCapabilities)driver).getCapabilities()  );
        testBody();
    }

    @Test
    public void FirefoxTest(){
        //init
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        System.out.println(  ((HasCapabilities)driver).getCapabilities()  );
        testBody();
    }

    private void testBody(){
        //inner class
        class GoodsProperties {
            String name;
            String regularPrice;
            String campaignPrice;
            boolean hasLineThroughRegularPrice;
            boolean isGreyRegularPrice;
            boolean isRedCampaignPrice;
            boolean isBoldCampaignPrice;

            public boolean equals(GoodsProperties prop){

                return (
                        (this == prop)
                        || (
                        (this.name.equals(prop.name)) &&
                        (this.regularPrice.equals(prop.regularPrice) ) &&
                        (this.campaignPrice.equals(prop.campaignPrice) ) &&
                        (this.hasLineThroughRegularPrice == prop.hasLineThroughRegularPrice)&&
                        (this.isGreyRegularPrice == prop.isGreyRegularPrice) &&
                        (this.isRedCampaignPrice == prop.isRedCampaignPrice) &&
                        (this.isBoldCampaignPrice == prop.isBoldCampaignPrice)
                        )
                );
            }

            public boolean isGrey(String rgbaString){
                //parse
                List<String> RGB = Arrays.asList(rgbaString.replaceAll("[^0-9]+", " ").trim().split(" "));
                return (RGB.get(0).equals(RGB.get(1))) && (RGB.get(0).equals(RGB.get(2)));
            }
            public boolean isRed(String rgbaString){
                return ("0".equals(rgbaString.replaceAll("[^0-9]+", " ").trim().split(" ")[1]))
                        && ("0".equals(rgbaString.replaceAll("[^0-9]+", " ").trim().split(" ")[2]));
            }
        }

        driver.navigate().to("http://localhost/litecart/");
        WebElement firstGood = driver.findElement(By.cssSelector("div#box-campaigns ul.products li.product"));

        //main page element parse to inner class GoodsProperties instance
        GoodsProperties firstGoodProperties = new GoodsProperties();
        firstGoodProperties.name =
                firstGood.findElement(By.cssSelector("div.name"))
                        .getAttribute("textContent");
        firstGoodProperties.regularPrice =
                firstGood.findElement(By.cssSelector(".regular-price"))
                        .getAttribute("textContent");
        firstGoodProperties.campaignPrice =
                firstGood.findElement(By.cssSelector(".campaign-price"))
                        .getAttribute("textContent");
        firstGoodProperties.hasLineThroughRegularPrice =
                "s".equals(firstGood.findElement(By.cssSelector(".regular-price")).getTagName());
        firstGoodProperties.isGreyRegularPrice =
                firstGoodProperties.isGrey(firstGood.findElement(By.cssSelector(".regular-price"))
                        .getCssValue("color"));
        firstGoodProperties.isRedCampaignPrice =
                firstGoodProperties.isRed(firstGood.findElement(By.cssSelector(".campaign-price"))
                        .getCssValue("color"));
        firstGoodProperties.isBoldCampaignPrice =
                "strong".equals(firstGood.findElement(By.cssSelector(".campaign-price"))
                        .getTagName());
        // campaign price FONTSIZE is  > than regular
        Assert.assertTrue(
                Double.parseDouble(
                            firstGood.findElement(By.cssSelector(".campaign-price"))
                                    .getCssValue("font-size").replace("px","").trim()
                )
                >
                Double.parseDouble(
                            firstGood.findElement(By.cssSelector(".regular-price"))
                                    .getCssValue("font-size").replace("px","").trim()
                )
        );
        //step to product page
        driver.navigate().to(
                driver.findElement(By.cssSelector("div#box-campaigns ul.products li.product a.link"))
                        .getAttribute("href")
        );
        //staff page element parse to inner class instance
        GoodsProperties secondGoodProperties = new GoodsProperties();
        secondGoodProperties.name =
                driver.findElement(By.cssSelector("div#box-product h1.title")).getAttribute("textContent");
        secondGoodProperties.regularPrice =
                driver.findElement(By.cssSelector("div#box-product div.information .regular-price"))
                        .getAttribute("textContent");
        secondGoodProperties.campaignPrice =
                driver.findElement(By.cssSelector("div#box-product div.information .campaign-price"))
                        .getAttribute("textContent");
        secondGoodProperties.hasLineThroughRegularPrice =
                "s".equals(
                        driver.findElement(By.cssSelector("div#box-product div.information .regular-price"))
                                .getTagName()
                );
        secondGoodProperties.isGreyRegularPrice =
                secondGoodProperties.isGrey(
                        driver.findElement(By.cssSelector("div#box-product div.information .regular-price"))
                        .getCssValue("color")
                );
        secondGoodProperties.isRedCampaignPrice =
                secondGoodProperties.isRed(
                        driver.findElement(By.cssSelector("div#box-product div.information .campaign-price"))
                                .getCssValue("color")
                );
        secondGoodProperties.isBoldCampaignPrice =
                "strong".equals(driver.findElement(By.cssSelector("div#box-product div.information .campaign-price"))
                        .getTagName()
                );
        // campaign price fontsize > regular
        Assert.assertTrue(
                Double.parseDouble(
                        driver.findElement(By.cssSelector("div#box-product div.information .campaign-price"))
                                .getCssValue("font-size").replace("px","").trim()
                )
                        >
                        Double.parseDouble(
                                driver.findElement(By.cssSelector("div#box-product div.information .regular-price"))
                                        .getCssValue("font-size").replace("px","").trim()
                        )
        );
        //equal ?
        Assert.assertTrue(firstGoodProperties.equals(secondGoodProperties));
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
