package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.*;

public class L5_Country_Zone extends TestBase {

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
    public void countryTest()  {
        //loginF();
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<WebElement> trList = driver.findElements(By.cssSelector("tr.row"));

        //part 1
        Assert.assertTrue( isLexicographicalOrder( trList, By.xpath(".//td[5]")) );

        //part 2
        List<String> nonZeroZonesLinks = new LinkedList<>();
        for (WebElement tr : trList) {
            if (Integer.parseInt(tr.findElement(By.cssSelector("td:nth-child(6)")).getAttribute("textContent")) > 0) {
                nonZeroZonesLinks.add(tr.findElement(By.cssSelector("td:nth-child(5) a")).getAttribute("href"));
            }
        }

        for (String nonZeroZoneLink : nonZeroZonesLinks ) {
            driver.navigate().to(nonZeroZoneLink);
            trList = driver.findElements(By.cssSelector("table#table-zones tr:not(.header)"));
            //delete last item
            trList.remove(trList.size()-1);

            Assert.assertTrue( isLexicographicalOrder(trList, By.xpath(".//td[3]")) );
        }

    }

    @Test
    public void geoTest(){
        //loginF();
        driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> tdLinksList = driver.findElements(By.cssSelector("tr.row td:nth-child(3) a"));

        List<String> linksList = new LinkedList<>();
        for (WebElement td : tdLinksList) {
            linksList.add(td.getAttribute("href"));
        }

        List<WebElement> selectList;
        for (String link : linksList) {
            driver.navigate().to(link);
            selectList = driver.findElements(
                    By.cssSelector("table#table-zones tr:not(.header) td:nth-child(3) select[name*=zone_code]"));
            Assert.assertTrue(isLexicographicalOrder(selectList , By.cssSelector("option[selected]")));
        }

    }



    private boolean isLexicographicalOrder(List<WebElement> list, By locator){
        if (list.size() == 0) {
            throw new NoSuchElementException("Empty table");
        }

        List<String> subList = new LinkedList<>();
        for (WebElement webElement : list) {
            subList.add(webElement.findElement(locator).getAttribute("textContent"));
        }

        List<String> sortedSubList = new LinkedList<>(subList);
        sortedSubList.sort(Comparator.naturalOrder());

        return sortedSubList.equals(subList);
    }
}
