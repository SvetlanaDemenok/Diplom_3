package ru.yandex.practicum.stellaburgers.ui.pageobjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class ProfilePage extends BasePage {
    //локатор кнопки "Выход"
    @FindBy(xpath = ".//button[contains(text(),'Выход')]")
    private SelenideElement exitButton;

    @FindBy(xpath = ".//a[contains(text(),'Профиль')]")
    private SelenideElement profileTitle;

    //клик по кнопке выхода из аккаунта
    @Step("Клик по кнопке выхода из аккаунта")
    public AuthPage exitButtonClick(){
        exitButton.click();

        return page(AuthPage.class);
    }

    @Step("Взять текст с заглавия личного кабинета")
    public boolean isButtonProfileTitleDisplayed() {
        return profileTitle.isDisplayed();
    }
}
