package org.brainstorm;

import bst_javabuilder_2016b.Run;
import java.util.*;

public class RunCompiled {
    public static void main(String[] args) {
        // Input and output parameters: empty
        List<Double> list1 = new ArrayList<Double>();
        List<Double> list2 = new ArrayList<Double>();
        // Execute code
        try{
            Run r = new bst_javabuilder_2016b.Run();
            r.brainstorm(list1, list2);
            System.out.println("BST> Compiled code returned without errors.");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
    }
}
