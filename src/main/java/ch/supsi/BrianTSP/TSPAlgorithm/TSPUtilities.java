package ch.supsi.BrianTSP.TSPAlgorithm;

import ch.supsi.BrianTSP.City;

import java.util.ArrayList;
import java.util.List;

public class TSPUtilities {
    public static int totalLength(List<City> cities) {
        int retval = 0;
        for(int i = 0; i<cities.size(); i++){
            if(i+1 < cities.size())
                retval += cities.get(i).getDistanceFrom(cities.get(i+1));
        }

        retval += cities.get(cities.size()-1).getDistanceFrom(cities.get(0));

        return retval;
    }

    public static int testSwap(List<City> test, int i, int j) {
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

    public static ArrayList<City> swap(List<City> test, int i, int j) {
        ArrayList<City> newTrip = new ArrayList<City>();

        //i is always the lower bound.
        if(i>j){
            int t = i;
            i = j;
            j = t;
        }

        int temp = 1;

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
        }


        return newTrip;
    }
}
