package org.brainstorm.list;

import javax.swing.*;
import java.awt.*;

/**
 * @author Francois Tadel
 */
public class BstProcessListRenderer implements ListCellRenderer {
    protected Font jFont;
    protected int panelHeight;
       
    public BstProcessListRenderer(int fontSize, int panelHeight){
        jFont = new Font("Dialog", java.awt.Font.PLAIN, fontSize);
        this.panelHeight = panelHeight;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        //JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        // Get list item
        BstListItem item = (BstListItem) value;
        // Panel container
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(10, panelHeight));
        // Left label: Name
        JLabel labelLeft = new JLabel(item.getName());
        labelLeft.setOpaque(false);
        labelLeft.setFont(jFont);
        panel.add(labelLeft, BorderLayout.WEST);
        // Right label: Type
        JLabel labelRight = new JLabel(item.getType());
        labelRight.setOpaque(false);
        labelRight.setFont(jFont);
        panel.add(labelRight, BorderLayout.EAST);

        // Selection border
        if (isSelected){
            //renderer.setBorder(label.getBorder());
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(99, 130, 191)),
                                                               BorderFactory.createEmptyBorder(0,11,0,11)));
            panel.setBackground(new Color(184, 207, 229));
            labelLeft.setForeground(new Color(51, 51, 51));
            labelRight.setForeground(new Color(51, 51, 51));
        }else{
            panel.setBorder(BorderFactory.createEmptyBorder(0,12,0,12));
            panel.setBackground(new Color(255,255,255));
            labelLeft.setForeground(new Color(0, 0, 0));
            labelRight.setForeground(new Color(0, 0, 0));
        }
        return panel;
    }
}