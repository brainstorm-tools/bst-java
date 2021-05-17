package org.brainstorm.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

/**
 * @author Francois Tadel
 */
public class BstNode extends DefaultMutableTreeNode {
    String  m_nodeType;
    String  m_nodeComment;
    String  m_fileName;
    int     m_studyIndex = 0;
    int     m_itemIndex = 0;
    boolean m_enabled = true;
    boolean m_marked = false;
    int     m_modifier = 0;
    long    m_lastmodifiedtime = 0;
    String  m_lastmodifieduser;

    // Constructor
    public BstNode(String nodeType, String nodeComment){
        m_nodeType      = new String(nodeType);
        m_nodeComment   = new String(nodeComment);
        m_fileName      = new String("");
    }
        
    public BstNode(String nodeType, String nodeComment, String fileName, int itemIndex, int studyIndex){
        m_nodeType      = new String(nodeType);
        m_nodeComment   = new String(nodeComment);
        m_fileName      = new String(fileName);
        m_itemIndex     = itemIndex;
        m_studyIndex    = studyIndex;
    }
    
    public BstNode(String nodeType, String nodeComment, String fileName, int itemIndex, int studyIndex, int modifier){
        m_nodeType      = new String(nodeType);
        m_nodeComment   = new String(nodeComment);
        m_fileName      = new String(fileName);
        m_itemIndex     = itemIndex;
        m_studyIndex    = studyIndex;
        m_enabled       = true;
        m_marked        = false;
        m_modifier      = modifier;
    }

    public String getType(){
        return m_nodeType;
    }
    public void setType(String type){
        this.m_nodeType = new String(type);
    }
    
    public String getComment(){
        return m_nodeComment;
    }
    public void setComment(String name){
        this.m_nodeComment = new String(name);
    }
    
    public String getFileName(){
        return m_fileName;
    }
    public void setFileName(String filename){
        this.m_fileName = new String(filename);
    }
    
    public int getStudyIndex(){
        return m_studyIndex;
    }
    public void setStudyIndex(int ind){
        this.m_studyIndex = ind;
    }
    
    public int getItemIndex(){
        return m_itemIndex;
    }
    public void setItemIndex(int ind){
        this.m_itemIndex = ind;
    }
    
    public boolean isEnabled(){
        return m_enabled;
    }
    public void setEnabled(boolean status){
        m_enabled = status;
    }
  
    public boolean isMarked(){
        return m_marked;
    }
    public void setMarked(boolean status){
        m_marked = status;
    }    

    public int getModifier(){
        return m_modifier;
    }
    public void setModifier(int mod){
        this.m_modifier = mod;
    }
    
    public void setLastModifiedTime(long mod) {
        m_lastmodifiedtime = mod;
    }
    public long getLastModifiedTime() {
        return m_lastmodifiedtime;
    }
    
    public void setLastModifiedUser(String user) {
        m_lastmodifieduser = user;
    }
    public String getLastModifiedUser() {
        return m_lastmodifieduser;
    }
    
    public String toString(){
        return this.getComment();
    }
    
    // ===== ACCELERATED ADD =====
    public BstNode add(String nodeType, String nodeComment, String fileName, int itemIndex, int studyIndex, int modifier){
        BstNode newNode = new BstNode(nodeType, nodeComment, fileName, itemIndex, studyIndex, modifier);
        this.add(newNode);
        return newNode;
    }
    public BstNode add(String nodeType, String nodeComment, String fileName, int itemIndex, int studyIndex){
        BstNode newNode = new BstNode(nodeType, nodeComment, fileName, itemIndex, studyIndex);
        this.add(newNode);
        return newNode;
    }
    public BstNode add(String nodeType, String nodeComment){
        BstNode newNode = new BstNode(nodeType, nodeComment);
        this.add(newNode);
        return newNode;
    }
    public BstNode [] add(String [] nodeTypes, String [] nodeComments, String [] fileNames, int [] itemIndices, int [] studyIndices, int [] modifiers){
        BstNode [] newNodes = new BstNode[nodeTypes.length];
        for (int i=0; i<nodeTypes.length; i++){
            newNodes[i] = new BstNode(nodeTypes[i], nodeComments[i], fileNames[i], itemIndices[i], studyIndices[i], modifiers[i]);
            this.add(newNodes[i]);
        }
        return newNodes;
    }

    
    // ===== STRING FORMATTING =====
    public String toText(){
        return   String.format("   Type        : " + m_nodeType + 
                             "\n   Filename    : " + m_fileName +
                             "\n   Comment     : " + m_nodeComment +
                             "\n   Study index : %1$d", m_studyIndex);
    }
    
    public static String formatSubjectName(int iSubject){
        if (iSubject == 0){
            return "Default subject (#0)";
        }else{
            return String.format("Subject #%1$d", iSubject);
        }
    }
    
    public String toHtml(){
        String strIndices;
        
        // Study date
        String strDate = "";
        if (m_modifier > 1){
            // Get day-mont-year
            int intDate = m_modifier;
            int year = intDate / (13*32);
            intDate = intDate - 13*32*year;
            int month = intDate / 32;
            intDate = intDate - 32*month;
            int day = intDate;
            // Format string
            strDate = String.format("&nbsp;&nbsp;&nbsp;&nbsp;(%02d-", day);
            switch(month){
                case 1:  strDate += "Jan"; break;
                case 2:  strDate += "Feb"; break;
                case 3:  strDate += "Mar"; break;
                case 4:  strDate += "Apr"; break;
                case 5:  strDate += "May"; break;
                case 6:  strDate += "Jun"; break;
                case 7:  strDate += "Jul"; break;
                case 8:  strDate += "Aug"; break;
                case 9:  strDate += "Sep"; break;
                case 10: strDate += "Oct"; break;
                case 11: strDate += "Nov"; break;
                case 12: strDate += "Dec"; break;
            }
            strDate += String.format("-%04d)", year + 1800);
        }
        
        // SUBJECTDB
        if(this.getType().equalsIgnoreCase("subjectdb")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Subjects directory</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // SUBJECT
        }else if(this.getType().equalsIgnoreCase("subject")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Subject</B>: </TD><TD>" + formatSubjectName(this.m_studyIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // ANATOMY
        }else if(this.getType().equalsIgnoreCase("anatomy")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>MRI</B>: </TD><TD>" + formatSubjectName(this.m_studyIndex) + String.format(" - Anatomy #%1$d", this.m_itemIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // VOLATLAS
        }else if(this.getType().equalsIgnoreCase("volatlas")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Volume atlas</B>: </TD><TD>" + formatSubjectName(this.m_studyIndex) + String.format(" - Anatomy #%1$d", this.m_itemIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";

        // SURFACES
        }else if(this.getType().equalsIgnoreCase("surface") || this.getType().equalsIgnoreCase("scalp") || this.getType().equalsIgnoreCase("cortex") ||
                 this.getType().equalsIgnoreCase("outerskull") || this.getType().equalsIgnoreCase("innerskull") || this.getType().equalsIgnoreCase("other") ||
                 this.getType().equalsIgnoreCase("fibers") || this.getType().equalsIgnoreCase("fem")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Surface</B>: </TD><TD>" + formatSubjectName(this.m_studyIndex) + String.format(" - Surface #%1$d", this.m_itemIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // STUDYDBSUBJ
        }else if(this.getType().equalsIgnoreCase("studydbsubj") || this.getType().equalsIgnoreCase("studydbcond")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Datasets directory</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // STUDY
        }else if(this.getType().equalsIgnoreCase("study")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Study</B>: </TD><TD>" + formatSubjectName(this.m_itemIndex) + String.format(" - Study #%1$d", this.m_studyIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // STUDYSUBJECT
        }else if(this.getType().equalsIgnoreCase("studysubject")){
            // If directory in subject/condition display
            if (this.m_studyIndex == 0){
                strIndices = "<HTML><TABLE><TR><TD align=right><B>Subject</B>: </TD><TD>" + formatSubjectName(this.m_itemIndex) + "</TD></TR>" + 
                             "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
            }else{
                strIndices = "<HTML><TABLE><TR><TD align=right><B>Study</B>: </TD><TD>" + formatSubjectName(this.m_itemIndex) + String.format(" - Study #%1$d", this.m_studyIndex) + strDate + "</TD></TR>" + 
                             "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
            }
        // CONDITION
        }else if(this.getType().equalsIgnoreCase("condition") || this.getType().equalsIgnoreCase("rawcondition")){
            // If directory in condition/subject display, OR a sudirectory of a subject in subject/condition display
            if (this.m_studyIndex == 0){
                strIndices = "<HTML><TABLE><TR><TD align=right><B>Condition</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
            // Else : Study node
            }else{
                strIndices = "<HTML><TABLE><TR><TD align=right><B>Study</B>: </TD><TD>" + formatSubjectName(this.m_itemIndex) + String.format(" - Study #%1$d", this.m_studyIndex) + strDate + "</TD></TR>" + 
                                          "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";            
            }
        // CHANNEL
        }else if(this.getType().equalsIgnoreCase("channel")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Channel file</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // HEADMODEL
        }else if(this.getType().equalsIgnoreCase("headmodel")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Headmodel</B>: </TD><TD>" + String.format("Study #%1$d - Headmodel #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // DATA
        }else if(this.getType().equalsIgnoreCase("data") || this.getType().equalsIgnoreCase("rawdata")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Data</B>: </TD><TD>" + String.format("Study #%1$d - Data #%2$d", this.m_studyIndex, this.m_itemIndex) + strDate + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // DATA LIST
        }else if(this.getType().equalsIgnoreCase("datalist")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Data list</B>: </TD><TD>" + String.format("Study #%1$d", this.m_studyIndex) + strDate + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // RESULTS
        }else if(this.getType().equalsIgnoreCase("results")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Results</B>: </TD><TD>" + String.format("Study #%1$d - Results #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // RESULTS KERNEL
        }else if(this.getType().equalsIgnoreCase("kernel")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Results kernel</B>: </TD><TD>" + String.format("Study #%1$d - Results #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // RESULTS LIST
        }else if(this.getType().equalsIgnoreCase("resultslist")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Results list</B>: </TD><TD>" + String.format("Study #%1$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // RESULTS-LINK
        }else if(this.getType().equalsIgnoreCase("link")){
            String listLink[] = this.m_fileName.split("[|]");
            String filename;
            if (listLink.length == 3){
                filename = listLink[1];
            }else{
                filename = this.m_fileName;
            }
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Results</B>: </TD><TD>" + String.format("Study #%1$d - Results #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + filename + "</TD></TR></TABLE></HTML>";

        // LINK
        }else if(this.getType().equalsIgnoreCase("defaultanat")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Subject uses default anatomy</B></TD></TR></TABLE></HTML>";
        // DEFAULT STUDY 
        }else if(this.getType().equalsIgnoreCase("defaultstudy")){
            // SUBJECT'S DEFAULT STUDY 
            if (this.getStudyIndex() > 0){
                strIndices = "<HTML><TABLE><TR><TD align=left><B>Default study</B>: "
                        + formatSubjectName(this.m_itemIndex) + String.format(" - Study #%1$d", this.m_studyIndex)
                        + "<BR>Channel and headmodel files shared by<BR>all the conditions of this subject.</B></TD></TR></TABLE></HTML>";
            }else{
                strIndices = "<HTML><TABLE><TR><TD align=left><B>Default study:</B> Channel and headmodel files shared <BR>by all the subjects.</B></TD></TR></TABLE></HTML>";
            }
        // IMAGE
        }else if(this.getType().equalsIgnoreCase("image") || this.getType().equalsIgnoreCase("video")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Image</B>: </TD><TD>" + String.format("Study #%1$d - Image #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" +
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // NOISECOV
        }else if(this.getType().equalsIgnoreCase("noisecov")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Noise covariance matrix</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";

        // DIPOLES
        }else if(this.getType().equalsIgnoreCase("dipoles")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Dipoles</B>: </TD><TD>" + String.format("Study #%1$d - Dipoles #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" +
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // TIMEFREQ
        }else if(this.getType().equalsIgnoreCase("timefreq")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Time-frequency map</B>: </TD><TD>" + String.format("Study #%1$d - Timefreq #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" +
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // SPECTRUM
        }else if(this.getType().equalsIgnoreCase("spectrum")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Spectrum</B>: </TD><TD>" + String.format("Study #%1$d - Timefreq #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" +
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // MATRIX
        }else if(this.getType().equalsIgnoreCase("matrix")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Matrix</B>: </TD><TD>" + String.format("Study #%1$d - Matrix #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" +
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // MATRIX LIST
        }else if(this.getType().equalsIgnoreCase("matrixlist")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Matrix list</B>: </TD><TD>" + String.format("Study #%1$d", this.m_studyIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // PDATA
        }else if(this.getType().equalsIgnoreCase("pdata")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Stat on recordings</B>: </TD><TD>" + String.format("Study #%1$d - Stat #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" + 
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // PRESULTS
        }else if(this.getType().equalsIgnoreCase("presults")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Stat on sources</B>: </TD><TD>" + String.format("Study #%1$d - Stat #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" +
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // PTIMEFREQ
        }else if (this.getType().equalsIgnoreCase("ptimefreq") || this.getType().equalsIgnoreCase("pspectrum") || this.getType().equalsIgnoreCase("philbert") || this.getType().equalsIgnoreCase("pconnect1") || this.getType().equalsIgnoreCase("pconnectn") || this.getType().equalsIgnoreCase("ppac")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Stat on timefreq</B>: </TD><TD>" + String.format("Study #%1$d - Stat #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" +
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // PMATRIX
        }else if(this.getType().equalsIgnoreCase("pmatrix")){
            strIndices = "<HTML><TABLE><TR><TD align=right><B>Stat on matrix</B>: </TD><TD>" + String.format("Study #%1$d - Stat #%2$d", this.m_studyIndex, this.m_itemIndex) + "</TD></TR>" +
                                      "<TR><TD align=right><B>File</B>: </TD><TD>" + this.m_fileName + "</TD></TR></TABLE></HTML>";
        // DEFAULT STRING
        }else{
            strIndices = String.format(
                    "<HTML><TABLE>" +
                    "<TR><TD align=right><B>Study index:</B></TD><TD>%1$d</TD></TR>" +
                    "<TR><TD align=right><B>Item index:</B></TD><TD>%2$d</TD></TR>" +
                    "<TR><TD align=right><B>File:</B></TD><TD>" + m_fileName + "</TD></TR>" +
                    "</TABLE></HTML>", m_studyIndex, m_itemIndex);
        }
        
        return strIndices;
    }
    
    
    // To disable one of the search parameter, set it to an ignored value :
    //    - fType  : ""
    //    - fStudy : -1
    //    - fItem  : -1
    static boolean testNode(BstNode node, String fType, int fStudy, int fItem){
        boolean isOk = false;
        if ((fType.length() == 0) || fType.equalsIgnoreCase(node.getType())){
            if ((fStudy == -1) || (fStudy == node.getStudyIndex())){
                if ((fItem == -1) || (fItem == node.getItemIndex())){
                    isOk = true;
                }
            }
        }
        return isOk;
    }
            
    // ===== FIND NODE BY TYPE/INDICE =====
    public BstNode findChild(String fType, int fStudy, int fItem,  boolean isRecursive){
        BstNode foundNode = null;
        BstNode currentNode = null;
        // Recursive search : search the whole subtree rooted in the current node
        if (isRecursive){
            Enumeration nodesList = this.breadthFirstEnumeration();
            while ((foundNode == null) && nodesList.hasMoreElements()){
                currentNode = (BstNode) nodesList.nextElement();
                if (testNode(currentNode, fType, fStudy, fItem)){
                    foundNode = currentNode;
                }
            }
                
        // Linear search : search only in the first-order children
        }else{
            int iChild = 0;
            while ((foundNode == null) && (iChild < this.getChildCount())){
                currentNode = (BstNode) this.getChildAt(iChild);
                if (testNode(currentNode, fType, fStudy, fItem)){
                    foundNode = currentNode;
                }else{
                    iChild++;
                }
            }
        }
        
        return foundNode;
    }

    // ===== FIND NODE BY COMMENT =====
    public BstNode findChild(String fComment,  boolean isRecursive){
        BstNode foundNode = null;
        BstNode currentNode = null;
        // Recursive search : search the whole subtree rooted in the current node
        if (isRecursive){
            Enumeration nodesList = this.breadthFirstEnumeration();
            while ((foundNode == null) && nodesList.hasMoreElements()){
                currentNode = (BstNode) nodesList.nextElement();
                if (currentNode.getComment().equalsIgnoreCase(fComment) ){
                    foundNode = currentNode;
                }
            }

        // Linear search : search only in the first-order children
        }else{
            int iChild = 0;
            while ((foundNode == null) && (iChild < this.getChildCount())){
                currentNode = (BstNode) this.getChildAt(iChild);
                if (currentNode.getComment().equalsIgnoreCase(fComment) ){
                    foundNode = currentNode;
                }else{
                    iChild++;
                }
            }
        }

        return foundNode;
    }

    public BstNode findAncestor(String fType, int fStudy, int fItem){
        BstNode foundNode = null;
        BstNode currentNode = this;
        while ((currentNode != null) && !testNode(currentNode, fType, fStudy, fItem)){
            currentNode = (BstNode) currentNode.getParent();
        }
        return currentNode;
    }


    // ========================================================================
    // === SORT CHILDREN =====================================================
    public boolean compareChildren(int i1, int i2){
        BstNode n1 = (BstNode) this.getChildAt(i1);
        BstNode n2 = (BstNode) this.getChildAt(i2);
        if (n1.getComment().compareToIgnoreCase(n2.getComment()) <= 0){
            return true;
        }else{
            return false;
        }
    }

    public void swapChildren(int i1, int i2){
        // Make sure that i1 is smaller than i2
        if (i1 > i2){
            int tmp = i1;
            i1 = i2;
            i2 = tmp;
        }
        // Get both children
        BstNode n1 = (BstNode) this.getChildAt(i1);
        BstNode n2 = (BstNode) this.getChildAt(i2);
        // Remove both children
        this.remove(i2);
        this.remove(i1);
        // Insert them again, in the other order
        this.insert(n2, i1);
        this.insert(n1, i2);
    }

    public void sortChildren(){
        int i = 1;
        int j = 2;
        while (i < getChildCount()){
            if (compareChildren(i-1, i)){
                i = j;
                j = j + 1;
            }else{
                swapChildren(i-1, i);
                i = i - 1;
                if (i == 0){
                    i = j;
                    j = j + 1;
                }
            }
        }
    }
}

