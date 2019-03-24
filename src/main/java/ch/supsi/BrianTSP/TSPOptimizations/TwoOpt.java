package ch.supsi.BrianTSP.TSPOptimizations;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.*;

public class TwoOpt extends TSPOptimization {

    public TwoOpt(List<City> start) {
        super(start);
    }

    public void optimize() {
        optimization = start;

        boolean optimized;

        do {
            optimized = false;
            int best_i = -1, best_j = -1;

            int bestGain = 0;

            for (int i = 0; i < start.size(); i++) {
                for (int j = i + 1; j < start.size(); j++) {

                    int gain = TSPUtilities.testSwap(optimization, i, j);

                    if (gain < bestGain) {
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
}
