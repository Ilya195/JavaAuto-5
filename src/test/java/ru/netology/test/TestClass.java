package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import ru.netology.data.DataGenerator;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

public class TestClass {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        //$("[type=button] .button__text").click();
        $(".notification__title").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(secondMeetingDate);
        $("[type=button] .button__text").click();
        $("[data-test-id=replan-notification] .notification__title").should(Condition.exactText("Необходимо подтверждение"));
        $("[data-test-id=replan-notification] span.button__text").click();
        $("[data-test-id=success-notification] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}