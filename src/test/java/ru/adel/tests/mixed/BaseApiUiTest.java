package ru.adel.tests.mixed;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.adel.tests.BaseTest;
import ru.adel.tests.api.accounts.UserApiClient;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class BaseApiUiTest extends BaseTest {
    protected WebDriver driver;
    protected UserApiClient apiClient;
    private static int timeout = 5000;
    protected static WireMockServer wireMockServer;

    @BeforeAll
    static void startMock() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        configureFor("localhost", 8080);
    }

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", timeout)
                .setParam("http.socket.timeout", timeout));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @AfterAll
    static void stopMock() {
        wireMockServer.stop();
    }
}

