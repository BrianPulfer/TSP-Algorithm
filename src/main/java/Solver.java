import ch.supsi.BrianTSP.Main;

public class Solver {
    private String filePath;

    public Solver(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void solve(){
        String[] args = {filePath};
        Main.main(args);
    }
}
