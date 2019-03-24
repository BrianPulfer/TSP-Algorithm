package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulatedAnnealing extends TSPOptimization {

    private double temperature = 100;
    private double endTime;
    private double slack = 20;

    private List<City> currentSolution;
    private List<City> bestSolution;

    private int bestSolutionLenght;

    public SimulatedAnnealing(List<City> start, int min, int seconds) {
        super(start);
        currentSolution = start;
        bestSolution = currentSolution;
        bestSolutionLenght = TSPUtilities.totalLength(bestSolution);

        endTime = System.currentTimeMillis() + min*60*1000 + seconds*1000;
    }

    public void optimize() {
        DoubleBridge db = new DoubleBridge();

        List<City> localOptimization = new ArrayList<City>();

        while (System.currentTimeMillis() + slack < endTime){
            localOptimization = db.swapRandomly(currentSolution);

            TwoOpt twoOpt = new TwoOpt(localOptimization);
            twoOpt.optimize();

            int newLenght = TSPUtilities.totalLength(twoOpt.getOptimization());
            int currentLenght = TSPUtilities.totalLength(currentSolution);

            if(newLenght < currentLenght){
                currentSolution = twoOpt.optimization;

                if(newLenght < bestSolutionLenght){
                    bestSolutionLenght = newLenght;
                    bestSolution = twoOpt.getOptimization();
                }
            } else {
                double error = (newLenght - currentLenght)/ currentLenght;
                if(getProbability(error) > Math.random()){
                    currentSolution = twoOpt.getOptimization();
                }
            }
        }

        this.optimization = bestSolution;
    }

    //Deprecated.
    //We shall use the double bridge with 2 opt variation instead of random guesses.
    private void randomSimulatedAnnealing(){
        int left, right;

        while(System.currentTimeMillis() + slack < endTime){
            do {
                left = (int) (Math.random() * currentSolution.size());
                right = (int) (Math.random() * currentSolution.size());
            } while (left == right);

            int deltaError = TSPUtilities.testSwap(currentSolution, left, right);

            if(deltaError < 0){
                currentSolution = TSPUtilities.swap(currentSolution,left, right);

                int newSolutionLenght = TSPUtilities.totalLength(currentSolution);

                if(newSolutionLenght < bestSolutionLenght){
                    this.bestSolutionLenght = newSolutionLenght;
                    this.bestSolution = currentSolution;
                }
            } else {
                if(getProbability(deltaError) > Math.random()){
                    currentSolution = TSPUtilities.swap(currentSolution, left, right);
                }
            }
        }

        this.optimization = bestSolution;
    }

    private double getProbability(double error){
        temperature = temperature * 0.95;
        return Math.pow(Math.E, (-error / temperature));  // e^^(-DE/T)
    }

    private class DoubleBridge{
        public List<City> swapRandomly(List<City> cities){
            ArrayList<City> retval = new ArrayList<City>();

            int[] citiesToSwap = new int[4];

            //TODO: Verify you don't have the same city twice.
            for(int i = 0; i<citiesToSwap.length; i++)
                citiesToSwap[i] = (int) (Math.random() * cities.size());

            Arrays.sort(citiesToSwap);

            retval = TSPUtilities.swap(cities, citiesToSwap[0], citiesToSwap[2]);
            retval = TSPUtilities.swap(retval, citiesToSwap[1], citiesToSwap[3]);

            return retval;
        }
    }
}