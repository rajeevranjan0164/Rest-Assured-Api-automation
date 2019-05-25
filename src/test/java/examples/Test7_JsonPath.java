package examples;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.when;
import static io.restassured.path.json.JsonPath.from;

public class Test7_JsonPath {

    /**
     * Extract the details as String fetching forther details w/o using jsonPath()
     */
    @Test
    public void testUsingWithoutJsonPath1() {
        String responseAsAString = when()
                .get("https://jsonplaceholder.typicode.com/photos")
                .then()
                .extract()
                .asString();

        List<Integer> id = from(responseAsAString).get("id");
        System.out.println(id.size());
    }

    /**
     * Extract details as String and fetch forther details using JsonPat h
     */
    @Test
    public void testJsonPath2() {
        String json = when()
                .get("http://services.groupkt.com/country/get/all")
                .then()
                .extract()
                .asString();

        JsonPath jsonPath = new JsonPath(json).setRoot("RestResponse.result");

        List<String> ls = jsonPath.get("name");

        Iterator<String> it = ls.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println(ls.size());
    }


}
