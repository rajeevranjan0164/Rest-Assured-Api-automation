package examples;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.xml.HasXPath.hasXPath;

public class Test1_BasicForXml {

    /**
     * To test the XML response for single body content
     */
    @Test
    public void testSingleContent() {
        given()
                .get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10")
                .then()
                .body("CUSTOMER.ID", equalTo("10"))
                .log()
                .all();
    }

    /**
     * test XML response for multiple  body content
     */
    @Test
    public void testMultipleContent() {
        given()
                .get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10")
                .then()
                .body("CUSTOMER.ID", equalTo("10"))
                .body("CUSTOMER.FIRSTNAME", equalTo("Sue"))
                .body("CUSTOMER.LASTNAME", equalTo("Fuller"))
                .body("CUSTOMER.STREET", equalTo("135 Upland Pl."))
                .body("CUSTOMER. CITY", equalTo("Dallas"));
    }

    /**
     * Compare complete text in one
     */
    @Test
    public void testCompeteTextInOne() {
        given()
                .get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10")
                .then()
                .body("CUSTOMER.text()", equalTo("10SueFuller135 Upland Pl.Dallas"))
                .log()
                .all();
    }

    /**
     * xpath can also be used to find values
     */
    @Test
    public void testUsingXapth1() {
        given()
                .get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10")
                .then()
                .body(hasXPath("/CUSTOMER/FIRSTNAME", containsString("Sue")));
    }

    /**
     * xpath type
     */
    @Test
    public void testUsingXpath2() {
        given()
                .get("http://www.thomas-bayer.com/sqlrest/INVOICE")
                .then()
                .body(hasXPath("/INVOICEList/INVOICE[text()='49']"))
                .log()
                .all();
    }
}
