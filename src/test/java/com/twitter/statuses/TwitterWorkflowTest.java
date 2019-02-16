package com.twitter.statuses;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import twitter.common.RestUtility;
import twitter.constances.EndPoints;
import twitter.constances.Path;

public class TwitterWorkflowTest {
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    String tweetId = "";

    @BeforeClass
    public void setup() {
        reqSpec = RestUtility.getRequestSpecification();
        reqSpec.basePath(Path.STATUSES);
        resSpec = RestUtility.getResponseSpecification();
    }

    @Test
    public void postTweet() {
        Response response =
                given()
                        .spec(RestUtility.createQueryParam(reqSpec, "status", "My First Tweet"))
                        .when()
                        .post(EndPoints.STATUSES_TWEET_POST)
                        .then()
                        .spec(resSpec)
                        .extract()
                        .response();
        JsonPath jsPath = RestUtility.getJsonPath(response);
        tweetId = jsPath.get("id_str");
        System.out.println("The response.path: " + tweetId);
    }

    @Test(dependsOnMethods = {"postTweet"})
    public void readTweet() {
        RestUtility.setEndPoint(EndPoints.STATUSES_READ_SINGLE);
        Response res = RestUtility.getResponse(
                RestUtility.createQueryParam(reqSpec, "id", tweetId), "get");
        String text = res.path("text");
        System.out.println("The tweet text is: " + text);
    }

    @Test(dependsOnMethods = {"readTweet"})
    public void deleteTweet() {
        given()
                .spec(RestUtility.createPathParam(reqSpec, "id", tweetId))
                .when()
                .post(EndPoints.STATUSES_TWEET_DISTROY)
                .then()
                .spec(resSpec);
    }
}