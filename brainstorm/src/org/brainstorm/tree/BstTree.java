package org.brainstorm.tree;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.Color;
import javax.swing.plaf.TreeUI;
import java.awt.event.*;
        
/**
 * @author Francois Tadel
 */
public class BstTree extends JTree {
    // "Loading..." node
    BstNode m_nodeLoading = null;
    float interfaceScaling;
    
    // ===== CONSTRUCTOR =====
    public BstTree(float interfaceScaling, int fontSize, String fontName) {
        // Call JTree(treeModel)
        super(new BstTreeModel(new BstNode("root", "Root")));
        // Configure Tree
        putClientProperty("JTree.lineStyle", "Angled");
        setShowsRootHandles(false);
        setRootVisible(false);
        setRowHeight(22);       
        setToolTipText(".");
        // Create cell renderer
        setCellRenderer(new BstCellRenderer(interfaceScaling, fontSize, fontName));
    }

    // ===== CALLED BY SYSTEM WHEN A TOOLTIP IS REQUESTED =====
    public JToolTip createToolTip(){
        JToolTip jtt = new JToolTip();
        jtt.setBackground(new Color(184, 207, 229));
        jtt.setForeground(new Color(51, 51, 51));
	return jtt;
    }
        
    // ===== GET TOOLTIP TEXT =====
    public String getToolTipText(MouseEvent e) {
        BstNode node = null;
        TreePath path = getPathForLocation(e.getX(), e.getY());
        if (path != null) {
            node = (BstNode) path.getLastPathComponent();
        }
        return (node == null) ? null : node.toHtml();
    }
  
    // ===== "LOADING" STATE =====
    public void setLoading(boolean isLoading){
        // Get root node
        DefaultMutableTreeNode nodeRoot = (DefaultMutableTreeNode) this.getModel().getRoot();
        // Set tree in "Loading" mode
        if (isLoading){
            // Remove child nodes
            nodeRoot.removeAllChildren();
            // Add a "Loading..." inactive node
            m_nodeLoading = new BstNode("loading", "Loading...");
            nodeRoot.add(m_nodeLoading);
        }else{
            // Remove "Loading..." node
            if (m_nodeLoading != null){
                nodeRoot.remove(m_nodeLoading);
                m_nodeLoading = null;
            }
        } 
        // Reload tree model
        refresh();
    }
    
    // ===== REFRESH MODEL =====
    public void refresh(){
        // Reload tree model
        ((DefaultTreeModel) this.getModel()).reload();
        // Expand root node
        this.expandRow(0);
    }
    
    // ===== REMOVE =====
    public void removeAllNodes(){
        ((BstNode) this.getModel().getRoot()).removeAllChildren();
        this.refresh();
    }
    public void removeSelectedNodes(){
        // Get selected nodes
        TreePath[] treeSelPaths = this.getSelectionPaths();
        if (treeSelPaths == null){
            return;
        }
        // Delete each selected node
        for (int i=0; i<treeSelPaths.length; i++){
            ((DefaultTreeModel) this.getModel()).removeNodeFromParent((MutableTreeNode) treeSelPaths[i].getLastPathComponent());
        }
        this.refresh();
    }

    // Fix setUI bug
    public void setUI(TreeUI newUI) {
        super.setUI(newUI);
        TransferHandler handler = getTransferHandler();
        setTransferHandler(null);
        setTransferHandler(handler);
    }

    // ===== GET NODES =====
    public BstNode[] getNodes(){
        // Get root
        BstNode root = (BstNode) this.getModel().getRoot();
        // Returned list
        BstNode listNodes[];
        // If no children: return null
        if (root.getChildCount() == 0){
            return null;
        // Else: return the list of all the nodes
        }else{
             listNodes= new BstNode[root.getChildCount()];
             for (int i=0; i<root.getChildCount(); i++){
                 listNodes[i] = (BstNode) root.getChildAt(i);
             }
        }
        return listNodes;
    }

}





