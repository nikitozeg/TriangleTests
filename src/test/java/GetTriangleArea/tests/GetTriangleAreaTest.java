package GetTriangleArea.tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Entities.Response.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static Helpers.Helpers.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class GetTriangleAreaTest {

    String BASE_URI = "https://qa-quiz.natera.com";
    String TRIANGLE = "/triangle/";
    List<String> list = new ArrayList<String>();


    @DataProvider(name = "positive-cases", parallel = true)
    public Object[][] positiveTestData() {
        return new Object[][]{
                //isosceles triangle
                {"1,2,2", 0.9682458365518543},

                //decimal
                {"0.1,0.1,0.1", 0.004330127018922195},

                //bigger than INT
                {"4294967295,4294967295,4294967295", 7.9876744887517071E18},

                //    //Triangle types test
                {"1,4,-4", 1.984313483298443},            // check negative values
                {"1,-4,4", 1.984313483298443},            // check negative values
                {"-1,4,4", 1.984313483298443},            // check negative values
                {"3,4,5", 6},           // right-angled triangle
                {"2,3,4", 2.9047375096555625},           //  obtuse triangle
                {"66, 67, 68", 1942.927800382711},           // acute-angled triangle
                {"6, 6, 6", 15.588457268119896},           // equilateral triangle
                {"3.0, 5, 3.0", 4.14578098794425}            // double and int
        };
    }

    @Test(dataProvider = "positive-cases", threadPoolSize = 10, invocationCount = 1)
    public void getTriangleAreaPositiveTest(String val, double area) throws InterruptedException {
        System.out.println("Passed Parameter Is : " + val);

        TriangleCreateResponse as = given()
                .log().all()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body("{\"separator\": \",\",\"input\": \"" + val + "\"}\n")
                .when()
                .post(TRIANGLE)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().as(TriangleCreateResponse.class);


        assertEquals(getAreaById(as.getId()), area);
        deleteCreatedTriangles(as.getId());
    }



}


