package org.brainstorm.table;

import org.brainstorm.icon.IconLoader;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;
import java.awt.event.MouseEvent;

/**
 * @author Francois Tadel
 */
public class BooleanCellRenderer implements TableCellRenderer{
    DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
    
    // Variables auxiliaires
    JTable laTable; 
    int i, j;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
        laTable = table;
        i = row;
        j = col;

        final JLabel l = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        
            l.addMouseListener(new java.awt.event.MouseListener() {
            public void mouseClicked(MouseEvent e) {
                boolean oldValue = ((Boolean) laTable.getModel().getValueAt(i, j)).booleanValue();
                laTable.getModel().setValueAt(new Boolean(!oldValue), i, j);
                // Redraw button
                if(!oldValue) 
                    l.setIcon(IconLoader.ICON_CHANNEL_GOOD);
                else                                
                    l.setIcon(IconLoader.ICON_CHANNEL_BAD);
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
        });
    
         
         l.setText("");
         l.setHorizontalAlignment(JLabel.CENTER);
         if(((Boolean)value).booleanValue()) 
             l.setIcon(IconLoader.ICON_CHANNEL_GOOD);
         else                                
             l.setIcon(IconLoader.ICON_CHANNEL_BAD);

         return l;
    }
}




