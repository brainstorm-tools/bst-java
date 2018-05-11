package org.brainstorm.file;

import java.io.*;
import java.util.zip.*;

/**
 * @author Francois Tadel
 */
public class Unpack {

    public static int sChunk = 8192;

    // ===== GUNZIP =====
    public static boolean gunzip(String zipname, String dest) {
        GZIPInputStream zipin;
        boolean status = true;
        try {
            FileInputStream in = new FileInputStream(zipname);
            zipin = new GZIPInputStream(in);
        } catch (IOException e) {
            System.out.println("Couldn't open " + zipname + ".\n");
            e.printStackTrace();
            return false;
        }
        byte[] buffer = new byte[sChunk];
        // decompress the file
        try {
            FileOutputStream out = new FileOutputStream(dest);
            int length;
            while ((length = zipin.read(buffer, 0, sChunk)) != -1) {
                out.write(buffer, 0, length);
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Couldn't decompress " + zipname + ".");
            status = false;
        }
        try {
            zipin.close();
        } catch (IOException e) {
            status = false;
        }
        return status;
    }

    // ===== UNZIP =====
    public static boolean unzip(String zipname, String destination) {
        ZipEntry zipEntry = null;
        FileOutputStream fout = null;
        ZipInputStream zin;
        boolean status = true;
        try {
            FileInputStream in = new FileInputStream(zipname);
            zin = new ZipInputStream(in);
        } catch (IOException e) {
            System.out.println("Couldn't open " + zipname + ".");
            return status;
        }

        byte[] buffer = new byte[4096];
        try {
            while ((zipEntry = zin.getNextEntry()) != null) {

                long ts = zipEntry.getTime();
                // the zip entry needs to be a full path from the
                // searchIndexDirectory... hence this is correct

                File f = new File(destination, zipEntry.getName());

                f.getParentFile().mkdirs();

                fout = new FileOutputStream(f);
                int len;
                while ((len = zin.read(buffer)) > 0) {
                    fout.write(buffer, 0, len);
                }
                zin.closeEntry();
                fout.close();
                f.setLastModified(ts);
            }
        } catch (IOException e) {
            System.out.println("Couldn't decompress " + zipname + ".");
            status = false;
        }

        try {
            fout.close();
        } catch (IOException e) {
            status = false;
        }
        return status;
    }
}



