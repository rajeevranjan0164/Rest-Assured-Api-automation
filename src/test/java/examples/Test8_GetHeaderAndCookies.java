package examples;

import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.get;

public class Test8_GetHeaderAndCookies {

    /**
     * Get the Response Header
     */
    @Test
    public void testResponseHeader() {
       Response response =  get("https://jsonplaceholder.typicode.com/photos");

       // to get the single Header
        String headerCFRAY = response.getHeader("CF-RAY");
        System.out.println(">>>>>>> Header : " +headerCFRAY);

        System.out.println("\n\n\n");

        Headers headers = response.getHeaders();
        for (Header h : headers) {
            System.out.println(h.getName() + " : " +h.getValue());
        }
    }

    /**
     * Get Cookies
     */
    @Test
    public void testGetCookies() {
        Response response =  get("https://jsonplaceholder.typicode.com/photos");

        Map<String, String> cookies = response.getCookies();

        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            System.out.println(entry.getKey() +" : " +entry.getValue());
        }
    }

    /**
     * To Get the Details about the Cookies
     */
    @Test
    public void testDetailsCookies() {
        Response response =  get("https://jsonplaceholder.typicode.com/photos");
        Cookie a = response.getDetailedCookie("__cfduid");

        System.out.println("Details " +a.getExpiryDate());
        System.out.println("Details " +a.hasExpiryDate());
        System.out.println("Details " +a.isSecured());
        System.out.println("Details " +a.getDomain());
    }
}
