package ru.yandex.practicum.stellaburgers.ui;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {
    String browserType;

    public BaseTest(String browserType) {
        this.browserType = browserType;
    }

    @Parameterized.Parameters(name = "{0}")
    public static String[] data() {
        return new String[] {"Chrome", "Yandex"};
    }

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (browserType.equals("Yandex")) {
            options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
        }
        ChromeDriver driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);
        open();
        WebDriverRunner.getWebDriver().manage().window().maximize();
        WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        WebDriverRunner.getWebDriver().quit();
    }
}
