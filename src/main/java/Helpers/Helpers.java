package Helpers;

import Entities.Response.TriangleCreateResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.config.JsonPathConfig;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Helpers {

    static final String BASE_URI = "https://qa-quiz.natera.com";
    static final String TRIANGLE = "/triangle/";
    List<String> list = new ArrayList<String>();

    public static Double getPerimeterById(String id) {

        JsonPathConfig cfg = new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
        BigDecimal perimeter = given()
                .log().all()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .when()
                .get(TRIANGLE + id + "/perimeter")
                .then()
                // .statusCode(200)
                .log().all()
                .extract()
                .jsonPath()
                .using(cfg).
                        get("result");
        Double myDouble = perimeter.doubleValue();

        System.out.println("Perimeter is: " + myDouble);
        return myDouble;
    }

    // @AfterSuite
    public static void deleteCreatedTriangles(String id) {
        given()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .when()
                .delete(TRIANGLE + id)
                .then()
                .extract()
                .body().asString();

    }


    public static Double getAreaById(String id) {

        JsonPathConfig cfg = new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
        BigDecimal result = given()
                .log().all()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .when()
                .get(TRIANGLE + id + "/area")
                .then()
                .log().all()
                .extract()
                .jsonPath()
                .using(cfg).
                        get("result");
        Double area = result.doubleValue();

        System.out.println("Perimeter is: " + area);
        return area;
    }


    @Test
    public void deleteAll() {
        RestAssured.defaultParser = Parser.JSON;
        List<TriangleCreateResponse> responseAll = Arrays.asList(given().log().all()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .when()
                .get(TRIANGLE + "all")
                .then()
                .log().all().statusCode(200)
                .extract()
                .body().as(TriangleCreateResponse[].class));

        responseAll.forEach(it -> deleteCreatedTriangles(it.getId()));
    }

}
