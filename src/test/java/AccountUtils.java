import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class AccountUtils {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static Locale defaultLocale = new Locale("ru");

    private AccountUtils() {

    }
    public static UserAccount GenerateUser(String status) {
        Faker faker = new Faker(defaultLocale);
        String login =  faker.name().username();
        String password = faker.internet().password();

        return new UserAccount(login, password, status);
    }

    public static void AddUserToPortal(UserAccount account){
        sendRequest(account);
    }

    private static void sendRequest(Object request){
        given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}

