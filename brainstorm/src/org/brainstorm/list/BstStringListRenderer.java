package org.brainstorm.list;

import javax.swing.*;
import java.awt.*;

/**
 * @author Francois Tadel
 */
public class BstStringListRenderer implements ListCellRenderer {
    protected DefaultListCellRenderer defaultRenderer;
    protected Font jFont;
       
    public BstStringListRenderer(int fontSize){
        defaultRenderer = new DefaultListCellRenderer();
        jFont = new Font("Dialog", java.awt.Font.PLAIN, fontSize);
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setFont(jFont);
        return label;
    }
}
