package ru.yandex.practicum.stellaburgers.ui.pageobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class RegisterPage extends BasePage {
    //Локатор поля ввода имени
    @FindBy(xpath = "//label[text() = 'Имя']/following-sibling::input")
    private SelenideElement nameInput;

    // Локатор поля ввода электронной почты
    @FindBy(xpath = "//label[text() = 'Email']/following-sibling::input")
    private SelenideElement emailInput;

    //Локатор поля ввода пароля
    @FindBy(xpath = "//input[@type='password']")
    private SelenideElement passwordInput;

    //Локатор кнопки регистрации
    @FindBy(xpath = ".//button[contains(text(),'Зарегистрироваться')]")
    private SelenideElement registrationButton;

    //Локатор кнопки "Войти"
    @FindBy(xpath= ".//a[contains(text(),'Войти')]")
    private SelenideElement loginButton;

    //Локатор некорректного пароля
    @FindBy(xpath = ".//p[contains(text(),'Некорректный пароль')]")
    private SelenideElement registrationErrorText;

    @Step("Заполнение поля имени")
    public void setName(String name) {
        nameInput.setValue(name);
    }

    @Step("Заполнение поля почты")
    public void setEmail(String email) {
        emailInput.setValue(email);
    }

    @Step("Заполнение поля пароля")
    public void setPassword(String password) {
        passwordInput.setValue(password);
    }

    @Step("Клик по кнопке регистрации")
    public void registrationButtonClick(){
        registrationButton.click();
    }

    @Step("Регистрация нового пользователя")
    public AuthPage registration(String name, String email, String password){
        setName(name);
        setEmail(email);
        setPassword(password);
        registrationButtonClick();

        return page(AuthPage.class);
    }

    @Step("Взять текст от некорректного пароля")
    public String getRegistrationErrorText() {
        return registrationErrorText.getText();
    }

    //клик по кнопке "Войти"
    @Step("Клик по кнопке 'Войти'")
    public AuthPage loginButtonClick(){
        loginButton.click();

        return page(AuthPage.class);
    }

}
