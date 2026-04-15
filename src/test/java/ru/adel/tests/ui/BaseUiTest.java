package ru.adel.tests.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.adel.tests.BaseTest;

import java.time.Duration;

public class BaseUiTest extends BaseTest {
    protected WebDriver driver;
    private final int timeout = 5000;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
