import ch.supsi.BrianTSP.Main;
import org.junit.Test;

import java.io.File;

public class SolverTest {

    public void maintest(String fileName) {
        String filepath = new File(fileName).getAbsolutePath().replace(fileName, "") + "src/main/resources/"+fileName+".tsp";

        Solver solver = new Solver(filepath);
        solver.solve();
    }


    @Test(timeout = 181000)
    public void ch130() { maintest("ch130"); }

    @Test(timeout = 181000)
    public void d198() {
        maintest("d198");
    }

    @Test(timeout = 181000)
    public void eil76() { maintest("eil76"); }

    @Test(timeout = 181000)
    public void fl1577() {
        maintest("fl1577"); }

    @Test(timeout = 181000)
    public void kroA100() {
        maintest("kroA100");
    }

    @Test(timeout = 181000)
    public void lin318() {
        maintest("lin318");
    }

    @Test(timeout = 181000)
    public void pcb442() {
        maintest("pcb442");
    }

    @Test(timeout = 181000)
    public void pr439() {
        maintest("pr439");
    }

    @Test(timeout = 181000)
    public void rat783() {
        maintest("rat783");
    }

    @Test(timeout = 181000)
    public void u1060() {
        maintest("u1060");
    }
}

