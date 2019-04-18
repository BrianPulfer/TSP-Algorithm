package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.MST.Arch;
import ch.supsi.BrianTSP.MST.Prim;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.*;

public class TwoOpt extends TSPOptimization {

    private boolean withCandidateList = false;
    private City[][] minDistances;
    private Prim prim;

    public TwoOpt(List<City> start) {
        super(start);
    }
    public TwoOpt(List<City> start, City[][] minDistances, Prim prim) {
        super(start);
        this.minDistances = minDistances;
        this.prim = prim;
        withCandidateList = true;
    }


    public void optimize() {
       if(withCandidateList){
            optimizeWithCandidateList();
       } else {
           optimizeWithoutCandidateList();
        }
    }

    public void optimizeWithCandidateList(){
        optimization = start;

        boolean optimized;

        do {
            optimized = false;
            int best_i = -1, best_j = -1;

            int bestGain = 0;

            //Finding best gain from closest cities.
            for (int i = 0; i < optimization.size(); i++) {
                for (int j = 0; j < minDistances[optimization.get(i).getId()-1].length; j++) {

                    City nextCloseCity = minDistances[optimization.get(i).getId()-1][j];


                    int closeCitiesGain = TSPUtilities.testSwap(optimization, optimization.get(i), nextCloseCity);

                    if (closeCitiesGain < bestGain) {
                        bestGain = closeCitiesGain;
                        optimized = true;
                        best_i = i; best_j = optimization.indexOf(nextCloseCity);
                    }
                }
            }


            //Finding best gain from Minimum spanning Tree.
            for(int i = 0; i<optimization.size(); i++) {
                List<Arch> arches = prim.getMSTArchesFrom(optimization.get(i));
                int mstGain = 0;

                for(Arch a : arches){
                    City mstCity = a.getOther(optimization.get(i));
                    mstGain = TSPUtilities.testSwap(optimization, optimization.get(i), mstCity);

                    if(mstGain < bestGain){
                        optimized = true;
                        bestGain = mstGain;
                        best_i = i;
                        best_j = optimization.indexOf(mstCity);
                    }
                }
            }

            if(         (best_i == 0 && best_j == optimization.size()-1)
                    || (best_i == optimization.size()-1 && best_j == 0)){
                optimized = false;
            }


            if(optimized){
                optimization = TSPUtilities.swap(optimization, best_i, best_j);
            }

            System.out.println(best_i+" "+best_j);
        } while(optimized);
    }

    private boolean headAndTail(List<City> optimization, City city, City nextCloseCity) {
        if((optimization.indexOf(city) == 0 && optimization.indexOf(nextCloseCity) == optimization.size()-1)
            || optimization.indexOf(nextCloseCity) == 0 && optimization.indexOf(city) == optimization.size()-1){
            return true;
        }
        return false;
    }

    public void optimizeWithoutCandidateList(){
        optimization = start;

        boolean optimized;

        do {
            optimized = false;
            int best_i = -1, best_j = -1;

            int bestGain = 0;

            for (int i = 0; i < optimization.size(); i++) {
                for (int j = i + 1; j < optimization.size(); j++) {

                    int gain = TSPUtilities.testSwap(optimization, i, j);

                    if (gain < bestGain && !(i == 0 && j == optimization.size()-1)) {
                        bestGain = gain;
                        optimized = true;
                        best_i = i; best_j = j;
                    }
                }
            }

            if(optimized){
                optimization = TSPUtilities.swap(optimization, best_i, best_j);
            }
        } while(optimized);
    }


    //Distances is an nx15 matrix containing for each node of the graph the 15 closest nodes.
    /*
    public void optimizeWithCandidateList(List<Arch> prim, City[][] closeCities){
        optimization = start;

        boolean optimized;

        do{
            optimized = false;

            int best_i = -1, best_j = -1;

            int bestGain = 0;

            for (int i = 0; i < start.size(); i++) {
                for(int j = 0; j<closeCities[i].length; j++){
                    City currentCloseCity = closeCities[i][j];

                    int gain = TSPUtilities.testSwap(optimization, );

                    if (gain < bestGain) {
                        bestGain = gain;
                        optimized = true;
                        best_i = i; best_j = j;
                    }
                }
            }

            if(optimized){
                optimization = TSPUtilities.swap(optimization, best_i, closeCities[best_i][best_j]);
            }

        } while (optimized);
    }
    */

    public static int[] bestSwap(List<City> cities){
        int bestGain = Integer.MAX_VALUE;
        int best_i = -1, best_j = -1;

        for (int i = 0; i < cities.size(); i++) {
            for (int j = i + 1; j < cities.size(); j++) {

                int gain = TSPUtilities.testSwap(cities, i, j);

                if (gain < bestGain) {
                    bestGain = gain;
                    best_i = i; best_j = j;
                }
            }
        }

        int[] retval = {best_i, best_j};
        return  retval;
    }
}
