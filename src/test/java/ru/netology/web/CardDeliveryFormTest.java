package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryFormTest {
   private String generateDate(int addDays, String pattern){
       return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
   }

    @Test
    void shouldTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        String planningDate = generateDate(4,"dd.MM.yyyy");
        $(".calendar-input__custom-control .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.DELETE);
        $(".calendar-input__custom-control .input__control").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Василий-Васильевич Иванов");
        $("[data-test-id='phone'] input").setValue("+72500000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}

