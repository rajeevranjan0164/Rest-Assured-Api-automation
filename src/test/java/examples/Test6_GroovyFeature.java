package examples;

import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;

public class Test6_GroovyFeature {

    /**
     * Verify that some expected name present in response or not
     */
    @Test
    public void testPresenceOfElement() {
        given()
                .get("http://services.groupkt.com/country/search?text=lands")
                .then()
                .body("RestResponse.result.name", hasItems("Cayman Islands", "Cocos (Keeling) Islands", "Cook Islands"));
    }

    /**
     * RestAssured implemented n Grovey hence Grovey advantage can be taken
     * Here I am adding length of all alpha3_code code coming in response
     */
    @Test
    public void testLenghthOfResponse() {
        given()
                .get("http://services.groupkt.com/country/search?text=lands")
                .then()
                .body("RestResponse.result.alpha3_code*.length().sum()", greaterThan(40));
    }

    /**
     * Get the all attribute as List from
     */
    @Test
    public void estGetResponseAsList() {
        String response = get("http://services.groupkt.com/country/search?text=lands").asString();
        List<String> ls = from(response).getList("RestResponse.result.name");
        System.out.println(ls.size());

        for (String country : ls) {
            if (country.equals("Cayman Islands")) {
                System.out.println("Place is there");
            } else {
                System.out.println("Place is not there");
                break;
            }
        }
    }

    /**
     * Get response as List and apply some condition on it
     * The Grovey has implicit variable called 'it' which represent current item on the list.
     */
    @Test
    public void testConditionOnList() {
        String response = get("http://services.groupkt.com/country/search?text=lands").asString();

        List<String> ls = from(response).getList("RestResponse.result.findAll {it.name.length()>40}.name");
        System.out.println(ls);
    }


}
