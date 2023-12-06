//package src.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.step;

@Tag("demoqa")
public class TextBoxTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy="eager";
        //   Configuration.headless=true;
        // Configuration.holdBrowserOpen = true;
        SelenideLogger.addListener("AllureSelenide",new AllureSelenide());
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @Test
    void fillFormTest() {
        step("Open form", (Runnable) () -> {
                    open("/automation-practice-form");
                    executeJavaScript("$('#fixedban').remove()");
                    executeJavaScript("$('footer').remove()");
                });
        step("Fill form", (Runnable) () -> {
                    $("#firstName").setValue("Elmira");
                    $("#lastName").setValue("Shaykhattarova");
                    $("#userEmail").setValue("elmirailgizovna@gmail.com");
                    $("#userNumber").setValue("8906123456");

                    $(By.xpath("//*[contains(text(),'Female')]")).click();
                    $(By.xpath("//*[contains(text(),'Reading')]")).click();
                    $(By.xpath("//*[contains(text(),'Sports')]")).click();

                    $("#dateOfBirthInput").click();
                    $(".react-datepicker__month-select").$(byText("May")).click();
                    $(".react-datepicker__year-select").$(byText("1998")).click();
                    $(".react-datepicker__day.react-datepicker__day--029").click();

                    $("#subjectsInput").setValue("ch");
                    $(byText("Chemistry")).click();

                    $("#uploadPicture").uploadFromClasspath("picture.jpg");

                    $("#currentAddress").setValue("Tatarstan,Kazan");
                    $("#react-select-3-input").setValue("N");
                    $(byText("NCR")).click();
                    $("#react-select-4-input").setValue("Noi");
                    $(byText("Noida")).click();


                    $("#submit").click();
                });

        step("Verify results", (Runnable) () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave((text("Elmira")),
                    text("Shaykhattarova"),
                    text("elmirailgizovna@gmail.com"),
                    text("Female"), text("8906123456"),
                    text("29 April,1998"), text("Chemistry"),
                    text("picture.jpg"), text("Tatarstan,Kazan"),
                    text("NCR Noida"));
        });

    }
}