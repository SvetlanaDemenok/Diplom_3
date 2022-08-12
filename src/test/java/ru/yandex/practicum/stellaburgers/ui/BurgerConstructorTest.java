package ru.yandex.practicum.stellaburgers.ui;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@DisplayName("Переход по разделам конструктора")
public class BurgerConstructorTest {
    String browserType;
    MainPage mainPage;

    public BurgerConstructorTest(String browserType) {
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
        mainPage = open("https://stellarburgers.nomoreparties.site/", MainPage.class);
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @After
    public void tearDown() {
        WebDriverRunner.getWebDriver().quit();
    }

    @Test
    @DisplayName("Переходим к разделу начинок")
    public void goToTheFillingChapterTest() {
        assertTrue(mainPage.transitionFillingChapter());
    }

    @Test
    @DisplayName("Переходим к разделу соусов")
    public void goToTheSauceChapterTest() {
        assertTrue(mainPage.transitionSauceChapter());
    }

    @Test
    @DisplayName("Переходим к разделу булок")
    public void goToTheBreadChapterTest() {
        assertTrue(mainPage.transitionBreadChapter());
    }
}
