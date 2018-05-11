package org.brainstorm.list;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * @author Francois Tadel
 */
public class BstClusterList extends JList {
    // CONSTRUCTOR
    public void BstClusterList(int fontSize){
        // Set renderer
        this.setCellRenderer(new BstClusterListRenderer(fontSize));
    }    

    // TOOLTIP
    public String getToolTipText(MouseEvent evt) {
        // Get item index
        int index = locationToIndex(evt.getPoint());
        if (index == -1){
            return null;
        }
        // Get item
        Object item = getModel().getElementAt(index);
        // Return the tool tip text
        return item.toString();
    }
}
