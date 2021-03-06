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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args){

        File file = new File(args[0]);

        if(!file.exists()){
            System.out.println("Could not open file "+args[0]);
            return;
        }

        TSPFile tspFile = new TSPFile(file);
        System.out.println("File opened: " + tspFile.getName() + "\nBest known: " + tspFile.getBest());

        TSPAlgorithm algorithm = new NearestNeighbor();
        algorithm.compute(tspFile);

        //Prim prim = new Prim(algorithm.citiesFinalOrder());

        TSPOptimization optimization = new SimulatedAnnealing(algorithm.citiesFinalOrder(), 2, 55);
        optimization.optimize();

        //printStats(optimization.getClass().getSimpleName(), TSPUtilities.totalLength(optimization.getOptimization()), tspFile.getBest());
        //printPathValidity(optimization.getOptimization(), tspFile);
        //printFoundPath(optimization.getOptimization());
        //printBlankSpaces();

        TSPUtilities.writeSolutionToFile(tspFile, optimization.getOptimization());
    }


    private static void writeSeedToFile(long seed, double error){
        try {
            File file = new File("Seeds.txt");

            if(!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(("Seed: "+seed+"    Error: "+error+"\n").getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

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