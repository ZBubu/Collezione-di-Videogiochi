/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collezionegiochi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Scanner;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URLEncoder;

//MYSQL IMPORTS
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
        public static String DBQuery(String query, String[] campi ){
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
            String str=query;
            ResultSet rset= stmt.executeQuery(str);
            System.out.println("I record sono:");
            int rowCount=0;
            String risultato="";
            while(rset.next()) {   // Repeatedly process each row
                for(int i=0;i<campi.length;i++){
                risultato += rset.getString(campi[i]);
                }
                ++rowCount;
                System.out.println(risultato);
             }
            return risultato;
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                return ex.getMessage();
            }
        }
        
    public static void main(String args[]){
        String[] a={"titolo"};
        String b= "select * from gioco";
        DBQuery(b,a);
            try {
                httpRequest();
            } catch (IOException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
//+URLEncoder.encode("?api_key=moby_cCHC70MMaHmCQWg58WH2v4Xne4K", "UTF-8")
    public static void httpRequest() throws IOException{
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.mobygames.com/v1/platforms?api_key="+
                                URLEncoder.encode("moby_cCHC70MMaHmCQWg58WH2v4Xne4K", "UTF-8")))
                        .build();
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                System.out.println("response body: " + response.body());
            } catch (InterruptedException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

}
