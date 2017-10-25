package ru.stqa.training.selenium.pajetestapp.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.training.selenium.pajetestapp.pages.CartPage;
import ru.stqa.training.selenium.pajetestapp.pages.ItemPage;
import ru.stqa.training.selenium.pajetestapp.pages.MainPage;


public class Application {

    private WebDriver driver;

    private MainPage mainPage;
    private CartPage cartPage;
    private ItemPage itemPage;

    public Application() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        cartPage = new CartPage(driver);
        itemPage = new ItemPage(driver);
    }

    public void quit() {
        driver.quit();
    }
    public boolean isSummaryTablePresent(){
        cartPage.open();
        return cartPage.isSummaryTablePresent();
    }

    public Double getSummaryPrice(){
        cartPage.open();
        return cartPage.getTotalPrice();
    }

    public Integer getItemInCartCount(){
        mainPage.open();
        return mainPage.getCartQuantity();
    }

    public void buyNewItem() {
        mainPage.open();
        mainPage.openFirstItemFromList();
        itemPage.buyItem();
    }

    public void deleteItemFromCart() {
        cartPage.open();
        cartPage.deleteItem();
    }

}