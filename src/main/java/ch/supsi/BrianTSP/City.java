package ch.supsi.BrianTSP;

public class City {
    private final int id;
    private double lat;
    private double lon;

    public City(int id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public int getDistanceFrom(City c){
        return (int)(Math.sqrt((this.lat-c.lat)*(this.lat-c.lat) + (this.lon-c.lon)*(this.lon-c.lon)) + 0.5);
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof City){
            if(((City) o).getId() == this.getId())
                return true;
        }

        return false;
    }
}
