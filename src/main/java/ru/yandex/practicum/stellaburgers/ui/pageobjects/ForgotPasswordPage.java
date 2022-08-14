package ru.yandex.practicum.stellaburgers.ui.pageobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class ForgotPasswordPage extends BasePage {
    @FindBy(linkText = "Войти")
    private SelenideElement loginButton;

    @Step("Клик по кнопке 'Войти'")
    public AuthPage loginButtonClick(){
        loginButton.click();

        return page(AuthPage.class);
    }
}
