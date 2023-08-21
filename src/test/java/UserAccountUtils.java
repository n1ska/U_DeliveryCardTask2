import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserAccountUtils {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static final String ActiveAccount = "active";
    public static final String BlockedAccount = "blocked";

    private UserAccountUtils() {

    }

    public static UserAccount generateUser(String status) {
        String userName = DataGenerator.generateUserName();
        String password = DataGenerator.generatePassword();

        return new UserAccount(userName, password, status);
    }

    public static void addUserToPortal(UserAccount account) {
        sendRequest(account);
    }

    private static void sendRequest(Object request) {
        given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}

