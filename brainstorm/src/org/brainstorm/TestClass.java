package org.brainstorm;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import org.brainstorm.dnd.*;
import org.brainstorm.table.*;
import org.brainstorm.tree.*;
import org.brainstorm.list.*;
import org.brainstorm.file.*;
import org.brainstorm.icon.*;
import org.brainstorm.dialogs.*;
import java.awt.event.*;

/**
 * @author Francois Tadel
 */
public class TestClass {
    // ===== CONSTRUCTOR =====
    public TestClass(){     
    }
    
    // ===== TEST TREE =====
    static public void TestTreeWindow(){
        // Try to load an icon
        int []gg = IconLoader.getIcon_RGB("ICON_MOVE"); 
        
        // ==== CREATE TREE ====
        // Create a tree
        final BstTree treeDrag = new BstTree(1.5f, 6, "Arial");
        treeDrag.setEditable(true);
        treeDrag.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        // Enable drag'n'drop
        treeDrag.setDragEnabled(true);
        treeDrag.setTransferHandler(new TreeDragTransferHandler());
        // Create tree nodes
        BstNode nodeRoot = (BstNode) treeDrag.getModel().getRoot();
        final BstNode subject1 = new BstNode( "condition", "Subject #1", "C:\\SUBJECTS\\brainstormsubject.mat", 1, 1, 37774);
            BstNode bstNode = new BstNode( "studysubject", "Anatomy", "C:\\SUBJECTS\\test.mri", 1, 1, 37774);
            bstNode.setMarked(true);
            subject1.add(bstNode);
            subject1.add(new BstNode( "defaultstudy", "Trial 1", "C:\\STUDIES\\brainstorm_study.bin", 1, 1, 37774));
            subject1.add(new BstNode( "defaultanat", "(Default anatomy)", "", 0, 0));
            subject1.add(new BstNode( "timefreq", "Data1", "C:\\SUBJECTS\\timefreq_sss.mri", 1, 3));
            subject1.add(new BstNode( "timefreq", "Data2", "C:\\SUBJECTS\\timefreq_hilbert.mri", 1, 3, 1));
            subject1.add(new BstNode( "spectrum", "Data1", "C:\\SUBJECTS\\timefreq_sss.mri", 1, 3));
            subject1.add(new BstNode( "spectrum", "Data2", "C:\\SUBJECTS\\timefreq_fft.mri", 1, 3, 1));
            subject1.add(new BstNode( "result", "Exemple defaut", "C:\\SUBJECTS\\tata.mri", 1, 3));
            subject1.add(new BstNode( "link", "Linl", "link|resultsfile|datafile", 1, 3));
            subject1.sortChildren();
            subject1.add(new BstNode( "presults", "(Cortex)", "C:\\SUBJECTS\\surface_tess.mat", 1, 1));
            BstNode bstN5 = new BstNode( "headmodel", "Exemple defaut", "C:\\SUBJECTS\\tata.mri", 1, 3);
            bstN5.setMarked(true);
            bstN5.setEnabled(false);
            subject1.add(bstN5);
        nodeRoot.add(subject1);
        nodeRoot.add(new BstNode( "subject", "Subject #2", "C:\\SUBJECTS\\brainstormsubject.mat", 1, 2));
        // Reload tree
        treeDrag.refresh();
        
        // ==== CREATE TARGET JTREE ====
        // Create object
        BstTreeTable treeDrop = new BstTreeTable(1.5f, 6, "Arial");
                
        // ==== DISPLAY FRAME ====
        // Frame
        JFrame f = new JFrame("Test Brainstorm Tree");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(300, 500));
        // Split panel
        JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treeDrag, treeDrop.getContainer());
        splitPanel.setDividerLocation(200);
        f.add(splitPanel);
        // Test button
        JButton jButtonTest = new JButton("Test");
        f.add(jButtonTest, BorderLayout.SOUTH);
        jButtonTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                subject1.sortChildren();
                treeDrag.refresh();
            }
        });

        // Make frame visible
        f.setVisible(true);
    }
    

    // ===== TEST TREE =====
    static public void TestListWindow() {
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch(Exception e){
        }
        JFrame frame = new JFrame("Sizing Samples");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultListModel model = new DefaultListModel();
        model.addElement(new BstListItem("mean", "/EXAMPLE/PATH", "Process #1 BLABLA", 1, 1f, 0.4f, 0.4f));
        model.addElement(new BstListItem("mean", "/EXAMPLE/PATH", "Process #2", 1, 0.4f, 1f, 0.4f));
        model.addElement(new BstListItem("mean", "/EXAMPLE/PATH", "Process #3: TE", 1, 0f, 1f, 0.4f));
        model.addElement(new BstListItem("PCA", "/EXAMPLE/PATH/2", "Process #40"));
        
        //JList jlist2 = new JList();
        JList jlist2 = new BstClusterList();
        jlist2.setModel(model);
        jlist2.setLayoutOrientation(jlist2.HORIZONTAL_WRAP);
        
        //ListCellRenderer renderer = new BstColorListRenderer(20);
        //ListCellRenderer renderer = new BstProcessListRenderer(20, 28);
        ListCellRenderer renderer = new BstClusterListRenderer(20, 300);
        jlist2.setCellRenderer(renderer);

        JScrollPane scrollPane2 = new JScrollPane(jlist2);
        frame.add(scrollPane2, BorderLayout.CENTER);

        frame.setSize(300, 350);
        frame.setVisible(true);

        jlist2.ensureIndexIsVisible(50);
        
        //jlist2.setEnabled(false);
    }


    // ===== TEST TREE =====
    static public void TestCheckListWindow() {
        JFrame frame = new JFrame("Check list");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultListModel model = new DefaultListModel();
        model.addElement(new BstListItem("Projector 1", "", "Process #1", (int) 0, 1f, 0.4f, 0.4f));
        model.addElement(new BstListItem("Projector 2", "", "Process #1", (int) 1));
        model.addElement(new BstListItem("Projector 3", "", "Process #1", (int) 2));

        JList jlist2 = new JList(model);
        ListCellRenderer renderer = new BstCheckListRenderer(20);
        jlist2.setCellRenderer(renderer);

        jlist2.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent event){
                JList list = (JList) event.getSource();
                if (!list.isEnabled()){
                    return;
                }
                int index = list.locationToIndex(event.getPoint());
                BstListItem item = (BstListItem) list.getModel().getElementAt(index);
                int status = ((Integer) item.getUserData()).intValue();
                if (status == 1){
                    item.setUserData(0);
                }else if (status == 0){
                    item.setUserData(1);
                }
                list.repaint(list.getCellBounds(index, index));
            }
        });   
        
        JScrollPane scrollPane2 = new JScrollPane(jlist2);
        frame.add(scrollPane2, BorderLayout.CENTER);

        frame.setSize(300, 350);
        frame.setVisible(true);

        jlist2.ensureIndexIsVisible(50);
        jlist2.setEnabled(false);
    }
    
    
    // ===== TEST TABLE WINDOW =====
    static public void TestTableWindow(){
        // Create and configure JFrame
        JFrame f = new JFrame("Test Brainstorm Table");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(600, 300));
        f.setForeground(Color.black);
        f.setBackground(Color.lightGray);

        // Column names
        String columnNames[] = {" ", " ", "Name", "Type",  "Comment", "Loc(1)", "Loc(2)", "Orient(1)", "Orient(2)", "Weight"};
        boolean isColEditable[] = {false, true, true, true, true, false, false, false, false, false};
        Object locArray[] = {0.253566445, -5.123, 1.02};
        // Build channels name
        Object channelsData[][] = {{999, java.lang.Boolean.TRUE, "", "EGG", "AVERAGE REF", null, locArray, locArray, locArray,  1}, 
                                   {5, java.lang.Boolean.TRUE, "EG152", "EGG", "AVERAGE REF", locArray, locArray, locArray, locArray,  1},
                                   {1, java.lang.Boolean.TRUE, "B", "EGG", "AVERAGE REF", locArray, locArray, locArray, locArray,  1},
                                   {1, java.lang.Boolean.TRUE, "A", "EGG", "AVERAGE REF", locArray, locArray, locArray, locArray,  1}};

        // Create JTable
        final JTable jTableChannel = new JTable();
        final ChannelTableModel model = new ChannelTableModel(jTableChannel, channelsData, columnNames, isColEditable);
        jTableChannel.setModel(model);
        jTableChannel.setRowHeight(22);
        jTableChannel.setForeground(new Color(51,  51,  51));
        jTableChannel.setSelectionBackground(new Color(184, 207, 229));
        jTableChannel.setSelectionForeground(new Color(51,  51,  51));
        jTableChannel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        jTableChannel.getColumnModel().getColumn(0).setPreferredWidth(30);  // Channel index
        jTableChannel.getColumnModel().getColumn(1).setPreferredWidth(15);  // Good/Bad
        jTableChannel.getColumnModel().getColumn(2).setPreferredWidth(50);  // Name
        jTableChannel.getColumnModel().getColumn(3).setPreferredWidth(60);  // Type
        jTableChannel.getColumnModel().getColumn(4).setPreferredWidth(70);  // Comment
        jTableChannel.getColumnModel().getColumn(9).setPreferredWidth(20);  // Weight

        // Create cell Renderers
        jTableChannel.getColumnModel().getColumn(0).setCellRenderer(new IntegerCellRenderer());
        jTableChannel.getColumnModel().getColumn(1).setCellRenderer(new BooleanCellRenderer());
        jTableChannel.getColumnModel().getColumn(5).setCellRenderer(new ArrayCellRenderer());
        jTableChannel.getColumnModel().getColumn(6).setCellRenderer(new ArrayCellRenderer());

        // Create cell editors
        Object tableListTypes[] = {"MEG", "EEG", "EEG REF", "SEEG", "ECG", "Other"};
        JComboBox jComboTypes = new JComboBox(tableListTypes);
        jComboTypes.setEditable(true);

        jTableChannel.getColumnModel().getColumn(0).setCellEditor(new IntegerCellEditor());
        jTableChannel.getColumnModel().getColumn(1).setCellEditor(new BooleanCellEditor());
        jTableChannel.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(jComboTypes));
        jTableChannel.getColumnModel().getColumn(5).setCellEditor(new ArrayCellEditor());

        // Insert JTable in a ScrollPane
        f.getContentPane().add(new JScrollPane(jTableChannel),"Center");

        // Button "Sort"
        JButton jb = new JButton("Sort");
        jb.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // swap 2 lines
                model.sortIndices(0, 2);
                jTableChannel.repaint();
            }
        });
        f.getContentPane().add(jb, "South");

        f.setVisible(true);
    }
    

    // ===== TEST TABLE WINDOW =====
    static public void TestTreeTableWindow(){
        // Create and configure JFrame
        JFrame f = new JFrame("Test Brainstorm TreeTable");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(600, 300));
        f.setForeground(Color.black);
        f.setBackground(Color.lightGray);
        // Create TableTree
        BstTreeTable table = new BstTreeTable(1.5f, 6, "Arial");

        // Build list of nodes
        BstNode node1 = new BstNode( "condition", "Cond #1", "C:\\SUBJECTS\\brainstormsubject.mat", 1, 1);
        BstNode node2 = new BstNode( "condition", "Cond #2", "C:\\SUBJECTS\\brainstormsubject.mat", 1, 1);
        Object tableData[][] = {{node1, "", "", "", ""},
                                {node2, "", "", "", ""}};
        // Update model
        table.setModel(tableData);
        table.add(new BstNode( "data", "Cond #3", "C:\\SUBJECTS\\brainstormsubject.mat", 1, 1));

        // Insert JTable in a ScrollPane
        f.getContentPane().add(table.getContainer(), "Center");

        f.setVisible(true);
    }


    // ===== TEST FILE SELECTOR =====
    static public void TestFileSelector(){
        // Test suffixes
        String []suffixes1 = {"_timeseries"};
        String []suffixes2 = {".txt"};
        String []suffixes3 = {"*"};
        // Create filters
        BstFileFilter filters[] = new BstFileFilter[3];
        filters[0] = new BstFileFilter(suffixes1, "Brainstorm time series (*timeseries*.mat)", "BST");
        filters[1] = new BstFileFilter(suffixes2, "ASCII time series (*.txt)", "ASCII");
        filters[2] = new BstFileFilter(suffixes3, "FreeSurfers (*.*)", "ASCII");
        // Display file selector
        BstFileSelector fSelector = new BstFileSelector( 
                BstFileSelector.TYPE_SAVE, 
                "Save time series", 
                "C:\\Users\\francois\\Documents\\test.tes", 
                BstFileSelector.SELECTION_SINGLE, 
                BstFileSelector.FILES_ONLY, 
                filters, 
                0);
        fSelector.showSameThread();
    }
    
    // ===== TEST DIALOGS =====
    public static void TestDialog(){
        String [] list = {"AAA", "BBB"};       
        //MsgServer.dlgError(null, "Message", "Title");
        //MsgServer.dlgWarning(null, "Message", "Title");
        //MsgServer.dlgQuest(null, "Message", "Title", list, "BBB");
        //MsgServer.dlgInput(null, "Message", "Title", "AAA");
        
        //String []res = MsgServer.dlgInput(null, list, "Title", list);
        //MsgServer.dlgInfo(null, res[0], "Title");
        
        boolean res = MsgServer.dlgErrorHelp(null, "test", "title");
        if (res){
            MsgServer.dlgInfo(null, "OK", "Title");
        } else {
            MsgServer.dlgInfo(null, "HELP", "Title");
        }
        
        // MsgServer.dlgConfirm(null, "Message", "Title");
        //MsgServer.dlgInfo(null, "Message", "Title");
        //MsgServer.dlgSelectInList(null, "Title", list);
    }

    // ===== TEST DOWNLOAD =====
    public static void TestDownload(){
        // Download file
        //String srcUrl  = "http://neuroimage.usc.edu/bst/getupdate.php?c=UbsM09&nobin=1";
        //String srcUrl  = "https://github.com/csn-le/wave_clus/archive/master.zip";
        String srcUrl  = "ftp://neuroimage.usc.edu/pub/templates/Kabdebon_7w.zip";
        final BstDownload bstDownload = new BstDownload(srcUrl, "C:\\Users\\franc\\Downloads\\test.zip", "Download test");
        
        bstDownload.download();
        // Wait for the termination of the thread
        try {
            while (bstDownload.getResult() == -1){
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Interruption", "Download file", JOptionPane.WARNING_MESSAGE);
        }
        // If file was not downloaded correctly
        if (bstDownload.getResult() != 1){
            JOptionPane.showMessageDialog(null, "Error downloading file: \n" + bstDownload.getMessage(), "Download file", JOptionPane.WARNING_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "File successfully downloaded!", "Download file", JOptionPane.OK_OPTION);
        }
    }
    
    // ===== TEST HOTKEY DIALOG =====
    public static void TestHotkeyDialog(){
        HotkeyDialog dialog = new HotkeyDialog(12);
        if (dialog.hasKey()) {
            char key = dialog.getKey();
            System.out.println("Chosen key: " + key);
        } else {
            System.out.println("No key chosen.");
        }
    }


    // ===== MAIN =====
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {     
                try {
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (Exception e) {

                }
                //TestTableWindow();
                // TestFileSelector();
                //TestDialog();
                TestDownload();
                //TestListWindow();
                // TestCheckListWindow();
                //TestTreeWindow();
                //TestHotkeyDialog();
                // TestTreeTableWindow();
                // CloneControl.probe("C:\\Work\\Dev\\brainstorm_git\\brainstorm3");
                
                // Unpack.gunzip("C:\\Users\\franc\\Downloads\\T1.mgz", "C:\\Users\\franc\\Downloads\\T1.mgh");
                // Unpack.unzip("C:\\TutorialCTF.zip", "C:\\");

                //String list_file[] = {"C:\\test.avi", "C:\\test2.avi"};
                //String list_prefix[] = {"AA", "BB"};
                //Pack.zip(list_file, "C:\\test.zip", list_prefix);
            }
        });
    }
}
