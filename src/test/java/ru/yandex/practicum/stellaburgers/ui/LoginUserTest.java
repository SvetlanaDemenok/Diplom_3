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
import ru.yandex.practicum.stellaburgers.api.ApiClient;
import ru.yandex.practicum.stellaburgers.api.models.RegisterUserResponse;
import ru.yandex.practicum.stellaburgers.api.models.User;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.ForgotPasswordPage;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.MainPage;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.RegisterPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@DisplayName("Авторизация пользователя")
public class LoginUserTest {
    String browserType;
    User user;
    ApiClient apiClient;

    public LoginUserTest(String browserType) {
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
    @DisplayName("Авторизация через главную страницу")
    public void authThroughMainPageTest() {
        MainPage mainPage = open("https://stellarburgers.nomoreparties.site/", MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Авторизация через личный кабинет")
    public void authThroughPersonalAccountTest() {
        var mainPage = open("https://stellarburgers.nomoreparties.site/", MainPage.class)
                .profileButtonClick()
                .login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Авторизация через кнопку в форме регистрации")
    public void authThroughRegistrationFormTest() {
        var mainPage = open("https://stellarburgers.nomoreparties.site/register", RegisterPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Авторизация через кнопку в форме восстановления пароля")
    public void authThroughForgotPasswordFormTest() {
        var mainPage = open("https://stellarburgers.nomoreparties.site/forgot-password", ForgotPasswordPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }



}
