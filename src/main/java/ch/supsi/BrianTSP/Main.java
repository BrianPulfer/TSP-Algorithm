package ch.supsi.BrianTSP;

import ch.supsi.BrianTSP.MST.Arch;
import ch.supsi.BrianTSP.MST.Prim;
import ch.supsi.BrianTSP.TSPAlgorithm.NearestNeighbor;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPAlgorithm;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;
import ch.supsi.BrianTSP.TSPOptimizations.SimulatedAnnealing;
import ch.supsi.BrianTSP.TSPOptimizations.TSPOptimization;
import ch.supsi.BrianTSP.TSPOptimizations.TabuSearch;
import ch.supsi.BrianTSP.TSPOptimizations.TwoOpt;

import java.io.File;
import java.util.HashSet;
import java.util.List;

public class Main {
    private static final String test_file_path = "/Users/brianpulfer21/Desktop/Semestre Primaverile/Algoritmi avanzati/Coppa Algoritmi/TSP-Algorithm/src/main/resources/";


    public static void main(String[] args){

        double errorsSum = 0;
        int tests = 10;

        for(int i = tests-1; i>=0; i--) {

            //TODO: Pass arg[0] when program is complete
            File file = new File(test_file_path+i+".tsp");

            TSPFile tspFile = new TSPFile(file);
            System.out.println("File opened: " + tspFile.getName()+"\nBest known: "+tspFile.getBest());

            TSPAlgorithm algorithm = new NearestNeighbor();
            algorithm.compute(tspFile);

            //Prim prim = new Prim(algorithm.citiesFinalOrder());

            TSPOptimization optimization = new SimulatedAnnealing(algorithm.citiesFinalOrder(), /*prim,*/0 , 10);
            optimization.optimize();


            printStats(optimization.getClass().getSimpleName(),TSPUtilities.totalLength(optimization.getOptimization()),tspFile.getBest());
            printPathValidity(optimization.getOptimization(), tspFile);
            //printFoundPath(optimization.getOptimization());
            printBlankSpaces();

            errorsSum += getError(TSPUtilities.totalLength(optimization.getOptimization()), tspFile.getBest());

        }
        System.out.println("Error average in percentage: "+errorsSum/tests);
    }

    private static void printBlankSpaces() {
        System.out.println("\n\n\n");
    }

    private static void printPathValidity(List<City> optimization, TSPFile tspFile) {
        if (new HashSet<City>(optimization).size() == tspFile.getDimension()) {
            System.out.println("Path is valid.");
        } else {
            System.err.println("Path is invalid.");
        }
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