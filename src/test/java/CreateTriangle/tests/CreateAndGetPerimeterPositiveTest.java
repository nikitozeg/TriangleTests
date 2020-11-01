package CreateTriangle.tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Entities.Response.*;
import Helpers.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class CreateAndGetPerimeterPositiveTest {

    String BASE_URI = "https://qa-quiz.natera.com";
    String TRIANGLE = "/triangle/";
    List<String> list = new ArrayList<String>();


    @DataProvider(name = "positive-cases",parallel = true)
    public Object[][] positiveTestData() {
        return new Object[][]{
                //isosceles triangle
                {"1,2,2", 5},

                //decimal
                {"0.1,0.1,0.1", 0.3},

                //bigger than INT
                {"4294967295,4294967295,4294967295", 1.2884901885e10},

                //check the work with integer overflow: (2147483647 + 1 = -2147483648, 2147483647 = 2^31 - 1)
                {"2147483647 ,2147483647 ,1", 4.294967295e9},

                // check boundary values for triangle sides
                {"2,3,5", 10},
                {"5,2,3", 10},
                {"3,5,2", 10},

                //Triangle types test
                {"1,4,-4", 9},            // check negative values
                {"3,4,5", 12},           // right-angled triangle
                {"2,3,4", 9},           //  obtuse triangle
                {"66, 67, 68", 201},           // acute-angled triangle
                {"6, 6, 6", 18},           // equilateral triangle
                {"3.0, 5, 3.0", 11}            // double and int
        };
    }

    @Test(dataProvider = "positive-cases",threadPoolSize=10,invocationCount=1)
    public void createTrianglePositiveTest(String val, double perimeter) throws InterruptedException {
        System.out.println("Passed Parameter Is : " + val);

        TriangleCreateResponse as = given()
                .header("X-User", "3b5151b6-4a02-4189-97eb-b25cc224bc60")
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body("{\"separator\": \",\",\"input\": \"" + val + "\"}\n")
                .when()
                .post(TRIANGLE)
                .then()
                .statusCode(200)
                .extract()
                .body().as(TriangleCreateResponse.class);
        //  list.add(as.getId());
        assertEquals(Helpers.getPerimeterById(as.getId()), perimeter);
        Helpers.deleteCreatedTriangles(as.getId());
    }



}


