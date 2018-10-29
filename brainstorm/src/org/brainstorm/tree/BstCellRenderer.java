package org.brainstorm.tree;

import org.brainstorm.icon.IconLoader;
import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author Francois Tadel
 */
public class BstCellRenderer extends JLabel implements TreeCellRenderer, TableCellRenderer{
    protected Color m_textSelectionColor;
    protected Color m_textNonSelectionColor;
    protected Color m_bkSelectionColor;
    protected Color m_bkNonSelectionColor;
    protected Color m_borderSelectionColor;
    protected Color m_textDisabledColor;
    protected Color m_textMarkedColor;
    protected boolean m_selected;
    protected float m_interfaceScaling;
    protected Font  m_fontItalic;
    protected Font  m_fontPlain;

    public BstCellRenderer(float interfaceScaling, int fontSize, String fontName) {
        super();
        m_textSelectionColor   = new Color(51, 51, 51);
        m_bkSelectionColor     = new Color(184, 207, 229);
        m_bkNonSelectionColor  = UIManager.getColor("Tree.textBackground");
        m_borderSelectionColor = new Color(99, 130, 191);
        m_textDisabledColor    = new Color(200,0,0);
        m_textMarkedColor      = new Color(0,150,0);
        m_interfaceScaling     = interfaceScaling;
        // Create fonts
        m_fontItalic = new Font(fontName, Font.ITALIC, fontSize);
        m_fontPlain  = new Font(fontName, Font.PLAIN, fontSize);
        setOpaque(false);
    }


    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col){
        return getRenderer(table, value, isSelected, false, false, row, hasFocus);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected,
                    boolean expanded, boolean leaf, int row, boolean hasFocus){
        return getRenderer(tree, value, isSelected, expanded, leaf, row, hasFocus);
    }

    public Component getRenderer(JComponent jcomp, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus){
        boolean isCategoryNode = false;
        
        if (value instanceof BstNode) {
            BstNode bstNode = (BstNode) value;
            ImageIcon nodeIcon = null;
            // ROOT
            if(bstNode.getType().equalsIgnoreCase("root")){
                nodeIcon = IconLoader.ICON_OBJECT;
            // FOLDER
            }else if(bstNode.getType().equalsIgnoreCase("folder")){
                if (expanded)
                    nodeIcon = IconLoader.ICON_FOLDER_OPEN;
                else
                    nodeIcon = IconLoader.ICON_FOLDER_CLOSE;
            // LOADING
            }else if(bstNode.getType().equalsIgnoreCase("loading")){
                nodeIcon = IconLoader.ICON_LOADING;
                
            // SUBJECTDB
            }else if(bstNode.getType().equalsIgnoreCase("subjectdb")){
                nodeIcon = IconLoader.ICON_SUBJECTDB;
            // SUBJECT
            }else if(bstNode.getType().equalsIgnoreCase("subject")){
                nodeIcon = IconLoader.ICON_SUBJECT;
                isCategoryNode = true;
            // ANATOMY
            }else if(bstNode.getType().equalsIgnoreCase("anatomy")){
                nodeIcon = IconLoader.ICON_ANATOMY;
            // SURFACES
            }else if(bstNode.getType().equalsIgnoreCase("surface")){
                nodeIcon = IconLoader.ICON_SURFACE;
            }else if(bstNode.getType().equalsIgnoreCase("scalp")){
                nodeIcon = IconLoader.ICON_SURFACE_SCALP;
            }else if(bstNode.getType().equalsIgnoreCase("cortex")){
                nodeIcon = IconLoader.ICON_SURFACE_CORTEX;
            }else if(bstNode.getType().equalsIgnoreCase("outerskull")){
                nodeIcon = IconLoader.ICON_SURFACE_OUTERSKULL;
            }else if(bstNode.getType().equalsIgnoreCase("innerskull")){
                nodeIcon = IconLoader.ICON_SURFACE_INNERSKULL;
            }else if(bstNode.getType().equalsIgnoreCase("other")){
                nodeIcon = IconLoader.ICON_SURFACE;
            // LINK
            }else if(bstNode.getType().equalsIgnoreCase("defaultanat")){
                //nodeIcon = IconLoader.ICON_LINK;

            // STUDYDBSUBJ
            }else if(bstNode.getType().equalsIgnoreCase("studydbsubj")){
                nodeIcon = IconLoader.ICON_STUDYDB_SUBJ;
            // STUDYDBCOND
            }else if(bstNode.getType().equalsIgnoreCase("studydbcond")){
                nodeIcon = IconLoader.ICON_STUDYDB_COND;
            // STUDY
            }else if(bstNode.getType().equalsIgnoreCase("study")){
                nodeIcon = IconLoader.ICON_STUDY;
                isCategoryNode = true;
            // STUDYSUBJECT
            }else if(bstNode.getType().equalsIgnoreCase("studysubject")){
                nodeIcon = IconLoader.ICON_STUDYSUBJECT;
                isCategoryNode = true;
            // CONDITION
            }else if(bstNode.getType().equalsIgnoreCase("condition")){
                isCategoryNode = true;
                if (expanded)
                    nodeIcon = IconLoader.ICON_FOLDER_OPEN;
                else
                    nodeIcon = IconLoader.ICON_FOLDER_CLOSE;
            // CHANNEL
            }else if(bstNode.getType().equalsIgnoreCase("channel")){
                nodeIcon = IconLoader.ICON_CHANNEL;
            // HEADMODEL
            }else if(bstNode.getType().equalsIgnoreCase("headmodel")){
                nodeIcon = IconLoader.ICON_HEADMODEL;
            // DATA
            }else if(bstNode.getType().equalsIgnoreCase("data")){
                nodeIcon = IconLoader.ICON_DATA;
            }else if(bstNode.getType().equalsIgnoreCase("datalist")){
                nodeIcon = IconLoader.ICON_DATA_LIST;
            // RAW DATA
            }else if(bstNode.getType().equalsIgnoreCase("rawdata")){
                if (bstNode.getFileName().contains("_0ephys"))
                    nodeIcon = IconLoader.ICON_SPIKE_SORTING;
                else
                    nodeIcon = IconLoader.ICON_RAW_DATA;
            }else if(bstNode.getType().equalsIgnoreCase("rawcondition")){
                isCategoryNode = true;
                if (expanded)
                    nodeIcon = IconLoader.ICON_RAW_FOLDER_OPEN;
                else
                    nodeIcon = IconLoader.ICON_RAW_FOLDER_CLOSE;
            // RESULTS
            }else if(bstNode.getType().equalsIgnoreCase("results")){
                nodeIcon = IconLoader.ICON_RESULT;
            }else if(bstNode.getType().equalsIgnoreCase("kernel")){
                nodeIcon = IconLoader.ICON_RESULT_KERNEL;
            }else if(bstNode.getType().equalsIgnoreCase("link")){
                nodeIcon = IconLoader.ICON_RESULTS_LINK;
            // DEFAULT STUDY
            }else if(bstNode.getType().equalsIgnoreCase("defaultstudy")){
                nodeIcon = IconLoader.ICON_DEFAULT_STUDY;
                isCategoryNode = true;
            // IMAGE
            }else if(bstNode.getType().equalsIgnoreCase("image")){
                nodeIcon = IconLoader.ICON_IMAGE;
            // VIDEO
            }else if(bstNode.getType().equalsIgnoreCase("video")){
                nodeIcon = IconLoader.ICON_VIDEO;
            // NOISECOV
            }else if(bstNode.getType().equalsIgnoreCase("noisecov")){
                nodeIcon = IconLoader.ICON_NOISECOV;
            // DIPOLES
            }else if(bstNode.getType().equalsIgnoreCase("dipoles")){
                nodeIcon = IconLoader.ICON_DIPOLES;
            // TIMEFREQ
            }else if(bstNode.getType().equalsIgnoreCase("timefreq")){
                if (bstNode.getFileName().contains("_hilbert")){
                    nodeIcon = IconLoader.ICON_HILBERT;
                }else if (bstNode.getFileName().contains("_connect1")){
                    nodeIcon = IconLoader.ICON_CONNECT1;
                }else if (bstNode.getFileName().contains("_connectn")){
                    nodeIcon = IconLoader.ICON_CONNECTN;
                }else if (bstNode.getFileName().contains("_pac")){
                    nodeIcon = IconLoader.ICON_PAC;
                }else if (bstNode.getFileName().contains("_dpac")){
                    nodeIcon = IconLoader.ICON_PAC;
                }else{
                    nodeIcon = IconLoader.ICON_TIMEFREQ;
                }
            // SPECTRUM
            }else if(bstNode.getType().equalsIgnoreCase("spectrum")){
                nodeIcon = IconLoader.ICON_SPECTRUM;
            // MATRIX
            }else if(bstNode.getType().equalsIgnoreCase("matrix")){
                nodeIcon = IconLoader.ICON_MATRIX;
            // MATRIX LIST
            }else if(bstNode.getType().equalsIgnoreCase("matrixlist")){
                nodeIcon = IconLoader.ICON_MATRIX_LIST;
            // PDATA
            }else if(bstNode.getType().equalsIgnoreCase("pdata")){
                nodeIcon = IconLoader.ICON_PDATA;
            // PRESULTS
            }else if(bstNode.getType().equalsIgnoreCase("presults")){
                nodeIcon = IconLoader.ICON_PRESULTS;
            // PTIMEFREQ
            }else if(bstNode.getType().equalsIgnoreCase("ptimefreq")){
                if (bstNode.getFileName().contains("_hilbert")){
                    nodeIcon = IconLoader.ICON_PHILBERT;
                }else if (bstNode.getFileName().contains("_connect1")){
                    nodeIcon = IconLoader.ICON_PCONNECT1;
                }else if (bstNode.getFileName().contains("_connectn")){
                    nodeIcon = IconLoader.ICON_PCONNECTN;
                }else if (bstNode.getFileName().contains("_pac")){
                    nodeIcon = IconLoader.ICON_PPAC;
                }else if (bstNode.getFileName().contains("_dpac")){
                    nodeIcon = IconLoader.ICON_PPAC;
                }else{
                    nodeIcon = IconLoader.ICON_PTIMEFREQ;
                }
            // PSPECTRUM
            }else if(bstNode.getType().equalsIgnoreCase("pspectrum")){
                nodeIcon = IconLoader.ICON_PSPECTRUM;
            // PMATRIX
            }else if(bstNode.getType().equalsIgnoreCase("pmatrix")){
                nodeIcon = IconLoader.ICON_PMATRIX;
            // DEFAULT ICON
            }else{
                nodeIcon = IconLoader.ICON_OBJECT;
            }
            
            // Scale icon if needed
            if ((m_interfaceScaling != 1) && (nodeIcon != null)){
                nodeIcon = IconLoader.scaleIcon(nodeIcon, m_interfaceScaling);
            }

            // Add overlay on icon
            switch (bstNode.getModifier()){
                case 0:
                    setIcon(nodeIcon);
                    break;
                case 1:
                    ImageIcon overlayIcon = IconLoader.ICON_MODIF_BAD;
                    if ((m_interfaceScaling != 1) && (nodeIcon != null)){
                        overlayIcon = IconLoader.scaleIcon(overlayIcon, m_interfaceScaling);
                    }
                    setIcon(new OverlayIcon(nodeIcon, overlayIcon));
                    break;
                // No modifier, but date to display
                default:
                    setIcon(nodeIcon);
                    break;
            }

            // Background color
            setBackground(isSelected ? m_bkSelectionColor : m_bkNonSelectionColor);
            // Foreground color
            if (bstNode.isMarked() && !isCategoryNode){
                setForeground(m_textMarkedColor);
            }else{
                if (bstNode.isEnabled()) {
                    setForeground(isSelected ? m_textSelectionColor : m_textNonSelectionColor);
                }else{
                    setForeground(isSelected ? m_textDisabledColor : m_textDisabledColor);
                }
            }

            // Font : default tree font, with some modifications
            String nodeComment;
            // Node name start with a '(' => Italic
            if ((bstNode.getComment().length() > 0) && (bstNode.getComment().charAt(0) == '(')){
                setFont(m_fontItalic);
                nodeComment = bstNode.getComment().replace("(", "").replace(")", "");
            }else{
                setFont(m_fontPlain);
                nodeComment = bstNode.getComment();
            }
            // Marked StudySubject nodes : UNDERLINED
            if (isCategoryNode && bstNode.isMarked()){
                setText("<HTML><U>" + nodeComment + "</U></HTML>");
            }else{
                setText(nodeComment);
            }

        }else{
            setText("Chargement...");
        }

        m_selected = isSelected;
        return this;
    }

    // === Draw component ===
    public void paintComponent(Graphics g) {
        Color bColor = getBackground();
        // Paint object background
        g.setColor(bColor);
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        // Paint border of the selection background (if selected)
        if (m_selected) {
            g.setColor(m_borderSelectionColor);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        // Draw component itself (icon and text)
        super.paintComponent(g);
    }   
}

