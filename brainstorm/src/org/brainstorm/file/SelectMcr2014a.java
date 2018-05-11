package org.brainstorm.file;

import javax.swing.*;
import java.io.*;

/**
 * @author Francois Tadel
 */
public class SelectMcr2014a {
    // ===== CONSTRUCTOR =====
    public SelectMcr2014a(){     
    }
    
    // ===== MAIN =====
    public static void main(String[] args) throws Exception {
        // ===== READ $HOME/MATLABROOT.txt =====
        // Build filename
        String userDir = System.getProperty("user.home");
        File confFile = new File(userDir + "/.brainstorm/MATLABROOT83.txt");
        String mcrDir = "";
        // Read file
        if (confFile.isFile()){
            try {
                BufferedReader in = new BufferedReader(new FileReader(confFile));
                String str = in.readLine();
                in.close();
                if ((str != null) && (str.length() != 0)){
                    mcrDir = str;
                }
            } catch (IOException e){
            }
        }

        // ===== SELECT FOLDER =====
        // Set system look&feel
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            
        }
        // Message box
        JOptionPane.showMessageDialog(null, 
                "To execute the Brainstorm binaries, you need to install the\n" +
                "Matlab Compiler Runtime 8.3 on your computer (MCR Matlab R2014a).\n\n" +
                "The MCR is available from the Mathworks website:\n" + 
                "> google \"download matlab mcr\".\n\n" + 
                "Now, select the installation folder of the MCR 8.3. Examples:\n" +
                "    - Linux:  /usr/local/Matlab_Compiler_Runtime/v83\n" +
                "    - Linux:  $HOME/MCR_R2014a\n" + 
                "    - MacOSX: /Applications/MATLAB/MATLAB_Compiler_Runtime/v83\n" +
                "    - Windows: C:\\Program Files\\MATLAB\\MATLAB Compiler Runtime\\v83\n");

        // Show dialog box
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Matlab Compiler Runtime folder (8.3)");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        if (mcrDir.length() > 1){
            fileChooser.setCurrentDirectory(new File(mcrDir));
        }
        int retval = fileChooser.showOpenDialog(null);
        if (retval != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operation canceled by user.");
            System.exit(-1);
        }
        // Get filename
        mcrDir = fileChooser.getSelectedFile().getAbsolutePath();
        if (mcrDir.length() == 0){
            System.exit(-1);
        }
        
        // ===== DETECT MCR =====
        if (!(new File(mcrDir + "/bin/maci64/libnativedl.jnilib")).isFile() &&
            !(new File(mcrDir + "/bin/maci64/libnativedl.dylib")).isFile() &&
            !(new File(mcrDir + "/bin/win32/mcr.dll")).isFile() &&
            !(new File(mcrDir + "/bin/win64/mcr.dll")).isFile() &&   
            !(new File(mcrDir + "/bin/glnx86/libnativedl.so")).isFile() &&  
            !(new File(mcrDir + "/bin/glnxa64/libnativedl.so")).isFile()){
            JOptionPane.showMessageDialog(null, "Not a valid Matlab Compiler Runtime 8.3 installation folder");
            System.exit(-1);
        }
        
        // ===== SAVE RESULT IN FILE =====
        // Create folder if necessary
        try {
            confFile.getParentFile().mkdirs();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Could not create folder: \n" + confFile.getParent());
            System.exit(-1);
        }
        // Write file
        try {
            PrintWriter out = new PrintWriter(new FileWriter(confFile));
            out.println(mcrDir);
            out.close();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Could not create folder: \n" + confFile.getParent());
            System.exit(-1);
        }
        // Success
        System.exit(0);
    }
}