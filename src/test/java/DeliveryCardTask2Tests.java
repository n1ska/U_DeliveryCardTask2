import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTask2Tests {

    @Test
    public void LoginWithCorrect_Success(){
         var newUser = AccountUtils.GenerateUser("active");
         AccountUtils.AddUserToPortal(newUser);

         open("http://localhost:9999");
         $("[data-test-id='login'] [name='login']").setValue(newUser.getLogin());
         $("[data-test-id='password'] [name='password']").setValue(newUser.getPassword());
         $("[data-test-id='action-login']").click();
         $("h2").shouldHave(Condition.exactText("  Личный кабинет"));
     }

    @Test
    public void LoginWithBlockedUser_Fail() {
        var newUser = AccountUtils.GenerateUser("blocked");
        AccountUtils.AddUserToPortal(newUser);

        open("http://localhost:9999");
        $("[data-test-id='login'] [name='login']").setValue(newUser.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
    }

    @Test
    public void LoginWithNotRegisteredUser_Fail() {
        var newUser = AccountUtils.GenerateUser("active");

        open("http://localhost:9999");
        $("[data-test-id='login'] [name='login']").setValue(newUser.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
    }

    @Test
    public void LoginWithIncorrectPassword_Fail() {
        var newUser = AccountUtils.GenerateUser("active");
        AccountUtils.AddUserToPortal(newUser);

        open("http://localhost:9999");
        $("[data-test-id='login'] [name='login']").setValue(newUser.getLogin());
        $("[data-test-id='password'] [name='password']").setValue("test");
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").shouldBe(Condition.visible);
    }
}
