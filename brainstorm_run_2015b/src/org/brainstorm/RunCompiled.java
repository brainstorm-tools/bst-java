package org.brainstorm;

import bst_javabuilder_2015b.Run;
import java.util.*;

public class RunCompiled {
    public static void main(String[] args) {
        // Input parameters: Copy the command line
        List<String> listIn = new ArrayList<String>();
        for (int i=0; i<args.length; i++){
            listIn.add(args[i]);
        }
        // Output parameters: empty
        List<Double> listOut = new ArrayList<Double>();
        // Execute code
        try{
            Run r = new bst_javabuilder_2015b.Run();
            r.brainstorm(listOut, listIn);
            System.out.println("BST> Compiled code returned without errors.");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
    }
}
