package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.ArrayList;
import java.util.List;

public class TwoOpt extends TSPOptimization {

    public TwoOpt(List<City> start) {
        super(start);
    }

    public void optimize() {
        List<City> test = start;
        List<City> tempTest;
        optimization = start;

        int minimumDistance = TSPUtilities.totalLength(test);

        for(int i = 0; i < start.size(); i++){
            for(int j = i+1; j<start.size(); j++){
                tempTest = test;
                test = swap(test, i, j);

                if(TSPUtilities.totalLength(test) < minimumDistance){
                    minimumDistance = TSPUtilities.totalLength(test);
                    optimization = test;
                } else {
                    test = tempTest;
                }
            }

        }
    }

    private List<City> swap(List<City> test, int i, int j) {
        List<City> retval = new ArrayList<City>();

        for(int k = 0; k<test.size(); k++){
            if(k == i) {
                retval.add(test.get(j));
            } else if(k == j) {
                retval.add(test.get(i));
            } else {
                retval.add(test.get(k));
            }
        }

        return retval;
    }
}
