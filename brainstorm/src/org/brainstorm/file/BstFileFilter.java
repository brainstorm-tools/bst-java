package org.brainstorm.file;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * @author Francois Tadel
 * @author Raymundo Cassani
 */
public class BstFileFilter extends FileFilter {
    String  []suffixes;
    String  description;
    String  formatName;

   public BstFileFilter(String []suffixes, String description, String formatName){
        this.suffixes    = suffixes;
        this.description = description;
        this.formatName  = formatName;
   }

   public BstFileFilter(String []suffixes, String description){
        this.suffixes    = suffixes;
        this.description = description;
        this.formatName  = new String("");
   }

   boolean appartient( String suffixe ){
      for( int i = 0; i<suffixes.length; ++i){
          if(suffixe.equals(suffixes[i])) return true;
      }
      return false;
   }

   public boolean accept(File f) {
     if (f.isDirectory()) {
         return true;
     }
     // ==== Get file extension ====
     String fileExt  = null;
     String fileName = f.getName();
     // Get index of the last '.' in the filename
     int indexDot = fileName.lastIndexOf('.');
     // If file has an extension
     if ((indexDot > 0) &&  (indexDot < fileName.length() - 1)) {
         fileExt = fileName.substring(indexDot);
     }else{
         //  No extension : file not accepted
         fileExt = "";
     }
     
     // ==== Check all suffixes ====
     boolean isAccepted = false;
     int i = 0;
     // File must be accepted by at least one of the suffixes.
     // There are three types of suffixes, depending on the character they start with:
     //    - '.' : look for an extension
     //    - '_' : look for a tag (a part of the filename and a '.mat' extension
     //    - Else: look for a whole file name (extension included)
     while (!isAccepted && (i < this.suffixes.length)){
        // BST TAG
         if (this.formatName.equalsIgnoreCase("bst") && (this.suffixes[i].charAt(0) == '_') && (!this.suffixes[i].contains("."))){
             // File extention must be '.mat' and FileName must contain suffixe
             if ((fileExt.equalsIgnoreCase(".mat")) && (fileName.toLowerCase().indexOf(this.suffixes[i].substring(1).toLowerCase()) != -1)){
                 isAccepted = true;
             }
         // EXTENSION
         }else if ((this.suffixes[i].charAt(0) == '.') || (this.suffixes[i].charAt(0) == '_')){
             // suffixe extension must match the file extension (or can be '*')
             if (fileExt.equalsIgnoreCase(this.suffixes[i]) || (this.suffixes[i].charAt(1) == '*') || fileName.endsWith(this.suffixes[i])) {
                 isAccepted = true;
             }
         // ANYTHING
         }else if (this.suffixes[i].charAt(0) == '*'){
             isAccepted = true;
         }
         // SPECIFIC FILENAME
         else{
             if (fileName.equals(this.suffixes[i])){
                 isAccepted = true;
             }
         }
         i = i + 1;
     }
     
     return isAccepted;
   }

   public String getDescription() {
       return description; 
   }
   
   public String getFormatName(){
        return this.formatName;
   }
   
   public String[] getSuffixes(){
       return this.suffixes;
   }
}

