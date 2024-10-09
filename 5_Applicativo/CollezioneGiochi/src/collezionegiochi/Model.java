/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collezionegiochi;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


import java.sql.*;
public class Model {
    public static void main(String args[]){
        
    String urldb = "jdbc:mysql://localhost:3306/progettogiochi";
    String username = "sidney";
    String password = "Admin";
    Connection c;
    try{
        //Connessione
        c= DriverManager.getConnection(urldb, username, password);
        //Creazione statement
        Statement stmt= c.createStatement();
        //Creazione Query SQL
        String str="select * from gioco";
        //Continuare con https://www3.ntu.edu.sg/home/ehchua/Programming/java/JDBC_Basic.html
        ResultSet rset= stmt.executeQuery(str);
    }catch(Exception ex){
        System.out.println(ex.getMessage());
    }
    }
    private URL url;
    private String apiKey;
    /*
    try{
        Scanner sc = new Scanner(new File("../../"));
        
    }
    catch(Exception e){
    
}

    public void httpRequest(){
        try{
        url = new URL("https://api.mobygames.com/v1/games?api_key");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        HttpUrlConnection con = (HttpURLConnection) url.openConnection();
        
    }
*/
}
