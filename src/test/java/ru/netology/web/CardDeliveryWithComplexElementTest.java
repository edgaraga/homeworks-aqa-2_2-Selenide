package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryWithComplexElementTest {
    LocalDate date = LocalDate.now();
    LocalDate newDate = date.plusDays(7);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void shouldSendFormUsingDropDownElements() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Ек");
        $$(".menu-item").find(exactText("Екатеринбург")).click();
        $(".icon-button").click();
        if ((newDate.getMonthValue() != (LocalDate.now()).getMonthValue())) {
            $("[data-step='1']").click();
        }
        $(byText(String.valueOf(newDate.getDayOfMonth()))).click();
        $("[data-test-id='name'] .input__control").setValue("Смирнова Мария-Антануетта");
        $("[data-test-id='phone'] .input__control").setValue("+39540018745");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + formatter.format(newDate)));
    }
}
