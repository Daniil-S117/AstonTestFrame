import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.example.lesson9.EchoDataUser;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    private final String BASE_URL = "https://postman-echo.com";

    @BeforeAll
    static void setUp() {
        // Общие настройки для всех запросов
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://postman-echo.com")
                .setContentType(ContentType.TEXT) // Для передачи простых строк в теле
                .build().log().all();

        // Общие проверки для всех ответов
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .build().log().all();
    }

    @Test
    void verifyGetMethod() {
        given()
                .queryParam("foo1", "bar1")
                .baseUri("https://postman-echo.com")
                .when()
                .get("/get")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("args.foo1", equalTo("bar1")) // Сравнение полей тела
                .log().all();
    }

    @Test
    void verifyPostRawTextMethod() {
        String text = "Даниил Сушков";
        given()
                .baseUri("https://postman-echo.com")
                .contentType(ContentType.JSON)
                .body(text)
                .when()
                .post("/post")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", equalTo(text))
                .log().all();
    }

    @Test
    void verifyPostFormDataMethod() {
        EchoDataUser user = new EchoDataUser("Даниил", "Сушков");
        given()
                .baseUri("https://postman-echo.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/post")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("json.userName", equalTo("Даниил"))
                .body("json.lastName", equalTo("Сушков"))
                .log().all();
    }

    @Test
    public void verifyPutMethod() {
        String name = "Даниил";
        Map<String, String> person = new HashMap<>();
        person.put("name", name);

        given()
                .baseUri("https://postman-echo.com")
                .contentType(ContentType.JSON)
                .body(person)
                .when()
                .put("/put") // Используем эндпоинт Echo
                .then()
                .log().body()
                .statusCode(HttpStatus.SC_OK)
                .body("json.name", equalTo(name))
                .log().all();
    }

    @Test
    void verifyPatchRequest() {
        String requestBody = "INFORMATION";
        given()
                .body(requestBody)
                .when()
                .patch("https://postman-echo.com" + "/patch")
                .then()
                .statusCode(200)
                .body("data", equalTo(requestBody))
                .log().all();
    }

    @Test
    void testDeleteRequest() {
        given()
                .when()
                .delete(BASE_URL + "/delete")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log().all();
    }
}
