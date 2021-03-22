/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.importserver;

import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


/**
 *
 * @author Norma
 */
public class SaveInFile {
    
    public static String saveUserRecord(String username, String lastname, String email) throws SQLException, IOException
    {
        System.out.println("Will save in file next");
        String path = "";
        
        Calendar calendar = Calendar.getInstance();
        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
     

        try{
            // Create new file
            //String content = "This is the content to write into create file";
            String content = username + " " + lastname + " " + email + " " + startDate + "\n";  
            System.out.println("content is: " + " " +  content);

            path = "/var/lib/tomcat8/logs/import_server.txt";
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write in file
            bw.write(content);

            // Close connection
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    
        String return_message = "Your information was successfully saved in this file: " + " " + path;  
	return (return_message);

    }
}
