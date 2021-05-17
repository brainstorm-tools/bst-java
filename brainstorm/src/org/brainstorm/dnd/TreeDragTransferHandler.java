package org.brainstorm.dnd;

import javax.swing.*;
import java.awt.datatransfer.*;
import org.brainstorm.tree.*;
import java.util.Arrays;

/**
 * @author Francois Tadel
 */
public class TreeDragTransferHandler extends TransferHandler {
    private BstNode [] srcNodes;
    private BstNode destNode;

    // ===== ACCESSORS =====
    public BstNode [] getSrcNodes(){
        return srcNodes;
    }
    public BstNode getDestNode(){
        return destNode;
    }

    // ===== DRAG =====
    public Transferable createTransferable(JComponent cp){
        // Get selected nodes
        BstTree jtree = (BstTree) cp;
        int [] iRows = jtree.getSelectionRows();
        int nbNodes = iRows.length;
        BstNode [] selNodes = new BstNode[nbNodes];
        Arrays.sort(iRows);
        for (int i=0; i<nbNodes; i++){
            selNodes[i] = (BstNode) jtree.getPathForRow(iRows[i]).getLastPathComponent();
        }
        // Create Transferable
        return new TransferableTreeNode(selNodes);
    }

    public int getSourceActions(JComponent c){
        return this.COPY_OR_MOVE;
    }
    
    public void exportDone(JComponent c, Transferable t, int ty){
        
    }

    public void exportToClipboard(JComponent comp, Clipboard clip, int action) {

    }
    
    // ===== DROP =====
    public boolean canImport(JComponent cp, DataFlavor[] df) {
        for (int i = 0; i < df.length; i++) {
            if (df[i].equals(TransferableTreeNode.localDataFlavor)) {
                return true;
            }
        }
        return false;
    }

    public boolean importData(JComponent cp, Transferable df) {
        if (df.isDataFlavorSupported(TransferableTreeNode.localDataFlavor)) {
            try {
                // Get transfered nodes
                this.srcNodes = (BstNode []) df.getTransferData(TransferableTreeNode.localDataFlavor);
                if ((this.srcNodes == null) || (this.srcNodes.length == 0)){
                    return false;
                }
                // Get destination node
                BstTree bstTree;
                if (cp.getClass().getName().compareTo("org.brainstorm.tree.BstTree") == 0){
                    bstTree = (BstTree)cp;
                }else if (cp.getClass().getName().compareTo("javax.swing.JScrollPane") == 0){
                    bstTree = (BstTree) (((JScrollPane) cp).getViewport().getView());
                }else {
                    return false;
                }
                if ((bstTree == null) || (bstTree.getSelectionPath() == null)){
                    return false;
                }
                this.destNode = (BstNode) bstTree.getSelectionPath().getLastPathComponent();
                if (this.destNode == null){
                    return false;
                }
                // Check source node types
                String srcType = this.srcNodes[0].getType().toLowerCase();
                if (!srcType.equalsIgnoreCase("data") &&
                    !srcType.equalsIgnoreCase("datalist") &&
                    !srcType.equalsIgnoreCase("results") &&
                    !srcType.equalsIgnoreCase("kernel") &&
                    !srcType.equalsIgnoreCase("timefreq") &&
                    !srcType.equalsIgnoreCase("matrix") &&
                    !srcType.equalsIgnoreCase("matrixlist") &&
                    !srcType.equalsIgnoreCase("dipoles") &&
                    !srcType.equalsIgnoreCase("channel") &&
                    !srcType.equalsIgnoreCase("noisecov") &&
                    !srcType.equalsIgnoreCase("headmodel") &&
                    !srcType.equalsIgnoreCase("tess") &&
                    !srcType.equalsIgnoreCase("other") &&
                    !srcType.equalsIgnoreCase("cortex") &&
                    !srcType.equalsIgnoreCase("innerskull") &&
                    !srcType.equalsIgnoreCase("outerskull") &&
                    !srcType.equalsIgnoreCase("scalp") &&
                    !srcType.equalsIgnoreCase("anatomy")){
                    return false;
                }
                // Check destination node types
                String destType = this.destNode.getType().toLowerCase();
                if (!destType.equalsIgnoreCase("defaultanat") &&
                    !destType.equalsIgnoreCase("study") &&
                    !destType.equalsIgnoreCase("studysubject") &&
                    !destType.equalsIgnoreCase("defaultstudy") &&
                    !destType.equalsIgnoreCase("condition") &&
                    !destType.equalsIgnoreCase("rawcondition") &&
                    !destType.equalsIgnoreCase("subject") &&
                    !destType.equalsIgnoreCase("folder")){
                    return false;
                }
                // Fire change event
                ((BstTreeModel) bstTree.getModel()).fireDropEvent();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}








