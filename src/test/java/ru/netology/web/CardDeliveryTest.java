package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    LocalDate date = LocalDate.now();
    LocalDate newDate = date.plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void shouldTestWithCorrectData() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Смирнова Мария-Антануетта");
        $("[data-test-id=phone] input").setValue("+39540018745");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + formatter.format(newDate)));
    }

    @Test
    void shouldTestWithAInvalidCity() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Королев");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Смирнова Мария-Антануетта");
        $("[data-test-id=phone] input").setValue("+39540018745");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=city] .input__sub").shouldBe(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestWithoutDate() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Смирнова Мария-Антануетта");
        $("[data-test-id=phone] input").setValue("+39540018745");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=date] .input__sub").shouldBe(Condition.text("Неверно введена дата"));
    }

    @Test
    void shouldTestWithAInvalidNameAndSurname() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Anton Volkov");
        $("[data-test-id=phone] input").setValue("+39540018745");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=name] .input__sub").shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWithoutNameAndSurname() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+39540018745");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=name] .input__sub").shouldBe(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWithoutPhoneNumber() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Смирнова Мария-Антануетта");
        $("[data-test-id=phone] input").setValue("");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=phone] .input__sub").shouldBe(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWithInvalidPhoneNumberTenDigits() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Смирнова Мария-Антануетта");
        $("[data-test-id=phone] input").setValue("+3954001874");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=phone] .input__sub").shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestWithInvalidPhoneNumberTwelveDigits() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Смирнова Мария-Антануетта");
        $("[data-test-id=phone] input").setValue("+395400187454");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=phone] .input__sub").shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestWithoutCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Смирнова Мария-Антануетта");
        $("[data-test-id=phone] input").setValue("+39540018745");
        $("[class=button__text]").click();
        $("[class=checkbox__text]").shouldBe(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldTestWithEmptyForm() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("");
        $("[class=button__text]").click();
        $("[data-test-id=city] .input__sub").shouldBe(Condition.text("Поле обязательно для заполнения"));
    }
}
