package examples;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Test5_ReadResponseInDiffWay {

    /**
     * get all response as string
     * here i can apply regex and i can fetch in details in JSON response
     */
    @Test
    public void testGetResponseAsString() {
        String response = get("http://services.groupkt.com/country/search?text=lands").asString();
        System.out.println("My Response  \n\n\n" + response);
    }

    /**
     * get all the string as InputStream
     */
    @Test
    public void testGetResponseAsInputStrem() {
        InputStream inputStream = get("http://services.groupkt.com/country/get/iso2code/IN").asInputStream();
        System.out.println("Stream length : " + inputStream.toString().length());
    }

    /**
     * get all response using byteArray
     */
    @Test
    public void testGetResponsAsByteArray() {
        byte[] byteArray = get("http://services.groupkt.com/country/get/iso2code/IN").asByteArray();
        System.out.println("Stream length : " + byteArray.toString().length());
    }

    /**
     * extract details using path
     */
    @Test
    public void testExtractDetailsUingPath() {
        String url =

                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/photos/1")
                        .then()
                        .contentType(ContentType.JSON)
                        .statusCode(200)
                        .body("albumId", equalTo(1))
                        .extract()
                        .path("url");

        System.out.println(url);

        when().get("https://via.placeholder.com/600/92c952").then().statusCode(200);
    }

    /**
     * Extract details path using one line
     */
    @Test
    public void textExtractPathInOneLine() {
        //Type 1
        String href1 = get("https://jsonplaceholder.typicode.com/photos/1").path("thumbnailUrl");
        System.out.println("Fetch URL : " + href1);
        when().get(href1).then().statusCode(200);

        //Type 2
        String href2 = get("https://jsonplaceholder.typicode.com/photos/1").andReturn().jsonPath().getString("thumbnailUrl");
        System.out.println("Fetch Url 2 : " + href2);
        when().get(href2).then().statusCode(200);
    }

    /**
     * Extract details as Response for Farther use
     */
    @Test
    public void testExtractDetailsUsingResponse() {
        Response response = when()
                .get("https://jsonplaceholder.typicode.com/photos/1")
                .then()
                .extract()
                .response();

        System.out.println("Content Type : " +response.contentType());
        System.out.println("Url : " +response.path("url"));
        System.out.println("Status Code : " +response.statusCode());
    }

   /* public void testSchema() {
        given()
                .get("http://geo.groupkt.com/ip/172.217.4.14/json")
                .then()
                .assertThat(matchesJsonSchemaInClassPath())
    }*/
}
