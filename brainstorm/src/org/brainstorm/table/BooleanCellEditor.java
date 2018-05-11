package org.brainstorm.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.event.CellEditorListener; 
import java.awt.Component;
import java.util.EventObject;

/**
 * @author Francois Tadel
 */
public class BooleanCellEditor implements TableCellEditor {
    JCheckBox jchk = new JCheckBox();
    DefaultCellEditor defaultCellEditor = new DefaultCellEditor(jchk);
    
    // la m�thode qui renvoie le composant �diteur
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
        Component c = defaultCellEditor.getTableCellEditorComponent(table, value, isSelected, row, col);
        c.setVisible(false);
        
        if(((Boolean)value).booleanValue()) {
            jchk.setSelected(true);
        } else {                              
            jchk.setSelected(false);
        }
        return c;
    }

   
    public Object getCellEditorValue() {
        return defaultCellEditor.getCellEditorValue();
    }
    public boolean isCellEditable(EventObject anEvent) {
        return defaultCellEditor.isCellEditable(anEvent);  
    }
    public boolean shouldSelectCell(EventObject anEvent) {
        return defaultCellEditor.shouldSelectCell(anEvent);  
    }
    public boolean stopCellEditing() {
        return defaultCellEditor.stopCellEditing();  
    }
    public void cancelCellEditing() {  
        defaultCellEditor.cancelCellEditing();  
    }
    public void addCellEditorListener(CellEditorListener l) {  
        defaultCellEditor.addCellEditorListener(l);
    }
    public void removeCellEditorListener(CellEditorListener l) {
        defaultCellEditor.removeCellEditorListener(l);
    }
}




