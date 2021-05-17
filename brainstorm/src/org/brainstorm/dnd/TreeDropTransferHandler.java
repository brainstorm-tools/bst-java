package org.brainstorm.dnd;

import javax.swing.*;
import java.awt.datatransfer.*;
import org.brainstorm.tree.*;
import java.awt.*;

/**
 * @author Francois Tadel
 */
public class TreeDropTransferHandler extends TransferHandler {
    
    // ===== CAN IMPORT ? =====
    public boolean canImport(JComponent cp, DataFlavor[] df) {
        for (int i = 0; i < df.length; i++) {
            if (df[i].equals(TransferableTreeNode.localDataFlavor)) {
                return true;
            }
        }
        return false;
    }
    
    // ===== IMPORT =====
    public boolean importData(JComponent cp, Transferable df) {
        if (df.isDataFlavorSupported(TransferableTreeNode.localDataFlavor)) {
            try {
                BstNode nodeRoot = null;
                BstTreeTable treeTable = null;
                // Get transfered node
                BstNode [] bstNodes = (BstNode []) df.getTransferData(TransferableTreeNode.localDataFlavor);
                if (bstNodes == null){
                    return false;
                }
                // Get data model
                if (cp.getClass().getName().compareTo("org.brainstorm.tree.BstTree") == 0){
                    nodeRoot = (BstNode) ((BstTree)cp).getModel().getRoot();
                }else if (cp.getClass().getName().compareTo("org.brainstorm.tree.BstTreeTable") == 0){
                    treeTable = (BstTreeTable)cp;
                }else if (cp.getClass().getName().compareTo("javax.swing.JScrollPane") == 0){
                    JScrollPane jScroll = (JScrollPane) cp;
                    treeTable = (BstTreeTable) (jScroll.getViewport().getView());
                }else {
                    return false;
                }
                // Add list items
                for (int i=0; i<bstNodes.length; i++){
                    // Check node type
                    BstNode newNode = (BstNode) bstNodes[i].clone();
                    String type = newNode.getType().toLowerCase();
                    
                    // Accept
                    if (type.equals("studydbsubj") || 
                        type.equals("studydbcond") || 
                        type.equals("study") || 
                        type.equals("studysubject") || 
                        type.equals("data") || 
                        type.equals("datalist") ||
                        type.equals("results") || 
                        type.equals("resultslist") || 
                        type.equals("link") ||
                        type.equals("timefreq") ||
                        type.equals("spectrum") ||
                        type.equals("matrix") ||
                        type.equals("matrixlist") ||
                        type.equals("pdata") || 
                        type.equals("presults") || 
                        type.equals("ptimefreq") ||
                        type.equals("pspectrum") ||
                        type.equals("pmatrix") ||
                        type.equals("rawdata") || 
                        type.equals("rawcondition")) {
                        // Remove [ ] blocks
                        newNode.setComment(strRemoveParenth(newNode.getComment(), '['));
                    }else if (type.equals("condition") || type.equals("folder")) {
                        // Condition: set condition path as comment
                        newNode.setComment(newNode.getFileName());
                    }else{
                        return false;
                    }
                    
                    // Set node properties
                    newNode.setMarked(false);
                    newNode.setComment(newNode.getComment() + "      ");
                    // Add node
                    if (nodeRoot != null){
                        nodeRoot.add(newNode);
                    }else{
                        treeTable.add(newNode);
                    }
                }
                // Update tree display
                if (nodeRoot != null){
                    ((BstTree)cp).refresh();
                }else{
                    treeTable.refresh();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        return false;
    }
    
    private String strRemoveParenth(String s, char parType){
        char chOpen, chClose;
        // Get opening/closing parenthesis
        if (parType == '('){
            chOpen = '('; chClose = ')';
        }else if (parType == '['){
            chOpen = '['; chClose = ']';
        }else if (parType == '{'){
            chOpen = '{'; chClose = '}';
        } else {
            return s;
        }
        // Find the occurrences of opening and closing parethesis
        int iParOpen  = s.indexOf(chOpen);
        int iParClose = s.indexOf(chClose, iParOpen);
        // Remove as long as they are things to remove
        while ((iParOpen != -1) && (iParClose != -1)){
            if (iParClose < s.length()){
                s = s.substring(0,iParOpen) + s.substring(iParClose+1);
            } else {
                s = s.substring(0,iParOpen);
            }
            iParOpen  = s.indexOf(chOpen);
            iParClose = s.indexOf(chClose, chOpen);
        }                
        return s.trim();
    }
}


    
    
    
    
    
    
    
    