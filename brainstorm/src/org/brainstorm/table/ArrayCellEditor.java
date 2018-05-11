package org.brainstorm.table;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*; 
import java.awt.*;
import java.util.EventObject;

/**
 * @author Francois Tadel
 */
public class ArrayCellEditor implements TableCellEditor {
    // Edition component : TextField
    JTextField editorTextField;
    DefaultCellEditor arrayEditor;   
    // Backup of the initial value and location in table
    Object initValue;
    int initRow, initCol;
    
    public ArrayCellEditor(){
        editorTextField = new JTextField();
        arrayEditor = new DefaultCellEditor(editorTextField);
        arrayEditor.setClickCountToStart(2);
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
            // Display input valut as an array : "[x,y,z]"
            Object objValues[] = (Object[]) value;
            StringBuffer editedValue = new StringBuffer("[");
            for (int i=0; i<objValues.length; i++){
                editedValue.append(String.format("%3.5f", ((Double)objValues[i]).doubleValue()));
                if (i != objValues.length - 1){
                    editedValue.append("; ");
                }
            }
            editedValue.append("]");
            
            // Set up editor component
            editorTextField.setText(editedValue.toString());
            editorTextField.setHorizontalAlignment(editorTextField.LEFT);
            editorTextField.setEnabled(true);
        }
        return editorTextField;
    }
    
    public Object getCellEditorValue() {       
        Object arrayValues[] = parseInputString();
        if (arrayValues == null){
            return initValue;
        }else{
            return arrayValues;
        }
    }

    public boolean isCellEditable(EventObject anEvent) {
        return arrayEditor.isCellEditable(anEvent);
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return arrayEditor.shouldSelectCell(anEvent) ;
    }

    public boolean stopCellEditing() {
        Object arrayValues[] = parseInputString();
        if (arrayValues == null){
            editorTextField.setForeground(new Color(0.8f, 0.0f, 0.0f));
            return false;
        } else {
            editorTextField.setForeground(new Color(0.0f, 0.0f, 0.0f));
            return true;
        }
    }

    public void cancelCellEditing() {  
        arrayEditor.cancelCellEditing();
    }

    public void addCellEditorListener(CellEditorListener l) {  
        arrayEditor.addCellEditorListener(l) ;
    }

    public void removeCellEditorListener(CellEditorListener l) {
        arrayEditor.removeCellEditorListener(l) ;
    }

    
    private Object[] parseInputString(){
        // Check that the input string has the format "[...]"
        char charInputs[] = editorTextField.getText().toCharArray();
        if ((charInputs.length < 7) || (charInputs[0] != '[') || (charInputs[charInputs.length - 1] != ']')){
            return null;
        }

        // Read all the inputs
        // (Remove the brackets and changes the ',' in '.')
        String strInputs[] = editorTextField.getText().replace(',', '.').substring(1, charInputs.length - 1).split(";");
        Double returnedArray[] = new Double[strInputs.length];
        
        // Check that there are 3 values
        if (strInputs.length != 3){
            return null;
        }
        
        // Parse values
        for (int i=0; i<strInputs.length; i++){
            //System.out.println(strInputs[i]);
            try{
                returnedArray[i] = Double.parseDouble(strInputs[i]);
            }catch (NumberFormatException e){
                return null;
            }
        }
                
        return returnedArray;
    }
    
}

