package com.twitter.statuses;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import twitter.common.RestUtility;
import twitter.constances.EndPoints;
import twitter.constances.Path;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class UserTimeLineTest {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp() {
        requestSpecification = RestUtility.getRequestSpecification();
        requestSpecification.queryParam("user_id", "Rajeev Ranjan");
        requestSpecification.basePath(Path.STATUSES);
        responseSpec = RestUtility.getResponseSpecification();
    }

    @Test
    public void readTweetsOne() {
        given()
                //.spec(requestSpecification)
                .spec(RestUtility.createQueryParam(requestSpecification, "count", "1"))
                .when()
                .get(EndPoints.STATUSES_USER_TIMELINE)
                .then()
                .log()
                .all()
                .spec(responseSpec)
                .body("user.screen_name", hasItem("RajeevR87352685"));
    }

    @Test
    public void readTweetsTwo() {
        RestUtility.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);

        Response response = RestUtility.getResponse(RestUtility.createQueryParam(requestSpecification, "count", "1"), "get");
        ArrayList<String> screenName = response.path("user.screen_name");
        System.out.println(screenName);
        Assert.assertTrue(screenName.contains("RajeevR87352685"));
    }
}
