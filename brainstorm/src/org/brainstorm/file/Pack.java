package org.brainstorm.file;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.*;

/**
 * @author Francois Tadel
 */
public class Pack {
    static ZipOutputStream cpZipOutputStream = null;
    static String strSource = "";
    static String strTarget = "";
    static long size = 0;
    static int numOfFiles = 0;
    static String strZipPath;


    static public boolean zip(String src, String dest){
        return zip(src, dest, null);
    }
    static public boolean zip(String src[], String dest){
        return zip(src, dest, null);
    }
    static public boolean zip(String src, String dest, String basePath[]){
        return zip(src, dest, basePath, true);
    }
    static public boolean zip(String src[], String dest, String basePath[]){
        return zip(src, dest, basePath, true);
    }
    static public boolean zip(String src, String dest, String basePath[], boolean isOutput) {
        String src_list[] = new String[1];
        src_list[0] = src;
        return zip(src_list, dest, basePath, isOutput);
    }

    static public boolean zip(String src[], String dest, String basePath[], boolean isOutput) {
        strTarget = dest;
        size = 0;
        numOfFiles = 0;
        
        try {
            FileOutputStream fos = new FileOutputStream(strTarget);
            cpZipOutputStream = new ZipOutputStream(fos);
            cpZipOutputStream.setLevel(9);
            for (int i=0; i<src.length; i++){
                strSource = src[i];
                File cpFile = new File(strSource);
                if (!cpFile.isFile() && !cpFile.isDirectory()) {
                    System.out.println("\nSource file/directory Not Found!");
                    return false;
                }
                // If source is a directory: keep its name at the beginning of all zipped files
                if ((basePath != null) && (i < basePath.length) && (basePath[i].length() > 0)){
                    strZipPath = basePath[i];
                }else{
                    strZipPath = "";
                }
                if (cpFile.isDirectory()){
                    if (strZipPath.length() > 0){
                        strZipPath = strZipPath + File.separator + cpFile.getName();
                    }else{
                        strZipPath = cpFile.getName();
                    }
                }
                zipFiles(cpFile);
            }
            cpZipOutputStream.finish();
            cpZipOutputStream.close();
            if (isOutput){
                System.out.println("Finished creating zip file " + strTarget);
                System.out.println("Total of  " + numOfFiles + " files are zipped (" + (size / 1024) + " Kb).");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static void zipFiles(File cpFile) {
        int byteCount;
        final int DATA_BLOCK_SIZE = 2048;
        FileInputStream cpFileInputStream;


        if (cpFile.isDirectory()) {
            if (cpFile.getName().equalsIgnoreCase(".metadata")) { //if directory name is .metadata, skip it.					
                return;
            }
            File[] fList = cpFile.listFiles();
            for (int i = 0; i < fList.length; i++) {
                zipFiles(fList[i]);
            }
        } else {
            try {
                if (cpFile.getAbsolutePath().equalsIgnoreCase(strTarget)) {
                    return;
                }
                //System.out.println("Zipping " + cpFile);
                size += cpFile.length();
                //String strAbsPath = cpFile.getAbsolutePath();								
                numOfFiles++;
                String strAbsPath = cpFile.getPath();
                String strZipEntryName;
                if (strAbsPath.length() > strSource.length()){
                    strZipEntryName = strZipPath + File.separator + strAbsPath.substring(strSource.length() + 1, strAbsPath.length());
                }else if(strZipPath.length() > 0){
                    strZipEntryName = strZipPath + File.separator + cpFile.getName();
                }else{
                    strZipEntryName = cpFile.getName();
                }
                //byte[] b = new byte[ (int)(cpFile.length()) ];

                cpFileInputStream = new FileInputStream(cpFile);
                ZipEntry cpZipEntry = new ZipEntry(strZipEntryName);
                cpZipOutputStream.putNextEntry(cpZipEntry);

                byte[] b = new byte[DATA_BLOCK_SIZE];
                while ((byteCount = cpFileInputStream.read(b, 0, DATA_BLOCK_SIZE)) != -1) {
                    cpZipOutputStream.write(b, 0, byteCount);
                }

                //cpZipOutputStream.write(b, 0, (int)cpFile.length());
                cpZipOutputStream.closeEntry();
                
                cpFileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


