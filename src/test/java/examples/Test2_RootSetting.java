package examples;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Test2_RootSetting {

    /**
     * Basic way to test the all parameters without rootPath
     */
    @Test
    public void testWithoutRoot() {
        given()
                .get("http://services.groupkt.com/country/get/iso2code/IN")
                .then()
                .body("RestResponse.result.name", is("India"))
                .body("RestResponse.result.alpha2_code", is("IN"))
                .body("RestResponse.result.alpha3_code", is("IND"));
    }

    /**
     * Suggested test all the parameter using rootPath or root
     * Because no need to write same path again and again.
     */
    @Test
    public void testWithRootSetting() {
        given()
                .get("http://services.groupkt.com/country/get/iso2code/IN")
                .then()
                .rootPath("RestResponse.result")
                .body("name", is("India"))
                .body("alpha2_code", is("IN"))
                .body("alpha3_code", is("IND"));
    }

    /**
     * we can detach root in between or we can detach root some specific root
     */
    @Test
    public void testDetachRootPath() {
        given()
                .get("http://services.groupkt.com/country/get/iso2code/IN")
                .then()
                .rootPath("RestResponse.result")
                .body("name", is("India"))
                .detachRoot("result")
                .body("result.alpha3_code", is("IND"));
    }


}
