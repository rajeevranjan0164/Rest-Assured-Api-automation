package examples;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class Test1_Basic {

    /**
     * Here I can check status code
     */

    /**
     * Here written code from of BDD style
     * given() is a pre-condition
     * when() is the define action need to perform
     * then() is a verification perform
     */
    @Test
    public void testStatusCode() {
        given()
                .get("https://jsonplaceholder.typicode.com/posts/3")
                .then()
                .statusCode(200);
    }

    /**
     * log.all it will verify the code and print the complete log in console
     */
    @Test
    public void testLogging() {
        given()
                .get("http://services.groupkt.com/country/get/iso2code/in")
                .then()
                .statusCode(200)
                .log()
                .all();
    }

    /**
     *  Verifying single content using org.hamcrest.Matchers.equalTo; library method
     */
    @Test
    public void testEqualToMethod() {
        given()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .body("[0].title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
    }

    /**
     * Verifying multiple content using org.hamcrest.Matchers.hasItems; library
     * here body title is giving all title response i need to verify some particular value so used  hasItems method
     */
    @Test
    public void testHasItemMethod() {
        given()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .body("title", hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
                , "qui est esse"
                , "ea molestias quasi exercitationem repellat qui ipsa sit aut",
                        "ea molestias quasi exercitationem repellat qui ipsa sit aut"));
    }

    /**
     * Here I have set Parameter and Header
     */
    @Test
    public void testParameterAndHeader() {
        given()
                .param("key1","value1") // I have used dummy parameter
                .header("HeadB", "valueB") // Here I have set dummy header
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(200)
                .log()
                .all();
    }


}
