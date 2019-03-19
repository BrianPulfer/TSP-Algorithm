package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.*;

public class TwoOpt extends TSPOptimization {

    public TwoOpt(List<City> start) {
        super(start);
    }

    public void optimize() {
        List<City> test = start;
        optimization = start;

        int minimumDistance = TSPUtilities.totalLength(test);

        boolean optimized;

        do {
            optimized = false;
            for (int i = 0; i < start.size(); i++) {
                for (int j = i + 1; j < start.size(); j++) {
                    test = start;

                    HashMap<Integer, ArrayList<City>> step = swap(test, i, j);

                    int newLenght = (Integer) step.keySet().toArray()[0];
                    test = step.get(newLenght);

                    if (newLenght < minimumDistance) {
                        minimumDistance = TSPUtilities.totalLength(test);
                        optimization = test;
                        optimized = true;
                    }
                }
            }

            if(optimized){
                start = optimization;
            }

        } while(optimized);
    }

    private HashMap<Integer, ArrayList<City>> swap(List<City> test, int i, int j) {
        ArrayList<City> newTrip = new ArrayList<City>();

        //i is always the lower bound.
        if(i>j){
            int t = i;
            i = j;
            j = t;
        }

        int temp = 1;
        int newTripLenght = 0;

        for(int k = 0; k < test.size(); k++){
            if(k==i){
                newTrip.add(test.get(j));
            } else if (k == j){
                newTrip.add(test.get(i));
            } else if(k<i || k>j){
                newTrip.add(test.get(k));
            } else {
                newTrip.add(test.get( j - temp));
                temp++;
            }

            if(k != 0){
                newTripLenght += newTrip.get(newTrip.size()-2).getDistanceFrom(newTrip.get(newTrip.size()-1));
            }

            if(k == test.size()-1){
                newTripLenght += newTrip.get(newTrip.size()-1).getDistanceFrom(newTrip.get(0));
            }
        }

        HashMap<Integer, ArrayList<City>> retval = new HashMap<Integer, ArrayList<City>>();
        retval.put(newTripLenght, newTrip);
        return retval;
    }
}
