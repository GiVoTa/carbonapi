package org.local.carbonapi;

import io.restassured.response.Response;
import org.junit.Test;
import java.util.*;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;



public class ParamExt {

    Response response =
            given().
                    param("param_name", "param_value").
                    when().
                    get("https://api.carbonintensity.org.uk/regional").
                    then().
                    contentType(JSON).
                    extract().
                    response();
    ArrayList shortnames = response.path("data.regions.shortname[0]");
    ArrayList forecasts = response.path("data.regions.intensity.forecast[0]");
    ArrayList indexes = response.path("data.regions.intensity.index[0]");

    @Test
    public void test_ResponseExtr() {

        ArrayList<Region> master = new ArrayList();

        for (int i = 0; i < indexes.size(); i++) {
            master.add(new Region(indexes.get(i).toString(),(int) forecasts.get(i), shortnames.get(i).toString()));
        }

        Collections.sort(master, new Sortbyforecast());
        for (int i = 0; i < master.size(); i++) {
            System.out.println(master.get(i));
        }
    }
}
