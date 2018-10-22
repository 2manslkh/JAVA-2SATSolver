package com.example.lib_2sat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class CNF {
    private List<String> COMMENTS = new ArrayList<>();
    private List<String> ARGUMENTS = new ArrayList<>();
    private List<Integer> A = new ArrayList<>();
    private List<Integer> B =  new ArrayList<>();
    private int VARIABLES;
    private int CLAUSES;
    private HashMap<Integer, List<Integer>> adj = new HashMap<>();
    private HashMap<Integer, List<Integer>> adjinv = new HashMap<>();
    private boolean [] visited;
    private boolean [] visitedinv;
    private Stack<Integer> stack = new Stack<>();

    private int [] scc;
    private int counter;

    // Clause Iterator count
    private int k = 0;

    public CNF(File f) throws IOException {
        parseCNF(f);
        VARIABLES = Integer.parseInt(ARGUMENTS.get(1));
        CLAUSES = Integer.parseInt(ARGUMENTS.get(2));
        visited = new boolean[10000];
        visitedinv = new boolean[10000];

        scc = new int[10000];
        counter = 1;
    }

    public List<String> getCOMMENTS() {
        return COMMENTS;
    }

    public List<String> getARGUMENTS() {
        return ARGUMENTS;
    }

    public List<Integer> getA() {
        return A;
    }

    public List<Integer> getB() {
        return B;
    }

    private void parseCNF(File f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {

            // Read Comments
            if (line.charAt(0) == 'c') {
                COMMENTS.add(line.substring(2));
                System.out.println("COMMENT added");

            // Read Arguments
            } else if (line.charAt(0) == 'p') {
                String[] args = line.split(" ");
                for (int i = 1; i < args.length; i++) {
                    ARGUMENTS.add(args[i]);
                }
            } else {
                String[] args = line.split(" ");
//                System.out.println(line);
                A.add(Integer.parseInt(args[0]));
                B.add(Integer.parseInt(args[1]));
            }
        }
    }

    public void solve(){

        // Add edges to graph
        for (int i = 0; i < CLAUSES; i++){
            if (A.get(i) > 0 && B.get(i) > 0){
                addEdges(A.get(i) + VARIABLES, B.get(i));
                addEdgesInv(A.get(i)+ VARIABLES, B.get(i));
                addEdges(B.get(i) + VARIABLES, A.get(i));
                addEdgesInv(B.get(i) + VARIABLES, A.get(i));
            } else if (A.get(i) > 0 && B.get(i) < 0){
                addEdges(A.get(i) + VARIABLES, VARIABLES - B.get(i));
                addEdgesInv(A.get(i)+  VARIABLES, VARIABLES - B.get(i));
                addEdges(-B.get(i), A.get(i));
                addEdgesInv(-B.get(i), A.get(i));
            } else if (A.get(i) < 0 && B.get(i) > 0){
                addEdges(-A.get(i), B.get(i));
                addEdgesInv(-A.get(i), B.get(i));
                addEdges(B.get(i) + VARIABLES, VARIABLES - A.get(i));
                addEdgesInv(B.get(i) + VARIABLES, VARIABLES - A.get(i));
            } else {
                addEdges(-A.get(i), VARIABLES - B.get(i));
                addEdgesInv(-A.get(i), VARIABLES - B.get(i));
                addEdges(-B.get(i), VARIABLES - A.get(i));
                addEdgesInv(-B.get(i), VARIABLES - A.get(i));
            }
        }

        // STEP 1 of Kosaraju's Algorithm which
        // Perform DFS on the original graph
        for (int i = 1; i<=2*CLAUSES; i++){
            if (!visited[i]){
                dfs1(i);
            }
        }

        // STEP 2 of Kosaraju's Algorithm
        // Perform DFS on the inverse graph.
        // This will identify the Strongly Connected Chains
        while (stack.size() != 0){
            int n = stack.peek();
            stack.pop();

            if (!visitedinv[n]) {
                dfs2(n);
                counter++;
            }
        }

        for (int i=1;i<=VARIABLES;i++) {
            if(scc[i]==scc[i+VARIABLES] && scc[i] != 0) {
                // THE FORMULA IS UNSATISFIABLE AS THERE EXIST -X and X in the SAME SCC
                System.out.println("FORMULA UNSATISFIABLE");
                return;
            }
        }
        // THE FORMULA IS SATISFIABLE AS THERE DO NOT EXIST -X and X in the SAME SCC
        System.out.println("FORMULA SATISFIABLE");

    }

    private void addEdges(Integer a, Integer b){
        if (adj.get(a) == null) { //gets the value for an id)
            adj.put(a, new ArrayList<Integer>()); //no ArrayList assigned, create new ArrayList
        }
        adj.get(a).add(b);
    }

    private void addEdgesInv(int a, int b){
        if (adjinv.get(b) == null) {
            adjinv.put(b, new ArrayList<Integer>());
        }
        adjinv.get(b).add(a);
    }

    private void dfs1(int u) {
        if (visited[u] || adj.get(u) == null){
            return;
        }
        visited[u] = true;
        for (int i = 0; i < adj.get(u).size(); i++){
            System.out.println(adj.get(u));
            dfs1(adj.get(u).get(i));
        }
        stack.push(u);
    }

    private void dfs2(int u){
        if (visitedinv[u] || adjinv.get(u) == null){
            return;
        }
        visitedinv[u] = true;
        for (int i = 0;  i < adjinv.get(u).size(); i++){
            dfs2(adjinv.get(u).get(i));

            // STORE SCC NUMBER of VERTEX/VARIABLE
            scc[u] = counter;
        }

    }

}
