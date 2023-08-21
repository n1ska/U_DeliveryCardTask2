import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTask2Tests {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void testLoginWithExistingUser_Success() {
        var newUser = UserAccountUtils.generateUser(UserAccountUtils.ActiveAccount);
        UserAccountUtils.addUserToPortal(newUser);

        $("[data-test-id='login'] [name='login']").setValue(newUser.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.exactText("  Личный кабинет"));
    }

    @Test
    public void testLoginWithBlockedUser_Fail() {
        var newUser = UserAccountUtils.generateUser(UserAccountUtils.BlockedAccount);
        UserAccountUtils.addUserToPortal(newUser);

        $("[data-test-id='login'] [name='login']").setValue(newUser.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void testLoginWithNotRegisteredUser_Fail() {
        var newUser = UserAccountUtils.generateUser(UserAccountUtils.ActiveAccount);

        $("[data-test-id='login'] [name='login']").setValue(newUser.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']  .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void testCorrectLoginWithInvalidPassword_Fail() {
        var newUser = UserAccountUtils.generateUser(UserAccountUtils.ActiveAccount);
        UserAccountUtils.addUserToPortal(newUser);

        $("[data-test-id='login'] [name='login']").setValue(newUser.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(DataGenerator.generatePassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']  .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void testInvalidLoginWithCorrectdPassword_Fail() {
        var newUser = UserAccountUtils.generateUser(UserAccountUtils.ActiveAccount);
        UserAccountUtils.addUserToPortal(newUser);

        $("[data-test-id='login'] [name='login']").setValue(DataGenerator.generateUserName());
        $("[data-test-id='password'] [name='password']").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']  .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
}
