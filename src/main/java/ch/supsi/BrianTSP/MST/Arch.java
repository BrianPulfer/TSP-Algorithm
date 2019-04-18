package ch.supsi.BrianTSP.MST;

import ch.supsi.BrianTSP.City;

public class Arch {
    private City depart;
    private City arrival;

    public Arch(City city1, City city2) {
        this.depart = city1;
        this.arrival = city2;
    }

    public City getDepart() {
        return depart;
    }

    public void setDepart(City depart) {
        this.depart = depart;
    }

    public City getArrival() {
        return arrival;
    }

    public void setArrival(City arrival) {
        this.arrival = arrival;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Arch){
            if(((Arch) o).depart == this.depart && ((Arch) o).arrival == this.arrival ||
                ((Arch) o).depart == this.arrival && ((Arch) o).arrival == this.depart)
                return true;
        }

        return false;
    }

    public double length(){
        return this.depart.getDistanceFrom(this.arrival);
    }

    public City getOther(City c){
        if(c == this.arrival){
            return this.depart;
        } else if (c == this.depart){
            return this.arrival;
        } else {
            return null;
        }
    }
}
