package org.local.carbonapi;

import java.util.Comparator;

public class Fuel {
    int perc;
    String dnoregion;

    public Fuel(int perc, String dnoregion) {
        this.perc = perc;
        this.dnoregion = dnoregion;
    }

    public String toString() {
        return this.perc + this.dnoregion;
    }
}

class sortByPerc implements Comparator<Fuel> {
    public int compare (Fuel a, Fuel b) {
        return b.perc - a.perc;
    }
}