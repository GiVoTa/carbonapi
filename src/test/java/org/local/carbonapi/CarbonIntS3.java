package org.local.carbonapi;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CarbonIntS3 {

    @Parameterized.Parameters(name = "Fuel: {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, "Biomass"}, {2, "Coal"}, {3, "Imports"},
                {4, "Gas"}, {5, "Nuclear"}, {6, "Other"},
                {7, "Hydro"}, {8, "Solar"}, {9, "Wind"}
        });
    }

    @Parameterized.Parameter()
    public int rInput;

    @Parameterized.Parameter(1)
    public String rName;

    @Test
    public void test_CheckResponse() {

        ArrayList<Fuel> master = new ArrayList();

        Response response =
                given().
                        param("param_name", "param_value").
                        when().
                        get("https://api.carbonintensity.org.uk/regional").
                        then().
                        contentType(JSON).
                        extract().
                        response();

        ArrayList control = response.path("data.regions");
        ArrayList fuels = response.path("data.regions.generationmix[0].fuel[0]");
        ArrayList allPercentages = response.path("data.regions.generationmix.perc[0]");
        ArrayList fullRegionName = response.path("data.regions.dnoregion[0]");

        for (int i = 0; i < fuels.size(); i++) {
            ArrayList regionPercentages = (ArrayList)allPercentages.get(i);
            //System.out.println(regionPercentages.get(4));
            for (int j = 0; j < regionPercentages.size(); j++) {
                BigDecimal p = new BigDecimal(regionPercentages.get(j).toString());
                System.out.println(p.intValue());
                //master.add(new Fuel(p.intValue(), ));
            }

        }

        //System.out.println(allPercentages.get(0));
    }

}