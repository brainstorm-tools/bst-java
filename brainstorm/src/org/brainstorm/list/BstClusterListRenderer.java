package org.brainstorm.list;

import javax.swing.*;
import java.awt.*;

/**
 * @author Francois Tadel
 */
public class BstClusterListRenderer implements ListCellRenderer {
    protected Font jFont;
    protected int width;
       
    public BstClusterListRenderer(int fontSize, int width){
        this.jFont = new Font("Dialog", java.awt.Font.PLAIN, fontSize);
        this.width = width;
    }

    public BstClusterListRenderer(int fontSize){
        this.jFont = new Font("Dialog", java.awt.Font.PLAIN, fontSize);
        this.width = 10;
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        //JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        // Get list item
        BstListItem item = (BstListItem) value;
        // Panel container
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(width, 22));

        // Item color
        if (item.getColor() != null){
            // Container
            JPanel colorPanel = new JPanel();
            colorPanel.setLayout(null);
            colorPanel.setPreferredSize(new Dimension(16,16));
            colorPanel.setOpaque(false);
            panel.add(colorPanel, BorderLayout.WEST);
            // Color
            JLabel labelColor = new JLabel("");
            if (list.isEnabled()){
                labelColor.setBackground(item.getColor());
            }else{
                labelColor.setBackground(new Color(150, 150, 150));
            }
            labelColor.setOpaque(true);
            labelColor.setLocation(0,5);
            labelColor.setSize(12,12);
            colorPanel.add(labelColor);
        }

        // Left label: Name
        JLabel labelLeft = new JLabel(item.getName());
        labelLeft.setOpaque(false);
        labelLeft.setFont(jFont);
        panel.add(labelLeft, BorderLayout.CENTER);
        // Right label: Type
        JLabel labelRight;
        if ((item.getType() != null) && (item.getType().length() > 0)){
            labelRight = new JLabel("[" + item.getType() + "]");
            labelRight.setOpaque(false);
            labelRight.setFont(jFont);
            panel.add(labelRight, BorderLayout.EAST);
        } else {
            labelRight = new JLabel();
        }
        // Selection border
        if (!list.isEnabled()){
            panel.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
            panel.setBackground(new Color(255,255,255));
            labelLeft.setForeground(new Color(150, 150, 150));
            labelRight.setForeground(new Color(150, 150, 150));
        }else if (isSelected){
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(99, 130, 191)),
                                                               BorderFactory.createEmptyBorder(0,4,0,4)));
            panel.setBackground(new Color(184, 207, 229));
            labelLeft.setForeground(new Color(51, 51, 51));
            labelRight.setForeground(new Color(51, 51, 51));
        }else{
            panel.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
            panel.setBackground(new Color(255,255,255));
            labelLeft.setForeground(new Color(0, 0, 0));
            labelRight.setForeground(new Color(0, 0, 0));
        }
        return panel;
    }
}