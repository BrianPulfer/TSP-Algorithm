package ch.supsi.BrianTSP.TSPAlgorithm;


import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPFile;

//Strategy pattern
public interface TSPAlgorithm {
    int compute(TSPFile file);
    City[] citiesFinalOrder();
}
