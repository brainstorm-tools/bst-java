package org.brainstorm.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.event.CellEditorListener; 
import java.awt.Component;
import java.util.EventObject;

/**
 * @author Francois Tadel
 */
public class DisabledCellEditor implements TableCellEditor {
    JLabel l;
    public DisabledCellEditor() {
    }

    // la m�thode qui renvoie le composant �diteur
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
        return l;
    }

    public Object getCellEditorValue() {
        return null ;
    }
    public boolean isCellEditable(EventObject anEvent) {
        return false;
    }
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
    public boolean stopCellEditing() {
        return true ; // pour pouvoir recommencer une �dition
    }
    public void cancelCellEditing() {  
    }
    public void addCellEditorListener(CellEditorListener l) {  
    }
    public void removeCellEditorListener(CellEditorListener l) {
    }
}

