package ru.adel.tests.ui.pages;

import org.openqa.selenium.WebDriver;

public class UsersPage {
    private WebDriver driver;

    public UsersPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isUserPresent(String userName) {
        System.out.println("Adel Test getPageSource:" + driver.getPageSource());
        return driver.getPageSource().contains(userName);
    }
}
