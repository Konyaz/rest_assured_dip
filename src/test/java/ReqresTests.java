import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresTests {

    @Test
    void registerSuccessful() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"pistol\"}")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void loginSuccessful() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"pistol\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void loginUnSuccessful() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void listResource() {
        given()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("page", equalTo(1),
                        "support.text", is("To keep ReqRes free," +
                                " contributions towards server costs are appreciated!"));
    }

    @Test
    void updateUserWithPatch() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\"," +
                        "\"job\": \"zion resident\"}")
                .when()
                .patch("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("job", containsString("zion resident"));
    }

    @Test
    void updateUser() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\"," +
                        "\"job\": \"zion resident\"}")
                .when()
                .put("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus")
                        , "updatedAt", containsString("2021"));
    }

    @Test
    void deleteUser() {
        given()
                .contentType(JSON)
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }
}