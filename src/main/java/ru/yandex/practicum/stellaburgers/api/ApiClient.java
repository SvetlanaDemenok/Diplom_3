package ru.yandex.practicum.stellaburgers.api;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.yandex.practicum.stellaburgers.api.models.User;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private static String BASE_URI = "https://stellarburgers.nomoreparties.site";
    private static String REGISTER_PATH = "/api/auth/register";
    private static String LOGIN_PATH = "/api/auth/login";
    private static String USER_PATH = "/api/auth/user";

    private String accessToken = "";

    public void setAccessToken(String token){
        accessToken = token.substring(7);
    }

    public static RequestSpecification getReqSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    @Step("Регистрация нового пользователя через API")
    public Response registerUser(User user) {
        return given()
                .spec(getReqSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH);
    }

    @Step("Логин пользователя через API")
    public Response loginUser(String email, String password) {
        return given()
                .spec(getReqSpec())
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Удаление пользователя через API")
    public Response removeUser() {
        return given()
                .spec(getReqSpec())
                .auth()
                .oauth2(accessToken)
                .when()
                .delete(USER_PATH);
    }
}
