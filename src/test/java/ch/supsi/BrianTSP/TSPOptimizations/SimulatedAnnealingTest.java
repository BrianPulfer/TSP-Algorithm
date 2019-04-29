package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SimulatedAnnealingTest {

    public List<Integer> doubleBridgeSwapMock(List<Integer> cities, int[] citiesToSwap){
        ArrayList<Integer> retval = new ArrayList<Integer>();

        Arrays.sort(citiesToSwap);

        for(int i = 0; i<cities.size(); i++){
            int temp = i;
            retval.add(cities.get(temp));

            if(i == citiesToSwap[0]){
                temp = citiesToSwap[2] + 1;
            } else if (i == citiesToSwap[1]){
                temp = citiesToSwap[3] + 1;
            } else if(i == citiesToSwap[2]){
                temp = citiesToSwap[0] + 1;
            } else if (i == citiesToSwap[3]){
                temp = citiesToSwap[1] +1;
            }
        }

        return retval;
    }

    @Ignore
    @Test
    public void doubleBridgeSwapMockTest(){
        List<Integer> cities = Arrays.asList(1,2,3,4,5,6,7,8);
        int[] toSwap = {0,2,4,6};

        List<Integer> computed = doubleBridgeSwapMock(cities, toSwap);

        List<Integer> result = Arrays.asList(1,6,7,4,5,2,3,8);
        assertEquals(computed, cities);
    }
}