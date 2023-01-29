import com.github.javafaker.Faker;
import org.example.DataGenerator;
import org.example.RegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRegistatedActive() {
        RegistrationDto registration = DataGenerator.getNewUser("active");
        Faker faker = new Faker();
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $$(".heading").find(exactText("Личный кабинет")).shouldBe(exist);
    }

    @Test
    void shouldRegistatedBlocked() {
        RegistrationDto registration = DataGenerator.getNewUser("blocked");
        Faker faker = new Faker();
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe(exist);
    }

    @Test
    void shouldRegistatedInvalidLogin() {
        RegistrationDto registration = DataGenerator.getNewUser("active");
        Faker faker = new Faker();
        $("[data-test-id=login] input").setValue(faker.name().firstName());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(exist);
    }

    @Test
    void shouldRegistatedInvalidPassword() {
        RegistrationDto registration = DataGenerator.getNewUser("active");
        Faker faker = new Faker();
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(faker.internet().password());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(exist);
    }
}
