package examples;

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class Test11_TimeMesurement {

    /**
     * Response time measurement
     */
    @Test
    public void testResponseTime() {
        long t = given()
                .get("https://jsonplaceholder.typicode.com/photos")
                .time();

        System.out.println("Time is : " +t);
    }

    @Test
    public void testResponseTimeInUnit() {
        long t = given()
                .get("https://jsonplaceholder.typicode.com/photos/1")
                .timeIn(TimeUnit.MILLISECONDS);

        System.out.println("Response getting in second : " +t);
    }

    @Test
    public void testResponseTimeAssertion() {
        given()
                .get("https://jsonplaceholder.typicode.com/photos/1")
                .then()
                .time(lessThan(1900L));
    }
}
