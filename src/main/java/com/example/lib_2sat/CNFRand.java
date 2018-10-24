package com.example.lib_2sat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class CNFRand {
    private List<String> COMMENTS = new ArrayList<>();
    private List<String> ARGUMENTS = new ArrayList<>();
    private List<Integer> A;
    private List<Integer> B;
    private int VARIABLES;
    private int CLAUSES;
    private boolean[] assignments;

    private Random rand = new Random();


    public CNFRand(File f) throws IOException {
        A = new ArrayList<>();
        B = new ArrayList<>();
        B =  new ArrayList<>();
        parseCNF(f);
        VARIABLES = Integer.parseInt(ARGUMENTS.get(1));
        CLAUSES = Integer.parseInt(ARGUMENTS.get(2));

        assignments = new boolean[100000];
        Arrays.fill(assignments, false);

    }

    // This function helps to read the CNF File
    private void parseCNF(File f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {

            // Skip Empty lines
            if (line.isEmpty()){
                continue;
            }
            // Read Comments
            else if (line.charAt(0) == 'c') {
                COMMENTS.add(line.substring(2));
                //System.out.println("COMMENT added");

                // Read Arguments
            } else if (line.charAt(0) == 'p') {
                String[] args = line.split(" ");
                for (int i = 1; i < args.length; i++) {
                    ARGUMENTS.add(args[i]);
                }
            } else {
                String[] args = line.split(" ");
                A.add(Integer.parseInt(args[0]));
                B.add(Integer.parseInt(args[1]));
            }
        }
    }

    public void solve(){
        System.out.println("Solving for " + CLAUSES + " clauses.");
        int flipCount = 0;
        //System.out.println("maxFlips = " + 10*VARIABLES*VARIABLES);
        while(flipCount < 10*VARIABLES*VARIABLES){
            boolean sat = true;
            for(int i=0; i<A.size(); i++){
                int indexA = toIndex(A.get(i));
                int indexB = toIndex(B.get(i));
                boolean valueA = A.get(i) > 0;
                boolean valueB = B.get(i) > 0;
                if(assignments[indexA] != valueA && assignments[indexB] != valueB){
                    flipRandom(indexA, indexB);
                    flipCount ++;
                    //System.out.println("flips = " + flipCount);
                    sat = false;
                }
            }
            if(sat){
                System.out.println("FORMULA SATISFIABLE");
                return;
            }

        }
        System.out.println("FORMULA UNSATISFIABLE");
    }

    private void flipRandom(int indexA, int indexB){
        int n = rand.nextInt(1);
        if(n==0) assignments[indexA] = !assignments[indexA];
        else assignments[indexB] = !assignments[indexB];
    }

    private int toIndex(int var){
        return var > 0 ? var-1 : -var - 1;
    }
}
