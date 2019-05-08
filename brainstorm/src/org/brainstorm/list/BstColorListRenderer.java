package org.brainstorm.list;

import javax.swing.*;
import java.awt.*;

/**
 * @author Francois Tadel
 */
public class BstColorListRenderer implements ListCellRenderer {
    protected DefaultListCellRenderer defaultRenderer;
    protected Font jFont;
       
    public BstColorListRenderer(int fontSize){
        defaultRenderer = new DefaultListCellRenderer();
        jFont = new Font("Dialog", java.awt.Font.PLAIN, fontSize);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        BstListItem item = (BstListItem) value;

        // Create panel
        JPanel renderer = new JPanel(new BorderLayout());
        renderer.setBackground(label.getBackground());
        // Item color
        if (item.getColor() != null){
            JLabel labelColor = new JLabel("    ");
            if (list.isEnabled()){
                labelColor.setBackground(item.getColor());
            }else{
                labelColor.setBackground(new Color(150, 150, 150));
            }
            labelColor.setOpaque(true);
            renderer.add(labelColor, BorderLayout.WEST);
        }
        // Item label
        String strText = label.getText();
        JLabel labelText;
        if ((strText.length() > 1) && (strText.startsWith("("))){
            labelText = new JLabel(" " + strText.substring(1));
            labelText.setForeground(new Color(178, 178, 178));
        }else if (list.isEnabled()){
            labelText = new JLabel(" " + strText);
            labelText.setForeground(label.getForeground());
        }else{
            labelText = new JLabel(" " + strText);
            labelText.setForeground(new Color(150, 150, 150));
        }
        labelText.setFont(jFont);
        renderer.add(labelText, BorderLayout.CENTER);


        // Selection border
        //if (isSelected){
        //    renderer.setBorder(label.getBorder());
        //}else{
            renderer.setBorder(null);
        //}

        // Selection arrow
        //if (isSelected){
        //    renderer.add(new JLabel(">"), BorderLayout.EAST);
        //}
        
        // Set color
        //if (item.getColor() != null){
        //     renderer.setBackground(item.getColor());
        //}

        return renderer;
    }
}