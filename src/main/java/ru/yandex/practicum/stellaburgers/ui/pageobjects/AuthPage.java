package ru.yandex.practicum.stellaburgers.ui.pageobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class AuthPage extends BasePage {

    // локатор заголовка страницы
    @FindBy(xpath = "//h2[text() = 'Вход']")
    private SelenideElement header;

    // локатор поля ввода электронной почты
    @FindBy(xpath = "//input[@name='name']")
    private SelenideElement authEmailInput;

    // локатор поля ввода пароля
    @FindBy(xpath = "//input[@type='password']")
    private SelenideElement authPasswordInput;

    // локатор кнопки регистрации
    @FindBy(xpath = ".//button[text() = 'Войти']")
    private SelenideElement loginButton;

    //заполнение поля email
    @Step("Заполнение поля email")
    public void setEmail(String email){
        authEmailInput.setValue(email);
    }

    //заполнение поля password
    @Step("Заполнение поля password")
    public void setPassword(String password){
        authPasswordInput.setValue(password);
    }

    @Step("Клик по кнопке 'Войти'")
    public void buttonLoginClick() {
        loginButton.click();
    }

    @Step("Получение текста")
    public String getHeaderText() {
        return header.getText();
    }

    @Step("Авторизация")
    public MainPage login(String email, String password) {
        setEmail(email);
        setPassword(password);
        buttonLoginClick();

        return page(MainPage.class);
    }
}
