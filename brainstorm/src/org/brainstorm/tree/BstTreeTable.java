package org.brainstorm.tree;

import javax.swing.*;
import java.awt.*;
import org.brainstorm.dnd.*;
import org.brainstorm.table.*;

/**
 * @author Francois Tadel
 */
public class BstTreeTable extends JTable{
    private TreeTableModel model;
    private String         columnNames[] = {"Files", "F1", "F2", "F3", "F4"};
    private JScrollPane    container;

    // ===== CONSTRUTOR =====
    public BstTreeTable(float interfaceScaling, int fontSize, String fontName){
        super();
        // === GRAPHIC PROPERTIES ===
        this.setRowHeight(22);
        this.setForeground(new Color(51,  51,  51));
        this.setSelectionBackground(new Color(184, 207, 229));
        this.setSelectionForeground(new Color(51,  51,  51));
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // === CREATE CONTAINER ===
        // Scroll panel
        container = new JScrollPane(this);
        container.getViewport().setBackground(this.getBackground());
        
        // === SET UP DRAG AND DROP ===
        // Enable drag'n'drop
        this.setDragEnabled(true);
        this.setTransferHandler(new TreeDropTransferHandler());
        container.setTransferHandler(this.getTransferHandler());

        // === DATA MODEL ===
        // Create model
        model = new TreeTableModel(this, null, columnNames);
        this.setModel(model);
        // Set columns
        this.getColumnModel().getColumn(0).setPreferredWidth(200);  // Node
        this.getColumnModel().getColumn(1).setPreferredWidth(30);  // Factor1
        this.getColumnModel().getColumn(2).setPreferredWidth(30);  // Factor2
        this.getColumnModel().getColumn(3).setPreferredWidth(30);  // Factor3
        this.getColumnModel().getColumn(4).setPreferredWidth(30);  // Factor4
        // Create cell Renderers
        this.getColumnModel().getColumn(0).setCellRenderer(new BstCellRenderer(interfaceScaling, fontSize, fontName));
        // Create cell editors
        Object tableListTypes[] = {"", 1, 2, 3, 4, 5, 7};
        JComboBox jComboTypes = new JComboBox(tableListTypes);
        this.getColumnModel().getColumn(0).setCellEditor(null);
        this.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(jComboTypes));
        this.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(jComboTypes));
        this.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(jComboTypes));
        this.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(jComboTypes));
    }
    // ===== GET CONTAINER =====
    public JComponent getContainer(){
        return container;
    }

    // ===== MODEL =====
    public void setModel(Object[][] listObj){
        model.setData(listObj);
        this.repaint();
    }
    public void removeAllNodes(){
        model.setData(null);
        model.refresh();
    }
    public void removeSelectedNodes(){
        removeIndices(this.getSelectedRows());
    }
    public void refresh(){
        model.refresh();
    }

    // ===== NODES: ADD =====
    public void add(BstNode node){
        // Get current list
        Object listObj[][] = model.getData();
        Object newListObj[][];
        // Allocate new array
        if (listObj == null){
            newListObj = new Object[1][5];
        }else{
            newListObj = new Object[listObj.length+1][5];
            // Copy all the previous nodes
            for (int i=0; i<listObj.length; i++){
                newListObj[i] = listObj[i];
            }
        }
        // Add new element
        Object newRow[] = {node, "", "", "", ""};
        newListObj[newListObj.length-1] = newRow;
        // Set the data array
        model.setData(newListObj);
    }

    // ===== ISMEMBER =====
    public boolean ismember(int val, int[] list){
        for (int i=0; i<list.length; i++){
            if (list[i] == val){
                return true;
            }
        }
        return false;
    }

    // ===== NODES: REMOVE =====
    public void removeIndices(int[] rmInd){
        // Get current list
        Object listObj[][] = model.getData();
        Object newListObj[][];
        // Allocate new array
        if ((listObj == null) | (rmInd == null)){
            return;
        }else{
            newListObj = new Object[listObj.length - rmInd.length][5];
            // Copy all the previous nodes
            int iNew = 0;
            for (int i=0; i<listObj.length; i++){
                if (!ismember(i, rmInd)){
                   newListObj[iNew] = listObj[i];
                   iNew = iNew+1;
                }
            }
        }
        // Set the data array
        model.setData(newListObj);
        model.refresh();
    }

    // ===== NODES: GET =====
    public BstNode[] getNodes(){
        // Get current list
        Object listObj[][] = model.getData();
        // If no elements: return null
        if (listObj == null){
            return null;
        // Else: return the list of all the nodes
        }else{
            // Create returned list
            BstNode listNodes[] = new BstNode[listObj.length];
            // Popuplate list
            for (int i=0; i<listObj.length; i++){
                listNodes[i] = (BstNode) listObj[i][0];
            }
            return listNodes;
        }
    }
}
