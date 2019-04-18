package ch.supsi.BrianTSP.TSPAlgorithm;

import ch.supsi.BrianTSP.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TSPUtilities {

    //private static long seed = 1557753992;
    private static long seed = 1782757382;

    private static final Random random = new Random(seed);

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
        int oldDistance = 0;
        int newDistance = 0;

        //TODO: REMOVE
        if(i>j){
            firstSwap = test.get(j);
            secondSwap = test.get(i);

            if(j == 0){
                beforeFirstSwap = test.get(test.size() -1);
            } else {
                beforeFirstSwap = test.get(j-1);
            }

            if(i == test.size()-1) {
                afterSecondSwap = test.get(0);
            } else {
                afterSecondSwap = test.get(i + 1);
            }

            if(j == 0 && i == test.size()-1){
                beforeFirstSwap = test.get(test.size()-2);
                firstSwap = test.get(test.size()-1);
                secondSwap = test.get(0);
                afterSecondSwap = test.get(1);
            }

        } else {
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
        }


        oldDistance = beforeFirstSwap.getDistanceFrom(firstSwap) + secondSwap.getDistanceFrom(afterSecondSwap);
        newDistance = beforeFirstSwap.getDistanceFrom(secondSwap) + firstSwap.getDistanceFrom(afterSecondSwap);

        return (newDistance - oldDistance);
    }

    public static int testSwap(List<City> test, City c1, City c2){
        return testSwap(test, test.indexOf(c1), test.indexOf(c2));
    }

    public static List<City> swap(List<City> test, int i, int j) {
        /*
        //TODO: REMOVE
        if(i>=j){
            System.err.println("MALE");
        }
        */

        ArrayList<City> newTrip = new ArrayList<City>();

        int temp = 1;

        if(i>j){
            while(i>0){
                i--; j--;
                if(j < 0){
                    j = test.size()-1;
                }
                City tail = test.remove(0);
                test.add(tail);
            }
        }

        for (int k = 0; k < test.size(); k++) {
            if (k == i) {
                newTrip.add(test.get(j));
            } else if (k == j) {
                newTrip.add(test.get(i));
            } else if (k < i || k > j) {
                newTrip.add(test.get(k));
            } else {
                newTrip.add(test.get(j - temp));
                temp++;
            }
        }

        return newTrip;
    }

    public static List<City> randomSwap(List<City> cities){
        int i = (int) (Math.random() * cities.size());
        int j = (int) (Math.random() * cities.size());

        return swap(cities, i, j);
    }

    public static Random getRandom() {
        return random;
    }

    public static long getSeed() {
        return seed;
    }

    public static City[][] getMinDistances(List<City> cities, int size){
        City[][] retval = new City[cities.size()][size];

        for(int i = 0; i<cities.size(); i++){
            int cityID = cities.get(i).getId();

            List<City> tempCities = new ArrayList<City>(cities);

            for(int j = 0; j<size; j++){
                City closest = findClosestFromCity(cities.get(i) ,tempCities);
                tempCities.remove(closest);

                retval[cityID-1][j] = closest;
            }
        }

        return retval;
    }

    private static City findClosestFromCity(City city, List<City> cities) {
        City retval = null;
        int minDistance = Integer.MAX_VALUE;

        for(City c : cities){
            if(c != city && city.getDistanceFrom(c) < minDistance){
                minDistance = city.getDistanceFrom(c);
                retval = c;
            }
        }

        return retval;
    }
}
