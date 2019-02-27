package ch.supsi.BrianTSP;

import ch.supsi.BrianTSP.TSPAlgorithm.NearestNeighbor;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPAlgorithm;
import sun.security.util.Resources;

import java.io.File;

public class Main {
    private static final String test_file_path = "/Users/brianpulfer21/Desktop/Semestre Primaverile/Algoritmi avanzati/Coppa Algoritmi/TSP-Algorithm/src/main/resources/TestFile1.tsp";


    public static void main(String[] args){
        System.out.println("Trying to open file: " + args[0]);

        //TODO: Pass arg[0] when program is complete

        File file = new File(test_file_path);

        TSPFile tspFile = new TSPFile(file);
        System.out.println(tspFile.getName());

        TSPAlgorithm algorithm = new NearestNeighbor();
        int result = algorithm.compute(tspFile);

        System.out.println(algorithm.getClass().getSimpleName()+" produced result: "+result);
        System.out.println("Best known: "+tspFile.getBest());
    }
}