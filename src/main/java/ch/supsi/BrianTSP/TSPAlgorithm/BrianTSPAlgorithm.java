package ch.supsi.BrianTSP.TSPAlgorithm;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPFile;
import java.util.ArrayList;
import java.util.List;


/*Idea contorta di soluzione dove si prendono i nodi più lontani e si percorre il grafo senza mai tornare dietro
  rispetto alla linea tracciata congiungendo i due nodi più lontani. */
public class BrianTSPAlgorithm implements TSPAlgorithm {

    private Point directional_vector;
    private Point scanner_vector;
    private final double scanner_width;
    private ArrayList<City> ordered_cities;

    public int getTotalDistance() {
        return totalDistance;
    }

    private int totalDistance;

    //Scanner width is a percentage of total distance and has to be a value between 0 and 1.
    public BrianTSPAlgorithm(double scanner_width) {
        if(scanner_width < 0 || scanner_width > 1){
            throw new RuntimeException("Selected scanner width not possible.");
        }
        this.scanner_width = scanner_width;
        ordered_cities = new ArrayList<City>();
    }


    public int compute(TSPFile file) {
        City[] most_distants = find_2_most_distant(file.getCities());
        directional_vector = getVector(most_distants);
        scanner_vector = directional_vector.perpendicular();

        Point start = new Point(most_distants[0].getLat(), most_distants[0].getLon());
        Point delta = new Point(directional_vector.x * scanner_width, directional_vector.y * scanner_width);

        connectCities(start, delta, file.getCities());
        return totalDistance;
    }

    //TODO: RICOMINCIARE DA QUA, salvare ordine città e distanza cumulata.
    private void connectCities(Point start, Point delta, City[] cities) {

    }

    private Point getVector(City[] most_distants) {
        double delta_x = most_distants[1].getLat() - most_distants[0].getLat();
        double delta_y = most_distants[1].getLon() - most_distants[0].getLon();
        return new Point(delta_x, delta_y);
    }

    private City[] find_2_most_distant(City[] cities) {
        int maxDistance = 0;
        City[] retval = new City[2];

        for(int i = 0; i<cities.length; i++){
            for(int j = i+1; j<cities.length; j++){
                if(cities[i].getDistanceFrom(cities[j]) > maxDistance){
                    maxDistance = cities[i].getDistanceFrom(cities[j]);
                    retval[0] = cities[i];
                    retval[1] = cities[j];
                }
            }
        }

        return retval;
    }


    public List<City> citiesFinalOrder() {
        return ordered_cities;
    }

    private class Point{
        double x;
        double y;

        public Point(double x, double y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o){
            if(o instanceof Point && ((Point)o).x == this.x && ((Point)o).y == this.y)
                return true;
            return false;
        }

        public Point perpendicular() {
            return new Point(y, -x);
        }

        public void move(Point vector){
            this.x += vector.x;
            this.y += vector.y;
        }
    }
}
