package ch.supsi.BrianTSP;

import ch.supsi.BrianTSP.TSPAlgorithm.NearestNeighbor;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPAlgorithm;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;
import ch.supsi.BrianTSP.TSPOptimizations.SimulatedAnnealing;
import ch.supsi.BrianTSP.TSPOptimizations.TSPOptimization;
import ch.supsi.BrianTSP.TSPOptimizations.TwoOpt;

import java.io.File;
import java.util.List;

public class Main {
    private static final String test_file_path = "/Users/brianpulfer21/Desktop/Semestre Primaverile/Algoritmi avanzati/Coppa Algoritmi/TSP-Algorithm/src/main/resources/TestFile1.tsp";


    public static void main(String[] args){
        System.out.println("Trying to open file: " + args[0]+"\n\n");

        //TODO: Pass arg[0] when program is complete
        File file = new File(test_file_path);

        TSPFile tspFile = new TSPFile(file);
        System.out.println("File opened: "+tspFile.getName());

        TSPAlgorithm algorithm = new NearestNeighbor();
        int result = algorithm.compute(tspFile);

        printStats(algorithm.getClass().getSimpleName(), result, tspFile.getBest());

        System.out.println("\n\n\n");

        /*
        TSPOptimization optimization = new TwoOpt(algorithm.citiesFinalOrder());
        optimization.optimize();

        result = TSPUtilities.totalLength(optimization.getOptimization());

        printStats(optimization.getClass().getSimpleName(), result, tspFile.getBest());

        printFoundPath(algorithm.citiesFinalOrder());
        */
        SimulatedAnnealing sa = new SimulatedAnnealing(algorithm.citiesFinalOrder(), 0, 10);
        sa.optimize();

        printStats(sa.getClass().getSimpleName(),TSPUtilities.totalLength(sa.getOptimization()),tspFile.getBest());
    }

    private static void printFoundPath(List<City> sorted) {
        System.out.println("\n\nFound path:");
        for(int i = 0; i<sorted.size(); i++){
            System.out.print("  -->  "+sorted.get(i).getId());
        }
    }

    private static void printStats(String algorithmName, int result, int best) {
        System.out.println(algorithmName+" produced result: "+result);
        System.out.println("Best known: "+best);
        System.out.println("Error in percentage: "+getError(result,best)+"%");
    }

    private static double getError(double produced, double best){
        return (produced-best)/best*100;
    }
}