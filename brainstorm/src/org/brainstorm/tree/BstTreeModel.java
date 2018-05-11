package org.brainstorm.tree;

import javax.swing.tree.*;

/**
 * @author Francois Tadel
 */
public class BstTreeModel extends DefaultTreeModel{
    public BstTreeModel(TreeNode root){
        super(root);
    }

    public void fireDropEvent(){
        Object []path = new Object[1];
        path[0] = root;
        this.fireTreeNodesChanged(root, path, null, null);
    }
}
