package ch.supsi.BrianTSP.MST;

import ch.supsi.BrianTSP.City;

import java.util.ArrayList;
import java.util.List;

public abstract class MinimumSpanningTree {

    protected List<City> start;

    public MinimumSpanningTree(List<City> start){
        this.start = new ArrayList<City>(start);
    }

    public abstract List<Arch> find();
}
