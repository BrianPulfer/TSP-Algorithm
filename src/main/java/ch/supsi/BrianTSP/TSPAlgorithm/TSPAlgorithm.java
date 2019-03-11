package ch.supsi.BrianTSP.TSPAlgorithm;


import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPFile;

import java.util.List;

//Strategy pattern
public interface TSPAlgorithm {
    int compute(TSPFile file);
    List<City> citiesFinalOrder();
}
