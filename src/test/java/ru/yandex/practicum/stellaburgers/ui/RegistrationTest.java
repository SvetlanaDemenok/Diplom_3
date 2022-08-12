package ru.yandex.practicum.stellaburgers.ui;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
public class RegistrationTest {
    String browserType;
    User user;
    ApiClient apiClient;
    RegisterPage registerPage;

    public RegistrationTest(String browserType) {
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
        registerPage = open("https://stellarburgers.nomoreparties.site/register", RegisterPage.class);
        WebDriverRunner.getWebDriver().manage().window().maximize();

        user = User.getRandomUser();
        apiClient = new ApiClient();
    }

    @After
    public void tearDown() {
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

        WebDriverRunner.getWebDriver().quit();
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
