package twitter.common;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import twitter.constances.Auth;
import twitter.constances.Path;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class RestUtility {

    public static String ENDPOINT;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPECIFICATION;
    public static ResponseSpecBuilder RESPONSE_BIILDER;
    public static ResponseSpecification RESPONSE_SPECIFICATION;

    public static void setEndPoint(String endPoint) {
        ENDPOINT = endPoint;
    }

    public static RequestSpecification getRequestSpecification() {
        AuthenticationScheme authScheme =
                RestAssured.oauth(Auth.CONSUMER_KEY, Auth.CONSUMER_SECRET, Auth.ACCESS_TOKEN, Auth.ACCESS_SECRET);
        REQUEST_BUILDER = new RequestSpecBuilder();
        REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
        REQUEST_BUILDER.setAuth(authScheme);
        REQUEST_SPECIFICATION = REQUEST_BUILDER.build();
        return REQUEST_SPECIFICATION;
    }

    public static ResponseSpecification getResponseSpecification() {
        RESPONSE_BIILDER = new ResponseSpecBuilder();
        RESPONSE_BIILDER.expectStatusCode(200);
        RESPONSE_BIILDER.expectResponseTime(lessThan(3L), TimeUnit.MINUTES);
        RESPONSE_SPECIFICATION = RESPONSE_BIILDER.build();
        return RESPONSE_SPECIFICATION;
    }

    public static RequestSpecification createQueryParam
            (RequestSpecification resSpc, String param, String value) {
        return resSpc.queryParam(param, value);
    }

    public static RequestSpecification createQueryParam
            (RequestSpecification resSpc, Map<String, String> queryMap) {
        return resSpc.queryParams(queryMap);
    }

    public static RequestSpecification createPathParam
            (RequestSpecification resSpc, String param, String value) {
        return resSpc.pathParam(param, value);
    }

    public static RequestSpecification createPathParam
            (RequestSpecification resSpc, Map<String, String> queryMap) {
        return resSpc.pathParams(queryMap);
    }

    public static Response getResponse() {
        return given().get(ENDPOINT);
    }

    public static Response getResponse(RequestSpecification reqSpec, String type) {
        REQUEST_SPECIFICATION.spec(reqSpec);
        Response response = null;
        if (type.equalsIgnoreCase("get")) {
            response = given().spec(REQUEST_SPECIFICATION).get(ENDPOINT);
        } else if (type.equalsIgnoreCase("post")) {
            response = given().spec(REQUEST_SPECIFICATION).post(ENDPOINT);
        } else if (type.equalsIgnoreCase("put")) {
            response = given().spec(REQUEST_SPECIFICATION).put(ENDPOINT);
        } else if (type.equalsIgnoreCase("put")) {
            response = given().spec(REQUEST_SPECIFICATION).delete(ENDPOINT);
        } else {
            System.out.println("Type is not Matching");
        }
        response.then().log().all();
        response.then().spec(RESPONSE_SPECIFICATION);
        return response;
    }

    public static JsonPath getJsonPath(Response response) {
        String path = response.asString();
        return new JsonPath(path);
    }

    public static XmlPath getXmlPath(Response response) {
        String path = response.asString();
        return new XmlPath(path);
    }

    public static void resetBasePath() {
        RestAssured.basePath = null;
    }

    public static void setContentType(ContentType type) {
        given().contentType(type);
    }
}
