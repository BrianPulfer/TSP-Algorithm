package ch.supsi.BrianTSP;

import ch.supsi.BrianTSP.TSPAlgorithm.BrianTSPAlgorithm;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPAlgorithm;

import java.io.File;

public class Main {
    public static void main(String[] args){

        System.out.println("Trying to open file: " + args[0]);
        File file = new File(args[0]);

        TSPFile tspFile = new TSPFile(file);
        System.out.println(tspFile.getName());

        /*
        TSPAlgorithm algorithm = new BrianTSPAlgorithm(30);
        System.out.println("Algorithm result is: "+algorithm.compute(tspFile));
        */
    }
}
