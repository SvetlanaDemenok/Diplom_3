package ru.yandex.practicum.stellaburgers.ui;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellaburgers.api.ApiClient;
import ru.yandex.practicum.stellaburgers.api.models.RegisterUserResponse;
import ru.yandex.practicum.stellaburgers.api.models.User;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@DisplayName("Переход в ЛК и на главную страницу, выход из аккаунта")
public class PersonalAccountTest extends BaseTest {
    User user;
    ApiClient apiClient;

    public PersonalAccountTest(String browserType) {
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
    @DisplayName("Переход в личный кабинет")
    public void enterToPersonalAccountTest() {
        var profilePage = open(MainPage.BASE_URL, MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword())
                .authProfileButtonClick();

        assertTrue(profilePage.isButtonProfileTitleDisplayed());
    }

    @Test
    @DisplayName("Переход на главную страницу через клик на конструктор из личного кабинета")
    public void enterToConstructorTest() {
        var mainPage = open(MainPage.BASE_URL, MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword())
                .authProfileButtonClick()
                .constructorButtonClick();

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Переход на главную страницу через клик на логотип из личного кабинета")
    public void enterToLogoTest() {
        var mainPage = open(MainPage.BASE_URL, MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword())
                .authProfileButtonClick()
                .logoButtonClick();

        assertTrue(mainPage.isButtonMakeOrderDisplayed());
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    public void exitFromPersonalAccountTest() {
        var profilePage = open(MainPage.BASE_URL, MainPage.class)
                .loginButtonClick()
                .login(user.getEmail(), user.getPassword())
                .authProfileButtonClick()
                .exitButtonClick();

        Assert.assertEquals("Вход", profilePage.getHeaderText());
    }
}
