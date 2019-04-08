package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.*;

public class SimulatedAnnealing extends TSPOptimization {

    private double temperature = 100;
    private double endTime;

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

        int currentLenght = TSPUtilities.totalLength(currentSolution);

        while (System.currentTimeMillis() < endTime){

            TwoOpt twoOpt = new TwoOpt(db.swapRandomly(currentSolution));
            twoOpt.optimize();

            int newLenght = TSPUtilities.totalLength(twoOpt.getOptimization());

            if(newLenght < currentLenght){
                currentSolution = twoOpt.getOptimization();
                currentLenght = newLenght;

                if(newLenght < bestSolutionLenght){
                    bestSolutionLenght = newLenght;
                    bestSolution = twoOpt.getOptimization();
                    System.out.println("New best found! Score = "+bestSolutionLenght);
                }
            } else {
                double error = (newLenght - currentLenght);
                if(getProbability(error) > Math.random()){      //error = 0 -> Always same solution!
                    currentSolution = twoOpt.getOptimization();
                }
            }
        }


        System.out.println("Optimization finished");
        this.optimization = bestSolution;
    }

    //Deprecated.
    //We shall use the double bridge with 2 opt variation instead of random guesses.
    private void randomSimulatedAnnealing(){
        int left, right;

        while(System.currentTimeMillis() < endTime){
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
        temperature *= 0.95;
        return Math.pow(Math.E, (-error / temperature));  // e^^(-DE/T)
    }

    private class DoubleBridge{
        public List<City> swapRandomly(List<City> cities){
            ArrayList<City> retval = new ArrayList<City>();

            int[] citiesToSwap = new int[4];


            while(!allDiffer(citiesToSwap, 4)) {
                for (int i = 0; i < citiesToSwap.length; i++)
                    citiesToSwap[i] = (int) (Math.random() * cities.size());
            }
            Arrays.sort(citiesToSwap);


            int temp = 0;

            for(int i = 0; i<cities.size(); i++){
                if(temp == citiesToSwap[0]){
                    temp = citiesToSwap[2] ;
                } else if (temp == citiesToSwap[1]){
                    temp = citiesToSwap[3] ;
                } else if(temp == citiesToSwap[2]){
                    temp = citiesToSwap[0] ;
                } else if (temp == citiesToSwap[3]){
                    temp = citiesToSwap[1] ;
                }

                if(temp+1 == cities.size()) {
                    temp = 0;
                } else {
                    temp++;
                }

                retval.add(cities.get(temp));
            }

            return retval;
        }

        private boolean allDiffer(int[] citiesToSwap, int size) {
            for(int i = 0; i<size; i++){
                for(int j = i+1; j<size; j++){
                    if(citiesToSwap[i] == citiesToSwap[j])
                        return false;
                }
            }

            return true;
        }
    }
}