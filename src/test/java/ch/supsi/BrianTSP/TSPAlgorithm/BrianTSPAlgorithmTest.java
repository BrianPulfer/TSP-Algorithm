package ch.supsi.BrianTSP.TSPAlgorithm;

import ch.supsi.BrianTSP.TSPFile;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class BrianTSPAlgorithmTest {

    private BrianTSPAlgorithm sut;
    private double scanner_width = 10/100;
    private TSPFile testfile;
    private String testfile_path = "/Users/brianpulfer21/Desktop/Semestre Primaverile/Algoritmi avanzati/Coppa Algoritmi/TSP-BrianPulfer/src/main/resources/TestFile1.tsp";;

    @Before
    public void init(){
        sut = new BrianTSPAlgorithm(scanner_width);
        testfile = new TSPFile(new File(testfile_path));
    }

    @Test
    public void constructor(){
        try {
            //Construction works with every value between 0 and 1.
            for (double i = 0; i < 1; i += 0.1)
                assertTrue(new BrianTSPAlgorithm(i) != null);

            BrianTSPAlgorithm impossible = new BrianTSPAlgorithm(1);
            fail();
        }catch (Exception e){
        }
    }

    @Test
    public void compute() {
        sut.compute(testfile);

        assertTrue(sut.citiesFinalOrder() != null);
        assertTrue(sut.citiesFinalOrder().length == testfile.getCities().length);
        assertTrue(sut.getTotalDistance() > 0);
    }
}