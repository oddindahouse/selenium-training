package ru.stqa.training.selenium;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class L6_UserCreateTest extends TestBase {

    @Test
    public void addUserTest() throws InterruptedException {
        //register
        String email = randomName(6)+"@"+randomName(4)+".com";
        driver.navigate().to("http://localhost/litecart/create_account");
        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys(randomName(6));
        driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys(randomName(7));
        driver.findElement(By.cssSelector("input[name='address1']"))
                .sendKeys(randomName(10)," , ",randomName(10));
        driver.findElement(By.cssSelector("input[name='postcode']"))
                .sendKeys( Integer.toString(new Random().nextInt(90000)+10000));
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys(randomName(10));
        new Select(driver.findElement(By.cssSelector("select[name='country_code']"))).selectByValue("US");
        wait.until(	presenceOfElementLocated(By.cssSelector("select[name='zone_code']")));
        Select state = new Select(driver.findElement(By.cssSelector("select[name='zone_code']")));
        state.selectByIndex(new Random().nextInt(state.getOptions().size()));
        driver.findElement(By.cssSelector("input[name='email']"))
                .sendKeys(email);
        driver.findElement(By.cssSelector("input[name='phone']"))
                .sendKeys("+1 " + ( new Random().nextInt(8_000_000)+1_000_000) );
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys("12345");
        driver.findElement(By.cssSelector("button[name='create_account']")).click();
        //login success

        //logout
        wait.until(presenceOfElementLocated(By.cssSelector("div#box-account")));
        driver.navigate().to("http://localhost/litecart/logout");

        //Log In Account
        wait.until(presenceOfElementLocated(By.cssSelector("form[name='login_form']")));
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("12345");
        driver.findElement(By.cssSelector("button[name='login']")).click();

        //logout
        wait.until(presenceOfElementLocated(By.cssSelector("div#box-account")));
        driver.navigate().to("http://localhost/litecart/logout");
    }

    private String randomName(int minLength){
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
