package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.ArrayList;
import java.util.List;

public class TabuSearch extends TSPOptimization {

    private ArrayList<Move> tabuList = new ArrayList();
    private final int tenure;
    private final long endTime;
    private final long startTime;

    private class Move{
        private City c1;
        private City c2;

        public Move(City c1, City c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        public boolean equals(Object o){
            if(o instanceof Move){
                if (((Move)o).getC1() == this.c1 && ((Move)o).getC2() == this.c2 ||
                        ((Move)o).getC1() == this.c2 && ((Move)o).getC2() == this.c1 ){
                    return true;
                }
            }

            return false;
        }

        public City getC1() {
            return c1;
        }

        public City getC2() {
            return c2;
        }
    }

    public TabuSearch(List<City> start, int min, int sec) {
        super(start);
        this.tenure = 10;

        startTime = System.currentTimeMillis();
        endTime = startTime + (sec*1000 + min*60*1000);
    }

    public TabuSearch(List<City> start, int tenure, int min, int sec) {
        super(start);
        this.tenure = tenure;

        startTime = System.currentTimeMillis();
        endTime = startTime + (sec*1000 + min*60*1000);
    }

    public void optimize() {
        optimization = start;
        List<City> bestSolution = start;
        int gain = 0;
        int bestGain = 0;

        while(System.currentTimeMillis() < endTime){
            /*
            int[] swapIndexes = TwoOpt.bestSwap(optimization);
            int i = swapIndexes[0];
            int j = swapIndexes[1];
            */
            List<int[]> neighbourhood = bestNeighbourhood(optimization);
            int i = neighbourhood.get(0)[0];
            int j = neighbourhood.get(0)[1];

            gain += TSPUtilities.testSwap(optimization, i, j);

            //If gain goes under 0, we have the best solution ever.
            if(gain < bestGain){
                bestGain = gain;
                bestSolution = TSPUtilities.swap(optimization, i, j);
                optimization = bestSolution;
            } else {
                Move move = new Move(optimization.get(i), optimization.get(j));

                if(inTabuList(move)){
                    gain -= TSPUtilities.testSwap(optimization, i, j);

                    int x = 1;
                    while(!inTabuList(move)) {
                        i = neighbourhood.get(x)[0];
                        j = neighbourhood.get(x)[1];

                        City c1 = optimization.get(i);
                        City c2 = optimization.get(j);

                        move = new Move(c1, c2);
                        x++;
                    }
                }
                    gain += TSPUtilities.testSwap(optimization, i, j);
                    optimization = TSPUtilities.swap(optimization, i, j);
                    addToTabuList(move);
            }
        }

        optimization = bestSolution;
    }

    private void addToTabuList(Move move){
        if(tabuList.size() == tenure){
            tabuList.remove(0);
        }

        tabuList.add(move);
    }

    private boolean inTabuList(Move m){
        for(int i = 0; i<tabuList.size(); i++){
            if(m.equals(tabuList.get(i)))
                return true;
        }

        return false;
    }

    private List<int[]> bestNeighbourhood(List<City> cities){
        ArrayList<int[]> retval = new ArrayList<int[]>();

        for(int i = 0; i < tenure+1; i++){
            int bestGain = Integer.MAX_VALUE;
            int best_j = 0, best_h = 0;

            for(int j = 0; j<cities.size(); j++){
                for(int h = 0; h<cities.size(); h++){

                    if(TSPUtilities.testSwap(cities, j, h) < bestGain){
                        boolean present = false;

                        for(int z = 0; z < retval.size(); z++){
                            if(retval.get(z)[0] == j && retval.get(z)[1] == h ||
                                retval.get(z)[0] == h && retval.get(z)[1] == j){
                                present = true;
                            }
                        }

                        if(!present) {
                            bestGain = TSPUtilities.testSwap(cities, j, h);
                            best_j = j;
                            best_h = h;
                        }
                    }

                }
            }

            int[] pair = {best_j, best_h};
            retval.add(pair);
        }
        return retval;
    }
}
