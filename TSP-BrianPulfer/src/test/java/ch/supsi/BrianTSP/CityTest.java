package ch.supsi.BrianTSP;

import ch.supsi.BrianTSP.City;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityTest {

    private City city1;
    private City city2;
    private City city3;

    @Before
    public void init(){
        city1 = new City(1,0,0);
        city2 = new City(2,100,200);
        city3 = new City(3,94.8320, 34.95237);
    }

    @Test
    public void getDistanceFrom() {
        assertTrue( (int) (Math.sqrt(100*100+200*200) + 0.5) == city1.getDistanceFrom(city2));
        assertTrue( (int) (Math.sqrt(Math.pow(94.8320, 2) + Math.pow(34.95237, 2)) + 0.5) == city1.getDistanceFrom(city3));
        assertTrue( (int) (Math.sqrt(Math.pow(100 - 94.832, 2) + Math.pow(200 - 34.95237, 2)) + 0.5) == city2.getDistanceFrom(city3));
    }
}