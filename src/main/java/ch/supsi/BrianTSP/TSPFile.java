package ch.supsi.BrianTSP;

import java.io.*;

public class TSPFile {

    private String filePath;
    private String name;
    private String type;
    private String comment;
    private int dimension;
    private String edge_weight_type;
    private int best;
    private City[] cities;

    public TSPFile(File file){
        if(!file.exists())
            throw new RuntimeException("File does not exist. Could not read.");

        this.filePath = file.getPath();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String current_line;

            while(!(current_line = br.readLine()).equals("NODE_COORD_SECTION")){
                if(current_line.contains("NAME"))
                        this.name = current_line.split(": ")[1];
                if(current_line.contains("COMMENT"))
                        this.comment = current_line.split(": ")[1];
                if(current_line.contains("TYPE") && !current_line.contains("EDGE_WEIGHT_TYPE"))
                        this.type = current_line.split(": ")[1];
                if(current_line.contains("DIMENSION"))
                        this.dimension = Integer.parseInt(current_line.split(": ")[1]);
                if(current_line.contains("BEST_KNOWN"))
                        this.best = Integer.parseInt(current_line.split(": ")[1]);
                if(current_line.contains("EDGE_WEIGHT_TYPE"))
                        this.edge_weight_type = current_line.split(": ")[1];
            }

            cities = new City[dimension];
            int index = 0;

            while(!(current_line = br.readLine()).equals("EOF")){

                //Allowing .tsp files to have a white-space before city ID.
                if(current_line.toCharArray()[0] == ' '){
                    current_line = new StringBuffer(current_line).deleteCharAt(0).toString();
                }

                String id_lat_lon[] = current_line.split(" ");

                int id = Integer.parseInt(id_lat_lon[0]);
                double lat = Double.parseDouble(id_lat_lon[1]);
                double lon = Double.parseDouble(id_lat_lon[2]);

                cities[index] = new City(id, lat, lon);
                index++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File not found.");
        } catch (IOException ioe){
            ioe.printStackTrace();
            System.err.println("File found but couldn't read.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public String getEdge_weight_type() {
        return edge_weight_type;
    }

    public void setEdge_weight_type(String edge_weight_type) {
        this.edge_weight_type = edge_weight_type;
    }

    public int getBest() {
        return best;
    }

    public void setBest(int best) {
        this.best = best;
    }

    public City[] getCities() {
        return cities;
    }

    public void setCities(City[] cities) {
        this.cities = cities;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
