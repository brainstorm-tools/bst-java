package org.brainstorm.file;

import javax.swing.*;
import java.io.File;

/**
 * @author Francois Tadel
 */
public class BstFileSelector{
    // Define dialog types
    public static final int TYPE_OPEN = 1;
    public static final int TYPE_SAVE = 2;
    public static final int SELECTION_SINGLE = 3;
    public static final int SELECTION_MULTIPLE = 4;
    public static final int FILES_ONLY = 5;
    public static final int DIRECTORIES_ONLY = 6;
    public static final int FILES_AND_DIRECTORIES = 7;

    JFileChooser jChooser;
    int dialogType;
    String defaultFile;
    
    public BstFileSelector( int dialogType, String windowTitle, String defaultFile, 
                         int selectionMode, int filesOrDir, BstFileFilter[]filters, int iDefaultFilter){
        // Instantiate file chooser
        
        jChooser = new JFileChooser();
        jChooser.setAcceptAllFileFilterUsed(false);
        // Properties (strings)
        jChooser.setDialogTitle(windowTitle);
        this.defaultFile = defaultFile;
        // Properties (enum)
        switch (selectionMode){
            case SELECTION_MULTIPLE:
                jChooser.setMultiSelectionEnabled(true);
                break;
            case SELECTION_SINGLE:
                jChooser.setMultiSelectionEnabled(false);
                break;
        }
        switch (filesOrDir){
            case FILES_ONLY: 
                jChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                break;
            case DIRECTORIES_ONLY:
                jChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                break;
            case FILES_AND_DIRECTORIES:
                jChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                break;
        }
        this.dialogType = dialogType;
        // Filters
        if (filters != null){
            for (int i=0; i<filters.length; i++){
                jChooser.addChoosableFileFilter(filters[i]);
            }
            jChooser.setFileFilter(filters[iDefaultFilter]);
        }
    }
    
    
    public void showNewThread(){
        // Display dialog in a new thread
        Thread indepDialog = new Thread(new Runnable(){
            public void run() {
                switch(dialogType){
                    case TYPE_OPEN:
                        jChooser.setCurrentDirectory(new File(defaultFile));
                        jChooser.showOpenDialog(null);
                        break;
                    case TYPE_SAVE:
                        jChooser.setSelectedFile(new File(defaultFile));
                        jChooser.showSaveDialog(null);
                        break;
                }
            }
        });
        indepDialog.start();
    }
    
    public void showSameThread(){
        // Display dialog in the current thread
        switch(dialogType){
            case TYPE_OPEN:
                jChooser.setCurrentDirectory(new File(defaultFile));
                jChooser.showOpenDialog(null);
                break;
            case TYPE_SAVE:
                jChooser.setSelectedFile(new File(defaultFile));
                jChooser.showSaveDialog(null);
                break;
        }
    }
    
    public JFileChooser getJFileChooser(){
        return this.jChooser;
    }
}
