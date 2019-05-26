package examples;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class Test9_RequestDemo {

    /**
     * Connect used HTTPS request
     */
    @Test
    public void testConnectRequest() {
        when().
                request("CONNECT", "https://api.fonts.com/rest/json/Account").
                then()
                .statusCode(400);
    }

    /**
     * In Get request we can set query Parameter
     */
    @Test
    public void testQueryParameter() {
        given()
                .queryParam("A", "A value")
                .queryParam("B", "B value")
                .when()
                .get("https://api.fonts.com/rest/json/Account")
                .then()
                .statusCode(400);
    }

    /**
     * Post request we can set form parameter
     */
    @Test
    public void testFromParameter() {
        given()
                .formParam("A", "A value")
                .formParam("B", "B value")
                .when()
                .post("http://api.fonts.com/rest/json/Domins")
                .then()
                .statusCode(400);
    }

    /**
     * Set Parameter - recommended
     * if request is Get then param will treat as QueryParameter
     * if request is post then param will treat as FormParameter
     */
    @Test
    public void testSetParameter() {
        given()
                .param("A", "A value")
                .param("B", "B value")
                .when()
                .get("https://api.fonts.com/rest/json/Account")
                .then()
                .statusCode(400);
    }

    /**
     * To set multiple value parameters
     * we can pass list, multiple value or no value in param
     */
    @Test
    public void testMultipleValueParameter() {
        List<String> list = new ArrayList<String>();
        list.add("One");
        list.add("Two");

        given()
                .param("A", "value1", "Value2", "Value3")
                .param("B")
                .param("C", list)
                .when()
                .get("https://api.fonts.com/rest/json/Account")
                .then()
                .statusCode(400);
    }

    /**
     * To Path Parameter
     */
    @Test
    public void testSetPathParameter() {
        given()
                .pathParam("type", "json")
                .pathParam("section", "Domains")
                .when()
                .post("https://api.fonts.com/rest/{type}/{section}")
                .then()
                .statusCode(400);
    }

    /**
     * cookie can set in request parameter
     */
    @Test
    public void testSetCookiesInRequest() {
        given()
                .cookie("__utmt", "1")
                .when()
                .get("http://webservicex.com/globalweather.asmx?op=GetCitiesByCountry")

                .then()
                .statusCode(200);
    }

    @Test
    public void testSetMultipleCookieAsRequest() {

        //to set multiple value
        given()
                .cookies("key", "value1", "value2");

        // to set details cookies
        Cookie cookie = new Cookie.Builder("some_cookies", "some_value").setSecured(true).setComment("some comment").build();
        given()
                .cookie(cookie)
                .when()
                .get("/cookie")
                .then()
                .assertThat()
                .body(equalTo("x"));

        // To set Details Cookies
        Cookie cookie1 = new Cookie.Builder("some_cookies", "some_value").setSecured(true).setComment("some comment").build();
        Cookie cookie2 = new Cookie.Builder("some_cookies", "some_value").setSecured(true).setComment("some comment").build();

        Cookies cookies = new Cookies(cookie1, cookie2);

        given()
                .cookies(cookies)
                .when()
                .get("/cookies")
                .then()
                .assertThat()
                .body(equalTo("x"));

    }

    /**
     * we can pass single header, headers with multiple value and multiple headers
     */
    @Test
    public void testSetHeadres() {
        given()
                .header("K", "V")
                .header("K10", "value1", "value2", "value3")
                .headers("K1", "V1", "K2", "V2")
                .when()
                .get("https://api.fonts.com/rest/json/Accounts")
                .then()
                .statusCode(200);
    }

    /**
     * ContentType also I can set
     */
    @Test
    public void testSetContentType() {
        given()
                .contentType(ContentType.JSON)
                .contentType("application/json; charset-utf-8")
                .when()
                .get("https://api.fonts.com/rest/json/Accounts")
                .then()
                .statusCode(200);
    }
}
