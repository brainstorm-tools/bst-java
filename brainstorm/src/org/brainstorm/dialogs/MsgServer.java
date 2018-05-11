package org.brainstorm.dialogs;

import javax.swing.*;
import java.awt.*;
import se.datadosen.component.RiverLayout;

/**
 * @author Francois Tadel
 */
public class MsgServer {
    static boolean isValidated = false;
    
    // ===== ERROR DIALOGS =====
    public static boolean dlgError(Component comp, String msg, String title){
        javax.swing.JOptionPane d = new javax.swing.JOptionPane();
        d.showMessageDialog( comp, msg, title, javax.swing.JOptionPane.ERROR_MESSAGE);
        return true;
    }
    
    // ===== WARNING DIALOGS =====
    public static boolean dlgWarning(Component comp, String msg, String title){
        javax.swing.JOptionPane d = new javax.swing.JOptionPane();
        d.showMessageDialog( comp, msg, title, javax.swing.JOptionPane.WARNING_MESSAGE);
        return true;
    }
    
    // ===== QUESTION DIALOGS =====
    public static String dlgQuest(Component comp, String msg, String title, String buttonList[], String defButton){
        int res = JOptionPane.showOptionDialog(comp, msg, title, 0, JOptionPane.QUESTION_MESSAGE, null, buttonList, defButton);
        
        if (res == JOptionPane.CLOSED_OPTION){
            return "";
        }else{
            return buttonList[res];
        }
    }
    
    // ===== INPUT DIALOG (ONE VALUE) =====
    public static String dlgInput(Component comp, String msg, String title, String defVal){
        Object res = JOptionPane.showInputDialog(comp, msg, title, JOptionPane.QUESTION_MESSAGE, null, null, defVal);
        if (res == null){
            return "#cancel#";
        }else{
            return (String) res;
        }
    }
    
    // ===== INPUT DIALOG (MORE THAN ONE VALUE) =====
    public static String [] dlgInput(Component comp, String [] msg, String title, String [] defVal){
        if (msg.length != defVal.length){
            return null;
        }
        
        // === SET UP DIALOG ===
        final JDialog jDialog = new JDialog((JFrame)comp, title, true);
        
        JPanel jPanelTop = new JPanel(new RiverLayout());
        jDialog.getRootPane().setLayout(new BorderLayout());
        jDialog.getRootPane().add(jPanelTop, BorderLayout.CENTER);
        
        JTextField [] jTextList = new JTextField[msg.length];
        for (int i=0; i<msg.length; i++){
            if (msg[i].toLowerCase().contains("password")){
                jTextList[i] = new JPasswordField(defVal[i]); 
            } else {
                jTextList[i] = new JTextField(defVal[i]);      
            }
            jPanelTop.add("p", new JLabel(msg[i]));
            jPanelTop.add("br hfill", jTextList[i]);
        }
        
        jPanelTop.add("br", new JLabel(" "));
        
        // === VALIDATION ===
        isValidated = false; 
        JButton jButtonOk = new JButton("OK");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isValidated = true;
                jDialog.dispose();
            }
        });              
        JButton jButtonCancel = new JButton("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDialog.dispose();
            }
        });
        jPanelTop.add("p right", jButtonOk);
        jPanelTop.add(jButtonCancel);
        // Make OK the default button
        jDialog.getRootPane().setDefaultButton(jButtonOk);
        
        
        // === SHOW DIALOG ===
        jDialog.pack();
        jDialog.setLocationRelativeTo(comp);
        jDialog.setModal(true);
        jDialog.setAlwaysOnTop(true);
        jDialog.setVisible(true);
        
        
        // === GET VALUES ===
        String [] result;
        if (isValidated){
            result = new String[msg.length];
            for (int i=0; i<msg.length; i++){
                result[i] = jTextList[i].getText();
            }
        }else{
            result = null;
        }
        
        jDialog.dispose();
        return result;
    }
    
    // ===== CONFIRM DIALOGS =====
    public static boolean dlgConfirm(Component comp, String msg, String title){
        int reponse = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.OK_OPTION){
            return true;
        }else{
            return false;
        }
    }
    
    // ===== INFORMATION DIALOGS =====
    public static boolean dlgInfo(Component comp, String msg, String title){
        javax.swing.JOptionPane d = new javax.swing.JOptionPane();
        d.showMessageDialog( null, msg, title, javax.swing.JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    
    public static int dlgSelectInList(java.awt.Frame parent, String title, String[] strList){
//        JDialogSelectList jDialog = new JDialogSelectList(parent, title, strList);
//        jDialog.setVisible(true);
//        
//        return jDialog.getSelIndex();
        String saisie = ( String )JOptionPane.showInputDialog( parent, 
                                                               title, 
                                                               "Selection",
                                                               JOptionPane.QUESTION_MESSAGE,
                                                               null, 
                                                               strList, strList[ 0 ] );
        for (int i=0; i<strList.length; i++){
            if (strList[i].equalsIgnoreCase(saisie)){
                return i;
            }
        }
        return -1;
    }
}

