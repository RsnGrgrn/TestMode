package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import ru.netology.entities.RegistrationDto;
import ru.netology.utils.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthTest {


    @Test
    public void shouldSuccessfulLoginIfRegisteredActiveUser() {

        RegistrationDto info = DataGenerator
                .Authorization
                .generateInfo("en", "active");

        open("http://localhost:9999/");
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login] span").click();
        $("h2").shouldHave(text("  Личный кабинет"));
    }

    @Test
    public void shouldUserBlockedAuth() {
        RegistrationDto info = DataGenerator
                .Authorization
                .generateInfo("en", "blocked");

        open("http://localhost:9999/");
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login] span").click();
        $(".notification__title").shouldHave(text("Ошибка"));
        $(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    public void shouldInvalidLoginAuth() {
        RegistrationDto info = DataGenerator
                .Authorization
                .generateInfo("en", "active");

        String invalidLogin = DataGenerator
                .Authorization
                .generateInvalidLogin("en");

        open("http://localhost:9999/");
        $("[data-test-id=login] input").setValue(invalidLogin);
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login] span").click();
        $(".notification__title").shouldHave(text("Ошибка"));
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldInvalidPasswordAuth() {
        RegistrationDto info = DataGenerator
                .Authorization
                .generateInfo("en", "active");

        String invalidPassword = DataGenerator
                .Authorization
                .generateInvalidPassword("en");

        open("http://localhost:9999/");
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(invalidPassword);
        $("[data-test-id=action-login] span").click();
        $(".notification__title").shouldHave(text("Ошибка"));
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}