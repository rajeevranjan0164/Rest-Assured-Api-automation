package examples;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

public class Test10_VerifyResponse {

    /**
     * Status Code verification
     */
    @Test
    public void testStatusInResponse() {

        given()
                .get("https://jsonplaceholder.typicode.com/photos")
                .then()
                .assertThat()
                .statusCode(200)
                .log()
                .status();

        given()
                .get("https://jsonplaceholder.typicode.com/photos")
                .then()
                .assertThat()
                .statusLine("HTTP/1.1 200 OK");

        given()
                .get("https://jsonplaceholder.typicode.com/photos")
                .then()
                .assertThat()
                .statusLine(containsString("OK"));
    }

    /**
     * Header and headers verification
     */
    @Test
    public void testHeaderInResponse() {
        given()
                .get("https://jsonplaceholder.typicode.com/photos")
                .then()
                .assertThat()
                .header("X-Powered-By", "Express");

        given()
                .get("https://jsonplaceholder.typicode.com/photos")
                .then()
                .assertThat()
                .headers("Vary", "Origin, Accept-Encoding", "Content-Type", containsString("application/json"));
    }


    /**
     * Here I have taken on Excepted body response and other one verifying Actual response same body
     */
    @Test
    public void testBodyInResponse() {

        //expexted response
        String responseString = get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/02").asString();


        given()
                .get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/02") // Actual Response
                .then()
                .assertThat()
                .body(equalTo(responseString));
    }

    /**
     * Cookies is every time changing So TODO
     */
    @Test
    public void testCookieInResponse() {
        given()
                .get("https://jsonplaceholder.typicode.com/comments")
                .then()
                .log()
                .all()
                .assertThat()
                .cookie("__cfduid", "dd43a5760268a0aeee6df95990428f40a1558849945");
    }

    /**
     * Verifying using lamda expression
     */
    @Test
    public void testBodyParameterInResponse() {
        given()
                .get("https://jsonplaceholder.typicode.com/photos/1")
                .then()
                .body("thumbnailUrl", response -> equalTo("https://via.placeholder.com/150/92c952")); // java 8 Lamda expression

        //without lamda experssion
        //simple way
        given()
                .get("https://jsonplaceholder.typicode.com/photos/1")
                .then()
                .body("thumbnailUrl", endsWith("92c952"));
    }
}
