package org.local.carbonapi;

import org.junit.Test;
import io.restassured.http.ContentType;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class AppTest {
    @Parameterized.Parameters(name= "Region Name: {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1, "North Scotland"},
                {2, "South Scotland"}
        });
    }

    @Parameterized.Parameter()
    public int rInput;

    @Parameterized.Parameter(1)
    public String rExpected;

    @Test
    public void test_Number_Of_Regions() {

        given().
                when().
                    get("https://api.carbonintensity.org.uk/regional").
                then().
                    assertThat().
                    body("data.regions.regionid[0]", hasSize(18));
    }

    @Test
    public void test_ResponseHeaderData_ShouldBeCorrect() {

        given().
                when().
                    get("https://api.carbonintensity.org.uk/regional").
                then().
                    assertThat().
                    statusCode(200).
                and().
                    contentType(ContentType.JSON);
    }

    @Test
    public void test_MD5CheckSumForTest() {

        String originalText = "test";
        String expectedMd5CheckSum = "098f6bcd4621d373cade4e832627b4f6";

        given().
                param("text", originalText).
                when().
                    get("http://md5.jsontest.com").
                then().
                    assertThat().
                    body("md5",equalTo(expectedMd5CheckSum));
    }

    @Test
    public void test_NumberOfRegions_Parameterized() {

        String cintensity = "regional";
        int numberOfRegions = 18;

        given().
                pathParam("endPoint",cintensity).
                when().
                    get("https://api.carbonintensity.org.uk/{endPoint}").
                then().
                    assertThat().
                    body("data.regions.regionid[0]", hasSize(numberOfRegions));
    }

    @Test
    public void test_RegionNamesAndID() {
        given().
                pathParam("regionId", rInput).
                when().
                    get("https://api.carbonintensity.org.uk/regional/regionid/{regionId}").
                then().
                    assertThat().
                    body("data.shortname[0]", equalTo(rExpected));
    }

}