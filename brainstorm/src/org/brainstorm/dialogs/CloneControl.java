package org.brainstorm.dialogs;

import java.net.*;
import java.io.*;

/**
 * @author Francois Tadel
 */
public class CloneControl {
    // Checks for the username/email and password of a user registered on the Brainstorm website
    public static int probe(String BrainstormHomeDir){
        String response = null;
        int status = -1;

        // Check if there are any GIT files in the folder
        if (!(new File(BrainstormHomeDir + File.separator + ".git").exists()) && 
            !(new File(BrainstormHomeDir + File.separator + "LICENSE").exists()) && 
            !(new File(BrainstormHomeDir + File.separator + "README.md").exists())){
            status = 1;
            return status;
        }

        // Allow multiple trials
        while (status == -1) {
            // Get username and password
            String [] list = {"<HTML>You got this version of Brainstorm from GitHub.<BR><BR>" + 
                    "Please take a minute to register on our website before using the software:<BR>" +
                    "https://neuroimage.usc.edu/brainstorm > Download > Create an account now<BR><BR><FONT color='#999999'>" + 
                    "This project is supported by public grants. Keeping track of usage demographics <BR>" +
                    "is key to document the impact of Brainstorm and obtain continued support.<BR>" + 
                    "Please take a moment to create a free account - thank you.</FONT><BR><BR>" +
                    "Email or username: ", "Password: "};
            String [] defaults = {"", ""};
            String [] res = MsgServer.dlgInput(null, list, "Brainstorm login", defaults);       
            // If user aborted
            if ((res == null) || (res[0].length() == 0)){
                status = 0;
                return status;
            }

            // Contact server
            try {
                // Create HTTP connection
                URL url = new URL("https://neuroimage.usc.edu/bst/check_user.php");
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection)con;
                http.setRequestMethod("POST");
                http.setDoOutput(true);

                // Create POST data
                byte[] out = ("email=" + URLEncoder.encode(res[0], "UTF-8") + "&mdp=" + URLEncoder.encode(res[1], "UTF-8")).getBytes();
                // Submit HTTP request
                http.setFixedLengthStreamingMode(out.length);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                http.connect();
                OutputStream os = http.getOutputStream();
                os.write(out);

                // Get results
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                response = in.readLine();
                in.close();
            } catch (IOException e) {
                status = 0;
            } 

            // Check server response
            if (response == null) {
                MsgServer.dlgError(null, "Cannot connect to https://neuroimage.usc.edu", "Identification error");
                status = 0;
            }else if (response.compareTo("1") == 0){
                status = 1;
            } else {
                MsgServer.dlgError(null, "Invalid username or password", "Identification error");
            }
        }
        return status;
    }
}
