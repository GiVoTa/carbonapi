package org.local.carbonapi;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CarbonIntS2 {

    @Parameterized.Parameters(name = "{1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, "North Scotland"}, {2, "South Scotland"}, {3, "North West England"},
                {4, "North East England"}, {5, "Yorkshire"}, {6, "North Wales and Merseyside"},
                {7, "South Wales"}, {8, "West Midlands"}, {9, "East Midlands"},
                {10, "East England"}, {11, "South West England"}, {12, "South England"},
                {13, "London"}, {14, "South East England"}, {15, "England"},
                {16, "Scotland"}, {17, "Wales"}, {18, "GB"}
        });
    }

    @Parameterized.Parameter()
    public int rInput;

    @Parameterized.Parameter(1)
    public String rName;

    @Test
    public void assertGenMixSums100() {
        Response response =
                given().
                        param("param_name", "param_value").
                        pathParam("regionid", rInput).
                        when().
                        get("https://api.carbonintensity.org.uk/regional/regionid/{regionid}").
                        then().
                        contentType(JSON).
                        extract().
                        response();

        ArrayList percPerRegion = response.path("data.data.generationmix[0].perc[0]");
        float sum = 0;

        for(int i = 0; i < percPerRegion.size(); i++) {
            BigDecimal t = new BigDecimal(percPerRegion.get(i).toString());
            sum += t.floatValue();
        }
        assertEquals(100, sum, 0.0001);
    }
}