package org.brainstorm.table;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * @author Francois Tadel
 */
public class ChannelTableModel extends AbstractTableModel {
    JTable jTable;
    Object[][] tableData;
    String[] colTitle;
    boolean[] isColEditable;

    public ChannelTableModel(JTable jTableChannel, Object tableData[][], String colTitle[], boolean isColEditable[]) {
        this.tableData = tableData;
        this.colTitle = colTitle;
        this.jTable = jTableChannel;
        this.isColEditable = isColEditable;
    }

    public ChannelTableModel(JTable jTableChannel, Object tableData[][], String colTitle[]) {
        this.tableData = tableData;
        this.colTitle = colTitle;
        this.jTable = jTableChannel;
        // By default: all the columns are editable
        this.isColEditable = new boolean[colTitle.length];
        for (int i=0; i<colTitle.length; i++){
            this.isColEditable[i] = true;
        }
    }
    
    public int getColumnCount() {
        return colTitle.length;
    }

    public Object getValueAt(int parm1, int parm2) {
        return tableData[parm1][parm2];
    }

    public void setValueAt(Object val, int parm1, int parm2) {
        int [] ind = jTable.getSelectedRows();
        if (!tableData[parm1][parm2].equals(val)){
            for (int i=0; i < ind.length; i++){
                tableData[ind[i]][parm2] = val;
            }
        }
        fireTableDataChanged();
    }

    public int getRowCount() {
        if (tableData == null){
            return 0;
        } else {
            return tableData.length;
        }
    }

    public String getColumnName(int col) {
        return colTitle[col];
    }

    public Class getColumnClass(int c) {
        if (tableData[0][c] == null){
            return java.lang.String.class;
        } else {
            return getValueAt(0, c).getClass();
        }
    }

    public boolean isCellEditable(int row, int col) {
        return isColEditable[col];
    }

    public boolean compareIndices(int i1, int i2, int iColInd, int iColName){
        // Return true if (i1 <= i2)
        int ind1 = (java.lang.Integer)tableData[i1][iColInd];
        int ind2 = (java.lang.Integer)tableData[i2][iColInd];

        if (ind1 < ind2){
            return true;
        }else if (ind1 > ind2){
            return false;
        }else{
            return false;
        }
    }

    public void swapIndices(int i1, int i2){
        Object tmp[];
        tmp = tableData[i1];
        tableData[i1] = tableData[i2];
        tableData[i2] = tmp;
    }

    public void sortIndices(int iColInd, int iColName) {
        int i = 1;
        int j = 2;
        while (i < getRowCount()){
            if (compareIndices(i-1, i, iColInd, iColName)){
                i = j;
                j = j + 1;
            }else{
                swapIndices(i-1, i);
                i = i - 1;
                if (i == 0){
                    i = j;
                    j = j + 1;
                }
            }
        }

        // Renumber indices
        for (i=0; i<getRowCount(); i++){
            tableData[i][iColInd] = new java.lang.Integer(i + 1);
        }
    }
}






