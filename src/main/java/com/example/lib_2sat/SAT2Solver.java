package com.example.lib_2sat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAT2Solver {

    public static void main(String args[]) throws IOException {

        File SOLVABLE10 = getFile("SOLVABLE_10.cnf");
        File UNSOLVABLE10 = getFile("UNSOLVABLE_10.cnf");
        File SOLVABLE100 = getFile("SOLVABLE_100.cnf");
        File UNSOLVABLE100 = getFile("UNSOLVABLE_100.cnf");
        File SOLVABLE1000 = getFile("SOLVABLE_1000.cnf");
        File UNSOLVABLE1000 = getFile("UNSOLVABLE_1000.cnf");

        List<CNF> cnfList = new ArrayList<>();
        cnfList.add(new CNF(SOLVABLE10));
        cnfList.add(new CNF(UNSOLVABLE10));
        cnfList.add(new CNF(SOLVABLE100));
        cnfList.add(new CNF(UNSOLVABLE100));
        cnfList.add(new CNF(SOLVABLE1000));
        cnfList.add(new CNF(UNSOLVABLE1000));

        for (CNF cnf : cnfList) {
            long start = System.nanoTime();
            cnf.solve();
            long time = System.nanoTime() - start;
            System.out.println("Time taken: " + time);
        }

        System.out.println(" ============= ");
        List<CNFRand> cnfRandList = new ArrayList<>();
        cnfRandList.add(new CNFRand(SOLVABLE10));
        cnfRandList.add(new CNFRand(UNSOLVABLE10));
        cnfRandList.add(new CNFRand(SOLVABLE100));
        cnfRandList.add(new CNFRand(UNSOLVABLE100));
        cnfRandList.add(new CNFRand(SOLVABLE1000));
        cnfRandList.add(new CNFRand(UNSOLVABLE1000));

        for (CNFRand cnf : cnfRandList) {
            long start = System.nanoTime();
            cnf.solve();
            long time = System.nanoTime() - start;
            System.out.println("Time taken: " + time/1000000.0 + " ms");
        }
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
