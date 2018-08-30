package org.local.carbonapi;

import java.util.*;
import java.lang.*;

public class Region {
    int forecast;
    String simplename, index;

    public Region(String index, int forecast, String simplename) {
        this.index = index;
        this.forecast = forecast;
        this.simplename = simplename;
    }

    public String toString() {
        return this.forecast + " - " + this.simplename;
    }
}

class Sortbyforecast implements Comparator<Region> {
    public int compare (Region a, Region b) {
        return b.forecast - a.forecast;
    }
}