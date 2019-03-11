package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import java.util.List;

public abstract class TSPOptimization {
    protected List<City> start;
    protected List<City> optimization;

    public TSPOptimization(List<City> start){
        this.start = start;
    }

    public abstract void optimize();

    public List<City> getOptimization(){
        return this.optimization;
    }
}
