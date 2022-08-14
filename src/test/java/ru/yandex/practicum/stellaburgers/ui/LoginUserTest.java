package ru.yandex.practicum.stellaburgers.ui;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellaburgers.api.ApiClient;
import ru.yandex.practicum.stellaburgers.api.models.RegisterUserResponse;
import ru.yandex.practicum.stellaburgers.api.models.User;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.ForgotPasswordPage;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.MainPage;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.RegisterPage;

import static com.codeborne.selenide.Selenide.open;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@DisplayName("Авторизация пользователя")
public class LoginUserTest extends BaseTest {
    User user;
    ApiClient apiClient;

    public LoginUserTest(String browserType) {
        super(browserType);
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();

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
    @Override
    public void tearDown() {
        super.tearDown();

        apiClient.removeUser().then().statusCode(SC_ACCEPTED);
    }

    @Test
    @DisplayName("Авторизация через главную страницу")
    public void authThroughMainPageTest() {
        MainPage mainPage = open(MainPage.BASE_URL, MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Авторизация через личный кабинет")
    public void authThroughPersonalAccountTest() {
        var mainPage = open(MainPage.BASE_URL, MainPage.class)
                .profileButtonClick()
                .login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Авторизация через кнопку в форме регистрации")
    public void authThroughRegistrationFormTest() {
        var mainPage = open(RegisterPage.REGISTER_URL, RegisterPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Авторизация через кнопку в форме восстановления пароля")
    public void authThroughForgotPasswordFormTest() {
        var mainPage = open(ForgotPasswordPage.FORGOT_PASS_URL, ForgotPasswordPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword());

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }
}
