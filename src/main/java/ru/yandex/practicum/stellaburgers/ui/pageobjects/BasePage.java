package ru.yandex.practicum.stellaburgers.ui.pageobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class BasePage {
    public static String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static String REGISTER_URL = BASE_URL + "/register";
    public static String FORGOT_PASS_URL = BASE_URL + "/forgot-password";

    // Локатор кнопки "Личный кабинет"
    @FindBy(xpath = ".//p[contains(text(),'Личный Кабинет')]")
    private SelenideElement profileButton;

    // Локатор кнопки "Конструктор"
    @FindBy(xpath = ".//p[contains(text(),'Конструктор')]")
    private SelenideElement constructorButton;

    // Локатор логотипа
    @FindBy(className = "AppHeader_header__logo__2D0X2")
    private SelenideElement logoButton;

    // клик на кнопку "Личный кабинет" и переход на страницу авторизации
    @Step("Клик на кнопку личный кабинет")
    public AuthPage profileButtonClick() {
        profileButton.click();

        return page(AuthPage.class);
    }

    // клик на кнопку "Личный кабинет" и переход в личный кабинет
    @Step("Клик на кнопку личный кабинет")
    public ProfilePage authProfileButtonClick() {
        profileButton.click();

        return page(ProfilePage.class);
    }

    //клик на кнопку "Конструктор"
    @Step("Клик на кнопку Конструктор")
    public MainPage constructorButtonClick() {
        constructorButton.click();

        return page(MainPage.class);
    }

    //клик на кнопку логотипа
    @Step("Клик на кнопку логотипа")
    public MainPage logoButtonClick() {
        logoButton.click();

        return page(MainPage.class);
    }
}
