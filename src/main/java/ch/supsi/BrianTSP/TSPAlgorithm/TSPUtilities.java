package ch.supsi.BrianTSP.TSPAlgorithm;

import ch.supsi.BrianTSP.City;

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
}
