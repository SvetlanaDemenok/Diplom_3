package ru.yandex.practicum.stellaburgers.ui;

import com.codeborne.selenide.Condition;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellaburgers.ui.pageobjects.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@DisplayName("Переход по разделам конструктора")
public class BurgerConstructorTest extends BaseTest {
    MainPage mainPage;

    public BurgerConstructorTest(String browserType) {
        super(browserType);
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();
        mainPage = open(MainPage.BASE_URL, MainPage.class);
    }

    @Test
    @DisplayName("Переходим к разделу начинок")
    public void goToTheFillingChapterTest() {
        mainPage.clickFillingChapter();

        mainPage.getFillingButton().parent().shouldBe(Condition.cssClass("tab_tab_type_current__2BEPc"));
    }

    @Test
    @DisplayName("Переходим к разделу соусов")
    public void goToTheSauceChapterTest() {
        mainPage.clickSauceChapter();

        mainPage.getSauceButton().parent().shouldBe(Condition.cssClass("tab_tab_type_current__2BEPc"));
    }

    @Test
    @DisplayName("Переходим к разделу булок")
    public void goToTheBreadChapterTest() {
        mainPage.clickFillingChapter();
        mainPage.clickBreadChapter();

        mainPage.getBreadButton().parent().shouldBe(Condition.cssClass("tab_tab_type_current__2BEPc"));
    }
}
