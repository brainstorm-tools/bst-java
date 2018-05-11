package org.brainstorm.table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;

/**
 * @author Francois Tadel
 */
public class ArrayCellRenderer implements TableCellRenderer{
    DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
         JLabel l = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        
         if (value == null){
             l.setText("N/A");
             l.setHorizontalAlignment(l.CENTER);
             l.setEnabled(false);

         }else{
             StringBuffer compString = new StringBuffer("[");
             Object compArray[] = (Object []) value;
             for (int i=0; i<compArray.length; i++){
                 if (compArray[i].getClass().equals(java.lang.Double.class)){
                    compString.append(String.format("%3.2f", ((java.lang.Double) compArray[i])));
                    if (i != compArray.length - 1){
                        compString.append("; ");
                    }
                 }
             }
             compString.append("]");

             l.setText(compString.toString());
             l.setHorizontalAlignment(l.LEFT);
             l.setEnabled(true);
         }
         
         return l;
    }
}


