package org.brainstorm.list;

import javax.swing.*;
import java.awt.*;

/**
 * @author Martin Cousineau
 */
public class BstHotkeyListRenderer implements ListCellRenderer {
    protected Font jFontText;
    protected Font jFontHotkey;
       
    public BstHotkeyListRenderer(int fontTextSize, int fontHotkeySize){
        this.jFontText = new Font("Dialog", java.awt.Font.PLAIN, fontTextSize);
        this.jFontHotkey = new Font("Dialog", java.awt.Font.PLAIN, fontHotkeySize);
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // Get list item
        BstListItem item = (BstListItem) value;
        // Panel container
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(10, 22));

        // Left label: Name
        JLabel labelLeft = new JLabel(item.getName());
        labelLeft.setOpaque(false);
        labelLeft.setFont(jFontText);
        panel.add(labelLeft, BorderLayout.CENTER);
        // Right label: Type
        JLabel labelRight;
        if ((item.getType() != null) && (item.getType().length() > 0)){
            labelRight = new JLabel("<html><i>" + item.getType() + "</i>");
            labelRight.setOpaque(false);
            labelRight.setFont(jFontHotkey);
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