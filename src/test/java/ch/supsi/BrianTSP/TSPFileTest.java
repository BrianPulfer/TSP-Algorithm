package ch.supsi.BrianTSP;

import ch.supsi.BrianTSP.TSPFile;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TSPFileTest {
    private TSPFile sut;
    private final String filepath = "/Users/brianpulfer21/Desktop/Semestre Primaverile/Algoritmi avanzati/Coppa Algoritmi/TSP-BrianPulfer/src/maintest/resources/TestFile1.tsp";


    @Before
    public void init(){
        sut = new TSPFile(new File(filepath));
    }

    @Ignore
    @Test
    public void construction(){
        assertEquals("ch130", sut.getName());
        assertEquals("TSP", sut.getType());
        assertEquals("130 city problem (Churritz)", sut.getComment());
        assertEquals(130, sut.getDimension());
        assertEquals("EUC_2D", sut.getEdge_weight_type());
        assertEquals(6110, sut.getBest());
        assertEquals(sut.getDimension(), sut.getCities().length);
    }

}