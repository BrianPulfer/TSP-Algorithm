package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.*;

public class TwoOpt extends TSPOptimization {

    public TwoOpt(List<City> start) {
        super(start);
    }

    public void optimize() {
        optimization = start;

        boolean optimized;

        do {
            optimized = false;
            int best_i = -1, best_j = -1;

            int bestGain = 0;

            for (int i = 0; i < start.size(); i++) {
                for (int j = i + 1; j < start.size(); j++) {

                    int gain = testSwap(optimization, i, j);

                    if (gain < bestGain) {
                        bestGain = gain;
                        optimized = true;
                        best_i = i; best_j = j;
                    }
                }
            }

            if(optimized){
                optimization = swap(optimization, best_i, best_j);
            }
        } while(optimized);
    }

    private int testSwap(List<City> test, int i, int j) {
        City beforeFirstSwap, firstSwap, secondSwap, afterSecondSwap;

        firstSwap = test.get(i);
        secondSwap = test.get(j);

        if(i == 0){
            beforeFirstSwap = test.get(test.size()-1);
        } else {
            beforeFirstSwap = test.get(i-1);
        }

        if(j == test.size()-1){
            afterSecondSwap = test.get(0);
        } else {
            afterSecondSwap = test.get(j+1);
        }

        //Check if we're trying to swap 2 consecutive cities (bugs happens else)
        if(i == 0 && j == test.size()-1){
            beforeFirstSwap = test.get(test.size()-2);
            firstSwap = test.get(test.size()-1);
            secondSwap = test.get(0);
            afterSecondSwap = test.get(1);
        }

        int oldDistance = beforeFirstSwap.getDistanceFrom(firstSwap) + secondSwap.getDistanceFrom(afterSecondSwap);
        int newDistance = beforeFirstSwap.getDistanceFrom(secondSwap) + firstSwap.getDistanceFrom(afterSecondSwap);

        return (newDistance - oldDistance);
    }

    private ArrayList<City> swap(List<City> test, int i, int j) {
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


        return newTrip;
    }
}
