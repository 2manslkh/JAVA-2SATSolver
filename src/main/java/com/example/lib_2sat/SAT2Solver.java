package com.example.lib_2sat;

import java.io.File;
import java.io.IOException;

public class SAT2Solver {

    public static void main(String args[]) throws IOException {

        File SOLVABLE = getFile("SOLVABLE.cnf");
        File UNSOLVABLE = getFile("largeUnsat.cnf");

        CNF SOLVABLE_CNF = new CNF(SOLVABLE);
        CNF UNSOLVABLE_CNF = new CNF(UNSOLVABLE);

        long start = System.nanoTime()/1000;
        SOLVABLE_CNF.solve();
//        UNSOLVABLE_CNF.solve();
        long time = System.nanoTime()/1000 - start;
        System.out.println("Time Taken:" + time + "ms");

    }

    // Helper function to parse the CNF File
    public static File getFile(String filename){
        File input;
        String filedir = "C:\\Users\\kengh\\AndroidStudioProjects\\MyApp01\\lib_2SAT\\src\\main\\java\\com\\example\\lib_2sat";
        String file = filedir + "\\" + filename;
        input = new File(file);
        if (!input.exists()) {
            System.out.println("Cannot Find CNF File.");
            return null;
        }
        return input;
    }
}
