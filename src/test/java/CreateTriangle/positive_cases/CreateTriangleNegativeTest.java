package CreateTriangle.positive_cases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Entities.Response.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class CreateTriangleNegativeTest {

    String BASE_URI = "https://qa-quiz.natera.com";
    String TRIANGLE = "/triangle/";
    List<String> list = new ArrayList<String>();


    @DataProvider(name = "negative-cases")
    public Object[][] negativeCases() {
        return new Object[][]{
                {"0,0,0", "Cannot process input"}, //should not be created
                {"2,1,1", "Cannot process input"}, //should not be created
                {"1,2,1", "Cannot process input"}, //should not be created
                {"1,1,2", "Cannot process input"}, //should not be created
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
                // check negative values
        };
    }

    @Test(dataProvider = "negative-cases")
    public void createTriangleNegativeTest(String val, String message) {
        System.out.println("Passed Parameter Is : " + val);

        TriangleCreateErrorResponse aas = given()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body("{\"separator\": \",\",\"input\": \"" + val + "\"}\n")
                .when()
                .post(TRIANGLE)
                .then()
                .statusCode(422)
                .extract()
                .body().as(TriangleCreateErrorResponse.class);

        assertEquals(aas.getMessage(), message);

    }




    // @AfterSuite
    public void deleteCreatedTriangles(String id) {

        // for (int i = 0; i < list.size(); i++) {
        given()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                //  .log().everything()
                .contentType(ContentType.JSON)
                .when()
                // .delete(TRIANGLE + list.get(i))
                .delete(TRIANGLE + id)
                .then()
                .extract()
                .body().asString();

        //  }
    }
    @Test
    public void deleteAll() {
        RestAssured.defaultParser = Parser.JSON;
        // for (int i = 0; i < list.size(); i++) {
        //TriangleCreateResponse triangles=
                given()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                //  .log().everything()
                .contentType(ContentType.JSON)
                .when()
                // .delete(TRIANGLE + list.get(i))
                .get(TRIANGLE + "/all")

                .then()
                        .statusCode(200)
                .log().all();
                //.extract()


        //  }
    }
}


