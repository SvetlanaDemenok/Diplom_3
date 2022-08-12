package ru.yandex.practicum.stellaburgers.ui;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.practicum.stellaburgers.api.ApiClient;
import ru.yandex.practicum.stellaburgers.api.models.RegisterUserResponse;
import ru.yandex.practicum.stellaburgers.api.models.User;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.MainPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@DisplayName("Переход в ЛК и на главную страницу, выход из аккаунта")
public class PersonalAccountTest {
    String browserType;
    User user;
    ApiClient apiClient;

    public PersonalAccountTest(String browserType) {
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

        user = User.getRandomUser();
        apiClient = new ApiClient();
        String accessToken = apiClient.registerUser(user)
                .then()
                .statusCode(SC_OK)
                .extract()
                .as(RegisterUserResponse.class)
                .getAccessToken();
        apiClient.setAccessToken(accessToken);
    }

    @After
    public void tearDown() {
        apiClient.removeUser().then().statusCode(SC_ACCEPTED);
        WebDriverRunner.getWebDriver().quit();
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    public void enterToPersonalAccountTest() {
        var profilePage = open("https://stellarburgers.nomoreparties.site/", MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword())
                .authProfileButtonClick();

        assertTrue(profilePage.isButtonProfileTitleDisplayed());
    }

    @Test
    @DisplayName("Переход на главную страницу через клик на конструктор из личного кабинета")
    public void enterToConstructorTest() {
        var mainPage = open("https://stellarburgers.nomoreparties.site/", MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword())
                .authProfileButtonClick()
                .constructorButtonClick();

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Переход на главную страницу через клик на логотип из личного кабинета")
    public void enterToLogoTest() {
        var mainPage = open("https://stellarburgers.nomoreparties.site/", MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword())
                .authProfileButtonClick()
                .logoButtonClick();

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    public void exitFromPersonalAccountTest() {
        var profilePage = open("https://stellarburgers.nomoreparties.site/", MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword())
                .authProfileButtonClick()
                .exitButtonClick();

        Assert.assertEquals("Вход", profilePage.getHeaderText());
    }
}
