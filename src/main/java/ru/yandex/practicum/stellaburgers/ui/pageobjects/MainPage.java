package ru.yandex.practicum.stellaburgers.ui.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class MainPage extends BasePage{

    // Локатор кнопки "Войти в аккаунт"
    @FindBy(xpath = "//button[text() = 'Войти в аккаунт']")
    private SelenideElement loginButton;

    // Локатор кнопки "Оформить заказ"
    @FindBy(xpath = "//button[text() = 'Оформить заказ']")
    private SelenideElement makeOrderButton;


    // Локатор "Булки"
    @FindBy(xpath = ".//span[contains(text(),'Булки')]")
    private SelenideElement breadButton;

    // Локатор "Соусы"
    @FindBy(xpath = ".//span[contains(text(),'Соусы')]")
    private SelenideElement sauceButton;

    // Локатор "Начинки"
    @FindBy(xpath = ".//span[contains(text(),'Начинки')]")
    private SelenideElement fillingButton;

    // Локатор "Флюоресцентная булка R2-D3"
    @FindBy(xpath = ".//p[contains(text(),'Флюоресцентная булка R2-D3')]")
    private SelenideElement fluorescentBun;

    // Локатор "Соус Spicy-X"
    @FindBy(xpath = ".//p[contains(text(),'Соус Spicy-X')]")
    private SelenideElement sauceSpicyTitle;

    // Локатор "Филе Люминесцентного тетраодонтимформа"
    @FindBy(xpath = ".//p[contains(text(),'Филе Люминесцентного тетраодонтимформа)]")
    private SelenideElement filletLuminescent;

    //клик на кнопку "Войти в аккаунт"
    @Step("Клик на кнопку войти в аккаунт")
    public AuthPage loginButtonClick(){
        loginButton.click();

        return page(AuthPage.class);
    }

    public boolean isButtonMakeOrderDisplayed() {
        return makeOrderButton.isDisplayed();
    }

    public SelenideElement getBreadButton() {
        return breadButton;
    }

    public SelenideElement getSauceButton() {
        return sauceButton;
    }

    public SelenideElement getFillingButton() {
        return fillingButton;
    }

    @Step("Переход к разделу с начинками")
    public void clickFillingChapter() {
        fillingButton.click();
    }

    @Step("Переход к разделу с соусами")
    public void clickSauceChapter() {
        sauceButton.click();
    }

    @Step("Переход к разделу с булками")
    public void clickBreadChapter() {
        breadButton.click();
    }
}
