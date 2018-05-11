package org.brainstorm.table;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import org.brainstorm.tree.BstTreeTable;

/**
 * @author Francois Tadel
 */
public class TreeTableModel extends AbstractTableModel {
    BstTreeTable jTreeTable;
    Object donnees[][];
    String titres[];

    public TreeTableModel(BstTreeTable jTreeTable, Object donnees[][], String titres[]) {
        this.jTreeTable = jTreeTable;
        this.donnees = donnees;
        this.titres = titres;
    }

    public int getColumnCount() {
        //return donnees[0].length;
        return 5;
    }

    public Object getValueAt(int parm1, int parm2) {
        return donnees[parm1][parm2];
    }

    public void setValueAt(Object val, int parm1, int parm2) {
        int selRows[] = jTreeTable.getSelectedRows();
        for (int i=0; i<selRows.length; i++){
            donnees[selRows[i]][parm2] = val;
        }
        fireTableDataChanged();
    }

    public void setData(Object[][] data){
        donnees = data;
    }
    public Object[][] getData(){
        return this.donnees;
    }
    
    public int getRowCount() {
        if (donnees == null){
            return 0;
        }else{
            return donnees.length;
        }
    }

    public String getColumnName(int col) {
        return titres[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col >= 1){
            return true;
        }else{
            return false;
        }
    }

    public void refresh(){
        fireTableDataChanged();
    }

}
