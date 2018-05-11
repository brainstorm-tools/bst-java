package org.brainstorm.list;

import javax.swing.*;
import java.awt.*;

/**
 * @author Francois Tadel
 */
public class BstCheckListRenderer implements ListCellRenderer {
    protected DefaultListCellRenderer defaultRenderer;
    protected Font jFont;
    protected boolean isRenderSelection;
    
    public BstCheckListRenderer(int fontSize, boolean isRenderSelection){
        this.defaultRenderer = new DefaultListCellRenderer();
        this.jFont = new Font("Dialog", java.awt.Font.PLAIN, fontSize);
        this.isRenderSelection = isRenderSelection;
    }
    
    public BstCheckListRenderer(int fontSize){
        this.defaultRenderer = new DefaultListCellRenderer();
        this.jFont = new Font("Dialog", java.awt.Font.PLAIN, fontSize);
        this.isRenderSelection = true;
    }

    public void setRenderSelection(boolean isRenderSelection){
        this.isRenderSelection = isRenderSelection;
    }
    public boolean getRenderSelection(){
        return this.isRenderSelection;
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // Cancel selection
        if (!isRenderSelection){
            isSelected = false;
        }
        // Get default rendered object
        JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        BstListItem item = (BstListItem) value;
        int status = ((Integer) item.getUserData()).intValue();
        // Create panel
        JPanel renderer = new JPanel(new BorderLayout());
        renderer.setBackground(label.getBackground());
        // Item checkbox
        JCheckBox check;
        if (status > 0){
            check = new JCheckBox(label.getText(), true);
        }else{
            check = new JCheckBox(label.getText(), false);
        }
        if ((status == 2) || (!list.isEnabled())){
            check.setEnabled(false);
        }
        check.setForeground(label.getForeground());
        check.setOpaque(false);
        check.setFont(jFont);
        renderer.add(check, BorderLayout.CENTER);
        

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