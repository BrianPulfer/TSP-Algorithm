package ch.supsi.BrianTSP.TSPAlgorithm;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NearestNeighbor implements TSPAlgorithm{

    private List<City> ordered = new ArrayList<City>();

    public int compute(TSPFile file) {
        ArrayList<City> cities = new ArrayList<City>(Arrays.asList(file.getCities()));

        City currentCity = cities.get(0);
        City nextCity;
        ordered.add(currentCity);

        int total_cities = cities.size();

        for(int i = 0; i<total_cities-1; i++){
            nextCity = findClosest(currentCity, cities);
            ordered.add(nextCity);
            cities.remove(currentCity);
            currentCity = nextCity;
        }

        return TSPUtilities.totalLength(ordered);
    }

    private City findClosest(City city, List<City> cities) {
        int current_closest_distance = -1;
        int current_closest_index = -1;

        for(int i = 0; i<cities.size(); i++){
            if(cities.get(i) != city){
                if(current_closest_distance == -1){
                    current_closest_distance = city.getDistanceFrom(cities.get(i));
                    current_closest_index = i;
                } else{
                    if(city.getDistanceFrom(cities.get(i)) < current_closest_distance){
                        current_closest_distance = city.getDistanceFrom(cities.get(i));
                        current_closest_index = i;
                    }
                }
            }

        }
        return cities.get(current_closest_index);
    }

    public List<City> citiesFinalOrder() {
        return ordered;
    }
}