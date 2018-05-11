package org.brainstorm.dnd;

import java.awt.datatransfer.*;
import org.brainstorm.tree.BstNode;
import java.io.IOException;

/**
 * @author Francois Tadel
 */
public class TransferableTreeNode implements Transferable {
    public static DataFlavor localDataFlavor;
    private BstNode bstNodes[];

    // ===== CONSTRUCTOR =====
    public TransferableTreeNode(BstNode _bstNode[]){
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + BstNode.class.getName();
        try {
            //localDataFlavor  = new DataFlavor(mimeType);
            localDataFlavor  = new DataFlavor(BstNode.class, null);
            this.bstNodes = _bstNode;
//        }catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    // ===== GET DATA FLAVORS =====
    public DataFlavor[] getTransferDataFlavors(){
        return new DataFlavor[] {localDataFlavor, DataFlavor.stringFlavor};
    }
    
    // ===== IS FLAVOR SUPORTED =====
    public boolean isDataFlavorSupported(DataFlavor flavor){
        return (flavor.equals(localDataFlavor) || flavor.equals(DataFlavor.stringFlavor));
    }
    
    // ===== GET TRANSFER DATA =====
    public Object getTransferData(DataFlavor flavor) throws IOException, UnsupportedFlavorException{
        if (flavor == null) {
            throw new IOException();
        }
        if (flavor.equals(localDataFlavor)) {
            return bstNodes;
        }
        if (flavor.equals(DataFlavor.stringFlavor)) {
            return bstNodes.toString();
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}






