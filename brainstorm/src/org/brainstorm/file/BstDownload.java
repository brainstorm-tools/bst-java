package org.brainstorm.file;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import se.datadosen.component.RiverLayout;

/**
 * @author Francois Tadel
 */
public class BstDownload {
    private static final int MAX_BUFFER_SIZE = 1024; // Max size of download buffer.
    private URL url;            // Download URL
    private String outputFile;  // Destination file
    private JFrame jDialog;    // Progressbar
    private JLabel jLabel;
    private JProgressBar jProgressBar;
    private boolean isDownloading = false;
    private int result = -1;
    private String message = "Error: Could not read distant file.";
    private Exception error = null;
    private Thread threadDownload = null;

    // Constructor
    public BstDownload(String strUrl, String file, String title){
        // Create interface
        init(title);

        // ===== COPY ARGUMENTS =====
        // Output filename
        outputFile = file;
        // Input URL
        try{
            url = new java.net.URL(strUrl);
        }
        catch (Exception e){
            jLabel.setText("Error: Invalid URL.");
        }
    }

    // Create interface
    public void init(String title){
        // ===== CREATE DIALOG =====
        // Create progress bar dialog
        //jDialog = new JDialog((JFrame) null, "Brainstorm update", false);
        jDialog = new JFrame(title);
        jDialog.setAlwaysOnTop(true);
        jDialog.setResizable(false);
        jDialog.setPreferredSize(new Dimension(350, 130));
        // Create main progress bar panel
        jLabel = new JLabel("Connecting to server...");
        jProgressBar = new JProgressBar(0, 99);
        jProgressBar.setStringPainted(true);
        JPanel jPanel = new JPanel(new RiverLayout());
        jPanel.add("p hfill vfill", jLabel);
        jPanel.add("p hfill", jProgressBar);
        // Add the main Panel
        jDialog.getContentPane().add(jPanel);
        jDialog.pack();
        // Set window location
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
        jDialog.getContentPane().repaint();
        // Set icon
        jDialog. setIconImage(org.brainstorm.icon.IconLoader.ICON_APP.getImage());
        // Set closing callback
        //Dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    close();
                }
        });
    }

    // Destructor
    public void close(){
        // Interrupt download thread
        //if ((threadDownload != null) && (threadDownload.isAlive())){
        //    threadDownload.interrupt();
        //}
        // Close window
        //jDialog.dispose();
        result = 0;
        isDownloading = false;
    }

    // Set text
    public void setText(String newText){
        jLabel.setText(newText);
    }

    // Get result
    public int getResult(){
        return result;
    }
    public String getMessage(){
        return message;
    }
    public Exception getError(){
        return error;
    }

    // Download thread (no proxy)
    public void download(){
        download(null);
    }

    // Download file.
    public void download(final Proxy proxy){
        threadDownload = new Thread() {
            public void run(){
                downloadThread(proxy);
            }
        };
        // Start thread
        threadDownload.start();
    }
    
    // Download thread
    public void downloadThread(Proxy proxy){
        OutputStream file = null;
        InputStream stream = null;
        int downloaded = 0;
        
        try {
            int attempts = 0;
            int contentLength;
            HttpURLConnection connection;
            
            // Sometimes it takes 2 tries to correctly start the connection.
            while (true) {
                connection = downloadAttempt(proxy);
            
                // Check for valid content length.
                contentLength = connection.getContentLength();
                attempts++;
                
                if (contentLength >= 1) {
                    break;
                } else if (attempts > 1) {
                    message = "Error: File is empty.";
                    throw new Exception("FileNotFound");
                }                
            }

            // Set progress bar maximum
            jLabel.setText("Downloading...");
            jProgressBar.setMaximum(contentLength);

            // Open output file
            file = new FileOutputStream(outputFile);
            stream = connection.getInputStream();
            isDownloading = true;

            while (isDownloading) {
                // Size buffer according to how much of the file is left to download.
                byte buffer[];
                if (contentLength - downloaded > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[contentLength - downloaded];
                }
                // Read from server into buffer.
                int read = stream.read(buffer);
                if (read <= 0){
                    break;
                }
                // Write buffer to file.
                file.write(buffer, 0, read);
                downloaded += read;
                // Update progress bar
                jProgressBar.setValue(downloaded);
            }
            // File downloaded successfully
            if (isDownloading){
                result = 1;
                message = "All files downloaded successfully.";
            } else {
                message = "Operation interrupted by user.";
            }
            
        } catch (Exception e) {
            result = 0;
            error = e;
        } finally {
            // Close file.
            if (file != null) {
                try {
                    file.close();
                } catch (Exception e) {}
            }
            // Close connection to server.
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {}
            }
            // Close window
            jDialog.dispose();
        }
    }
    
    private HttpURLConnection downloadAttempt(Proxy proxy) throws Exception {
        // Open connection to URL
        HttpURLConnection connection;
        if (url.getProtocol().equals("https")) {
            HttpsURLConnection tlsConnection;

            if (proxy != null){
                tlsConnection = (HttpsURLConnection) url.openConnection(proxy);
            } else {
                tlsConnection = (HttpsURLConnection) url.openConnection();
            }

            // If https is requested, ensure we use latest TLS version since
            // some websites disable older versions for security issues.
            
            // This is only supported in Java v1.7+
            double version = Double.parseDouble(System.getProperty("java.specification.version"));
            if (version >= 1.7) {
                SSLContext ssl = SSLContext.getInstance("TLSv1.2");
                ssl.init(null, null, new SecureRandom());
                tlsConnection.setSSLSocketFactory(ssl.getSocketFactory());
            } else {
                // GitHub requires the latest TLS version.
                if (url.getHost().equals("github.com")) {
                    message = "HTTPS connections to GitHub require Java 1.7. Please update Java.";
                    throw new Exception("ConnectionError");
                }
                
                // Create a trust all certificate checker because early Java 1.6
                // versions are too strict for our own neuroimage.usc.edu domain
                TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                    }
                };
                
                SSLContext ssl = SSLContext.getInstance("SSL");
                ssl.init(null, trustAllCerts, null);
                tlsConnection.setSSLSocketFactory(ssl.getSocketFactory());
            }

            connection = tlsConnection;
        } else {
            if (proxy != null){
                connection = (HttpURLConnection) url.openConnection(proxy);
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }
        }

        // Specify what portion of file to download.
        connection.setRequestProperty("Range", "bytes=0-");
        // Connect to server.
        try {
            connection.connect();
            Thread.sleep(200);
        } catch (Exception ec) {
            message = "Cannot open http connection.\nCheck the Matlab proxy configuration (Preferences > Web).";
            throw new Exception("ConnectionError");
        }
        // Make sure response code is in the 200 range.
        if (connection.getResponseCode() / 100 != 2) {
            message = "Error: File to download does not exist.";
            throw new Exception("FileNotFound");
        }
        
        return connection;
    }
}
