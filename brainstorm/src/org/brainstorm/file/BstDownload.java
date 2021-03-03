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
import org.apache.commons.net.ftp.*;

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
    private int contentLength = 0;
    private InputStream contentStream = null;

    // Constructors
    public BstDownload(String strUrl, String file, String title, String imgFile){
        // Create interface
        init(title, imgFile);

        // ===== COPY ARGUMENTS =====
        // Output filename
        this.outputFile = file;
        // Input URL
        try{
            this.url = createUrl(strUrl);
        }catch (Exception e){
            System.out.println("Error: Invalid URL.");
        }
    }
    
    public BstDownload(String strUrl, String file, String title){
        this(strUrl, file, title, null);
    }

    // Create interface
    public void init(String title, String imgFile){
        // ===== CREATE DIALOG =====
        // Create progress bar dialog
        //jDialog = new JDialog((JFrame) null, "Brainstorm update", false);
        jDialog = new JFrame(title);
        jDialog.setAlwaysOnTop(true);
        jDialog.setResizable(false);
        if (imgFile == null){
            jDialog.setPreferredSize(new Dimension(350, 130));
        }
        // Create main progress bar panel
        jLabel = new JLabel("Connecting to server...");
        jProgressBar = new JProgressBar(0, 99);
        jProgressBar.setStringPainted(true);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        JLabel jImage = new JLabel();
    
        // Generic constraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.weightx = 1;
        // IMAGE
        c.gridy = 1;
        if (imgFile != null){
            c.weighty = 1;
            jImage.setIcon(new ImageIcon(imgFile));
        }else{
            c.weighty = 0;
        }
        c.insets = new Insets(0,0,0,0);
        jPanel.add(jImage, c);
        // TEXT
        c.gridy = 2;
        if (imgFile != null){
            c.weighty = 0;
        }else{
            c.weighty = 1;
        }
        c.insets = new Insets(5,12,5,12);
        jPanel.add(jLabel, c);
        // PROGRESS BAR
        c.gridy = 3;
        c.weighty = 0;
        c.insets = new Insets(0,12,9,12);
        jPanel.add(jProgressBar, c);

        // Add the main Panel
        jDialog.getContentPane().add(jPanel);
        jDialog.pack();
        // Set window location
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
        jDialog.getContentPane().repaint();
        // Set icon
        jDialog.setIconImage(org.brainstorm.icon.IconLoader.ICON_APP.getImage());
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
        FTPClient ftp = null;
        int downloaded = 0;
        try {
            // Open download stream
            if (url.getProtocol().equalsIgnoreCase("http") || url.getProtocol().equalsIgnoreCase("https")){
                openStreamHttp(proxy);
            } else if (url.getProtocol().equalsIgnoreCase("ftp")) {
                ftp = openStreamFtp();
            } else {
                throw new Exception("Unsupported protocol: " + url.getProtocol());
            }
            
            // Set progress bar maximum
            jLabel.setText("Downloading...");
            jProgressBar.setMaximum(contentLength);
            // Open output file
            file = new FileOutputStream(outputFile);
            
            // Loop to read file by blocks of MAX_BUFFER_SIZE
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
                int read = contentStream.read(buffer);
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
            if (contentStream != null) {
                try {
                    contentStream.close();
                } catch (Exception e) {
                    System.out.println("Cannot close stream: " + e.getMessage());
                }
            }
            // Close FTP connection
            if (ftp != null){
                try {
                    boolean success = ftp.completePendingCommand();
                    if (!success){
                        result = 0;
                        error = new Exception("FTP download was interrupted.");
                    }
                    if (ftp.isConnected()) {
                        ftp.disconnect();
                    } 
                } catch (Exception e) {
                    result = 0;
                    error = e;
                }
            }
            // Close window
            jDialog.dispose();
        }
    }
    
    
    private FTPClient openStreamFtp() throws Exception{
        // Connect to FTP server
        FTPClient ftp = new FTPClient();
        if (url.getPort() > 0){
            ftp.connect(url.getHost(), url.getPort());
        } else {
            ftp.connect(url.getHost());
        }
        // Check connection
        int reply = ftp.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)) {
            throw new Exception("FTP server refused connection.");
        }
        // Login anonymously
        if (!ftp.login("anonymous", "anonymous")){
            throw new Exception("Invalid login.");
        }
        // Configure connection
        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        // Get file size
        FTPFile[] files = ftp.listFiles(url.getPath());
        if ((files.length != 1) || !files[0].isFile()){
            throw new Exception("File not found on server: " + url.getPath());
        }
        contentLength = (int) files[0].getSize();
        // Open target file
        contentStream = ftp.retrieveFileStream(url.getPath());
        return ftp;
    }
    
    
    private void openStreamHttp(Proxy proxy) throws Exception{
        HttpURLConnection connection;
        // Sometimes it takes 2 tries to correctly start the connection.
        int attempts = 0;
        while (true) {
            connection = openConnectionHttp(proxy);
            // Check for valid content length.
            this.contentLength = connection.getContentLength();
            attempts++;

            if (this.contentLength >= 1) {
                break;
            } else if (attempts > 1) {
                message = "Error: File is empty.";
                throw new Exception("FileNotFound");
            }                
        }
        this.contentStream = connection.getInputStream();
    }

    
    private HttpURLConnection openConnectionHttp(Proxy proxy) throws Exception {
        // Open connection to URL
        HttpURLConnection connection;
        try {
            connection = openHttpsConnection(url, proxy);
        } catch (IllegalArgumentException e) {
            message = "HTTPS connections to GitHub require Java 1.7 (Matlab >= 2013b).\nPlease update Java or Matlab.";
            throw new Exception("ConnectionError");
        }

        // Specify what portion of file to download.
        connection.setRequestProperty("Range", "bytes=0-");
        // Connect to server.
        try {
            connection.connect();
            Thread.sleep(200);
        } catch (Exception ec) {
            message = "Cannot open http connection.\nCheck the Matlab proxy configuration (Preferences > Web).\nHTTPS connections might require Java 1.7 (Matlab >= 2013b).";
            throw new Exception("ConnectionError");
        }
        // Make sure response code is in the 200 range.
        if (connection.getResponseCode() / 100 != 2) {
            message = "Error: File to download does not exist.";
            throw new Exception("FileNotFound");
        }
        return connection;
    }
    
    public static URL createUrl(String strUrl) throws MalformedURLException
    {
        // Old versions of Matlab default to a weird ICE library URL Handler
        // that causes HTTPS issues. Force usage of proper Handler if possible.
        URLStreamHandler handler = null;
        if (strUrl.toLowerCase().startsWith("https"))
        {
            try {
                handler = new sun.net.www.protocol.https.Handler();
            } catch (Exception e) {
                handler = null;
            }
        }
        return new java.net.URL(null, strUrl, handler);
    }
    
    public static HttpURLConnection openHttpsConnection(URL url, Proxy proxy) throws Exception
    {
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
                    throw new IllegalArgumentException("GitHubError");
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
        
        return connection;
    }
}
