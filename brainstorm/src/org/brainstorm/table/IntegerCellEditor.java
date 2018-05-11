package org.brainstorm.table;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*; 
import java.awt.*;
import java.util.EventObject;

/**
 * @author Francois Tadel
 */
public class IntegerCellEditor implements TableCellEditor {
    // Edition component : TextField
    JTextField editorTextField;
    DefaultCellEditor integerEditor;
    // Backup of the initial value and location in table
    Object initValue;
    int initRow, initCol;
    
    public IntegerCellEditor(){
        editorTextField = new JTextField();
        integerEditor = new DefaultCellEditor(editorTextField);
        integerEditor.setClickCountToStart(2);
    }
    
    // la methode qui renvoie le composant editeur
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
        // Stores initialization values
        initValue = value;
        initRow = row;
        initCol = col;

        if (value == null){
            editorTextField.setText("N/A");
            editorTextField.setHorizontalAlignment(editorTextField.CENTER);
            editorTextField.setEnabled(false);
        }else{
            // Set up editor component
            editorTextField.setText(((Integer)value).toString());
            editorTextField.setHorizontalAlignment(editorTextField.RIGHT);
            editorTextField.setEnabled(true);
        }
        return editorTextField;
    }
    
    public Object getCellEditorValue() {       
        Integer value = parseInputString();
        if (value == null){
            return initValue;
        }else{
            return value;
        }
    }

    public boolean isCellEditable(EventObject anEvent) {
        return integerEditor.isCellEditable(anEvent);
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return integerEditor.shouldSelectCell(anEvent) ;
    }

    public boolean stopCellEditing() {
        Integer value = parseInputString();
        if (value == null){
            editorTextField.setForeground(new Color(0.8f, 0.0f, 0.0f));
            return false;
        } else {
            editorTextField.setForeground(new Color(0.0f, 0.0f, 0.0f));
            return true;
        }
    }

    public void cancelCellEditing() {  
        integerEditor.cancelCellEditing();
    }
    public void addCellEditorListener(CellEditorListener l) {  
        integerEditor.addCellEditorListener(l) ;
    }
    public void removeCellEditorListener(CellEditorListener l) {
        integerEditor.removeCellEditorListener(l) ;
    }

    private Integer parseInputString(){
        // Parse value
        try{
            return Integer.parseInt(editorTextField.getText());
        }catch (NumberFormatException e){
            return null;
        }
    }
}

