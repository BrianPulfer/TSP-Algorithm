package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.MST.Arch;
import ch.supsi.BrianTSP.MST.Prim;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.*;

public class SimulatedAnnealing extends TSPOptimization {

    private double temperature = 100;
    private double startTime;
    private double endTime;

    private List<City> currentSolution;
    private List<City> bestSolution;

    private Prim prim = null;
    private City[][] closestCitiesMatrix = null;

    private Random random;

    private int bestSolutionLenght;

    public SimulatedAnnealing(List<City> start, int min, int seconds) {
        super(start);
        currentSolution = start;
        bestSolution = currentSolution;
        bestSolutionLenght = TSPUtilities.totalLength(bestSolution);

        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis() + min*60*1000 + seconds*1000;

        this.random = TSPUtilities.getRandom();
    }

    public SimulatedAnnealing(List<City> start, Prim prim, int min, int seconds) {
        super(start);
        currentSolution = start;
        bestSolution = currentSolution;
        bestSolutionLenght = TSPUtilities.totalLength(bestSolution);

        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis() + min*60*1000 + seconds*1000;

        this.random = TSPUtilities.getRandom();
        this.prim = prim;
        this.prim.find();
        this.closestCitiesMatrix = TSPUtilities.getMinDistances(start,16);
    }


    public void optimize() {
        DoubleBridge db = new DoubleBridge();

        //City[][] minDistances = TSPUtilities.getMinDistances(start, 15);

        int currentLenght = TSPUtilities.totalLength(currentSolution);


        while (System.currentTimeMillis() < endTime){

            TwoOpt twoOpt;

            if(this.prim == null) {
                twoOpt = new TwoOpt(db.swapRandomly(currentSolution));
            } else{
                twoOpt = new TwoOpt(db.swapRandomly(currentSolution), this.closestCitiesMatrix, this.prim);
            }

            twoOpt.optimize();

            int newLenght = TSPUtilities.totalLength(twoOpt.getOptimization());

            if(newLenght < currentLenght){
                currentSolution = twoOpt.getOptimization();
                currentLenght = newLenght;

                if(newLenght < bestSolutionLenght){
                    bestSolutionLenght = newLenght;
                    bestSolution = twoOpt.getOptimization();
                    //System.out.println("New best found! Score = "+bestSolutionLenght);
                }
            } else {
                double error = (newLenght - currentLenght);
                if(getProbability(error) > random.nextDouble()){      //error = 0 -> Always same solution!
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

        while(System.currentTimeMillis() < endTime){
            do {
                left = (int) (this.random.nextDouble() * currentSolution.size());
                right = (int) (random.nextDouble() * currentSolution.size());
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
                if(getProbability(deltaError) > random.nextDouble()){
                    currentSolution = TSPUtilities.swap(currentSolution, left, right);
                }
            }
        }

        this.optimization = bestSolution;
    }

    private double getProbability(double error){
        temperature = ((endTime - System.currentTimeMillis()) / (endTime - startTime));
        return Math.pow(Math.E, (-error / temperature));  // e^^(-DE/T)
    }

    private class DoubleBridge{
        private Random random;
        public DoubleBridge(){
            this.random = TSPUtilities.getRandom();
        }

        public List<City> swapRandomly(List<City> cities){
            ArrayList<City> retval = new ArrayList<City>();

            int[] citiesToSwap = new int[4];


            while(!allDiffer(citiesToSwap, 4)) {
                for (int i = 0; i < citiesToSwap.length; i++)
                    citiesToSwap[i] = (int) (random.nextDouble() * cities.size());
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