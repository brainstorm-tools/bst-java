package org.brainstorm.table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;

/**
 * @author Francois Tadel
 */
public class IntegerCellRenderer implements TableCellRenderer{
    DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
         JLabel l = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        
         if (value == null){
             l.setText("N/A");
             l.setHorizontalAlignment(l.CENTER);
             l.setEnabled(false);

         }else{
             l.setText(((Integer)value).toString());
             l.setHorizontalAlignment(l.RIGHT);
             l.setEnabled(true);
         }
         
         return l;
    }
}


