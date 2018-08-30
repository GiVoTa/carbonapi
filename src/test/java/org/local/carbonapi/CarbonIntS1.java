package org.local.carbonapi;

import io.restassured.response.Response;
import org.junit.Test;
import java.util.*;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;


public class CarbonIntS1 {

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

    /*
        While tests should not depend on other tests, in this specific scenario the master array list
        should not be built if any assertion fails
    */

    public void validateArrayData(int val) {
        given().
                pathParam("regionid", val + 1).
                when().
                get("https://api.carbonintensity.org.uk/regional/regionid/{regionid}").
                then().
                assertThat().
                body("data.shortname[0]", equalTo(shortnames.get(val))).
                body("data.data.intensity[0].forecast[0]", equalTo(forecasts.get(val))).
                body("data.data.intensity[0].index[0]", equalTo(indexes.get(val)));
    }

    @Test
    public void saveAndSortResponse() {


        ArrayList<Region> master = new ArrayList();

        for (int i = 0; i < indexes.size(); i++) {
            validateArrayData(i);
            master.add(new Region(indexes.get(i).toString(), (int) forecasts.get(i), shortnames.get(i).toString()));
        }

        Collections.sort(master, new sortByForecast());
        for (int i = 0; i < master.size(); i++) {


            System.out.println(master.get(i));
        }
    }
}
