package ch.supsi.BrianTSP;

import ch.supsi.BrianTSP.TSPAlgorithm.BrianTSPAlgorithm;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPAlgorithm;

import java.io.File;

public class Main {
    public static void main(String[] args){

        System.out.println("Trying to open file: " + args[0]);
        File file = new File(args[0]);

        TSPFile tspFile = new TSPFile(file);

        TSPAlgorithm algorithm = new BrianTSPAlgorithm();


        System.out.println("Algorithm result is: ");
    }
}
