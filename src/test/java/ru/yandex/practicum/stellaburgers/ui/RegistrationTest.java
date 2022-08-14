package ru.yandex.practicum.stellaburgers.ui;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellaburgers.api.ApiClient;
import ru.yandex.practicum.stellaburgers.api.models.AuthUserResponse;
import ru.yandex.practicum.stellaburgers.api.models.User;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.RegisterPage;

import static com.codeborne.selenide.Selenide.open;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@DisplayName("Регистрация нового пользователя")
public class RegistrationTest extends BaseTest {
    User user;
    ApiClient apiClient;
    RegisterPage registerPage;

    public RegistrationTest(String browserType) {
        super(browserType);
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();

        registerPage = open(RegisterPage.REGISTER_URL, RegisterPage.class);

        user = User.getRandomUser();
        apiClient = new ApiClient();
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();

        if (user.getPassword().length() >= 6) {
            var accessToken = apiClient.loginUser(user.getEmail(), user.getPassword())
                    .then()
                    .statusCode(SC_OK)
                    .extract()
                    .as(AuthUserResponse.class).getAccessToken();
            apiClient.setAccessToken(accessToken);
            apiClient.removeUser()
                    .then()
                    .statusCode(SC_ACCEPTED);
        }
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void successRegistrationTest() {
        var authPage = registerPage.registration(user.getName(), user.getEmail(), user.getPassword());

        Assert.assertEquals("Вход", authPage.getHeaderText());
    }

    @Test
    @DisplayName("Регистрация с неправильной длинной пароля")
    public void registrationWrongLengthPasswordTest() {
        user.setPassword(RandomStringUtils.randomAlphabetic(5));
        var authPage = registerPage.registration(user.getName(), user.getEmail(), user.getPassword());

        assertEquals("Некорректный пароль", registerPage.getRegistrationErrorText());
    }
}
