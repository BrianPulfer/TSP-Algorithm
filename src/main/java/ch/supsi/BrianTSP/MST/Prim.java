package ch.supsi.BrianTSP.MST;

import ch.supsi.BrianTSP.City;
import ch.supsi.BrianTSP.TSPAlgorithm.TSPUtilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


//Prim's Minimum spanning tree algorithm
public class Prim extends MinimumSpanningTree{

    private ArrayList<Arch>[] dataStructure = null;

    public Prim(List<City> start) {
        super(start);
        this.dataStructure = new ArrayList[start.size()];

        for(int i = 0; i<dataStructure.length; i++){
            dataStructure[i] = new ArrayList<Arch>();
        }
    }

    public List<Arch> find() {
        List<Arch> retval = new ArrayList<Arch>();

        List<City> notInTree = new ArrayList(start);
        List<City> inTree = new ArrayList<City>();

        City startingCity = notInTree.get((int) (TSPUtilities.getRandom().nextDouble()*start.size()));

        inTree.add(startingCity);
        notInTree.remove(startingCity);

        int retValSize = start.size()-1;

        while(retval.size() < retValSize){
            Arch next = null;
            double shortestLenght = Double.MAX_VALUE;

            for(City c : inTree){
                Arch current = shortestFromCity(c, notInTree);

                if(current.length() < shortestLenght){
                    shortestLenght = current.length();
                    next = current;
                }
            }

            retval.add(next);
            inTree.add(next.getArrival());
            notInTree.remove(next.getArrival());
        }

        fill(retval);
        return retval;
    }

    private void fill(List<Arch> retval) {
        for(Arch a : retval){
            dataStructure[a.getDepart().getId()-1].add(a);
            dataStructure[a.getArrival().getId()-1].add(a);
        }
    }

    public List<Arch> getMSTArchesFrom(City c){
        return dataStructure[c.getId()-1];
    }


    private Arch findShortestArch() {
        Arch arch = new Arch(start.get(0), start.get(1));
        Arch newArch;

        for(int i = 0; i<start.size(); i++){
            newArch = shortestFromCity(start.get(i), this.start);
            if(newArch.length() < arch.length()){
                arch = newArch;
            }
        }

        return arch;
    }

    private Arch shortestFromCity(City c1, List<City> cities){
        int shortestDistance = Integer.MAX_VALUE;
        int city = -1;

        for(int i = 0; i<cities.size(); i++){
            City nextCity = cities.get(i);

            if(nextCity != c1) {
                int newDistance = cities.get(i).getDistanceFrom(c1);

                if (newDistance < shortestDistance) {
                    shortestDistance = newDistance;
                    city = i;
                }
            }
        }

        return new Arch(c1, cities.get(city));
    }
}
