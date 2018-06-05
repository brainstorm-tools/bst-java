package org.brainstorm.dialogs;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Martin Cousineau
 */
public class HotkeyDialog extends JOptionPane
        implements KeyEventDispatcher, WindowListener
{
    private Character key;
    private JDialog dialog;
    
    public HotkeyDialog() {
        this.key = null;
        this.init();
    }
    
    private void init() {
        // Create key listener
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(this);
        // Create dialog
        JOptionPane optionPane = new JOptionPane(
                "Please enter a new shortcut key (B to Z).",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{}
        );
        this.dialog = optionPane.createDialog("Hotkey Selection");
        this.dialog.addWindowListener(this);
        this.dialog.setVisible(true);
    }
    
    private void close() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .removeKeyEventDispatcher(this);
        this.dialog.dispose();
    }
    
    public boolean hasKey() {
        return key != null;
    }
    
    public char getKey() {
        return key;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        char c = e.getKeyChar();
        // If it is a valid char, save it and close dialog
        if (c >= 'b' && c <= 'z') {
            key = c;
            this.close();
        }
        return true;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // Do nothing.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Ensure close() is called if user closes the window.
        this.close();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // Do nothing.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // Do nothing.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // Do nothing.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // Do nothing.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Do nothing.
    }
    
}
