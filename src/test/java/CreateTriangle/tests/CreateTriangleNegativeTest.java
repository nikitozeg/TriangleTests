package CreateTriangle.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import java.util.Arrays;

import io.restassured.response.ExtractableResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Entities.Response.*;

import java.util.ArrayList;
import java.util.List;

import static Helpers.Helpers.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class CreateTriangleNegativeTest {

    String BASE_URI = "https://qa-quiz.natera.com";
    String TRIANGLE = "/triangle/";
    List<String> list = new ArrayList<String>();


    @DataProvider(name = "negative-cases", parallel = true)
    public Object[][] negativeCases() {
        return new Object[][]{
                {"0,0,0", "Cannot process input"}, //should not be created
                {"2,1,1", "Cannot process input"}, //should not be created
                {"1,2,1", "Cannot process input"}, //should not be created
                {"1,1,2", "Cannot process input"}, //should not be created
                {"3,5,2,2", "Cannot process input"}, //should not be created
                {"3,5,0", "Cannot process input"}, //should not be created
                {"3,5,", "Cannot process input"}, //should not be created
                {"0.2,0.1,0.1", "Cannot process input"}, //should not be created
                {"0.1,0.2,0.1", "Cannot process input"}, //should not be created
                {"0.1,0.1,0.2", "Cannot process input"}, //should not be created
                {";;", "Cannot process input"}, //
                {"1,,", "Cannot process input"}, //
                {",0,", "Cannot process input"}, //
                {",,1", "Cannot process input"}, //
                {",0,1", "Cannot process input"}, //
                {"q,w,e", "Cannot process input"}, //
                {"", "Cannot process input"}, //
                {"select * from table where id=..,1,2", "Cannot process input"}, // SQL injection check
        };
    }

    @Test(dataProvider = "negative-cases", threadPoolSize = 20, invocationCount = 1)
    public void createTriangleNegativeTest(String val, String message) throws Exception {
        System.out.println("Passed Parameter Is : " + val);

        ExtractableResponse a = given()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body("{\"separator\": \",\",\"input\": \"" + val + "\"}\n")
                .when()
                .post(TRIANGLE)
                .then()
                .extract();

        if (a.statusCode() == 422) {
            TriangleCreateErrorResponse aas = a.body().as(TriangleCreateErrorResponse.class);
            assertEquals(aas.getMessage(), message);

        } else if (a.statusCode() == 200) {
            deleteCreatedTriangles(a.body().as(TriangleCreateResponse.class).getId());
            throw new Exception("Triangle should not be created with parameters: " + val);
        } else {
            throw new Exception("Status code is not as expected");
        }

    }



}


