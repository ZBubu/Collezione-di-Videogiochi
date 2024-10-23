/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collezionegiochi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Scanner;
import org.json.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URLEncoder;

import java.net.ProxySelector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;
import java.io.Reader;
import java.nio.file.Files;

//MYSQL IMPORTS
import java.sql.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject; 
import org.json.simple.JSONValue;

public class Model {

    public static String DBQuery(String query, String[] campi) {
        String urldb = "jdbc:mysql://localhost:3306/progettogiochi";
        String username = "sidney";
        String password = "Admin";
        Connection c;
        try {
            //Connessione
            c = DriverManager.getConnection(urldb, username, password);
            //Creazione statement
            Statement stmt = c.createStatement();
            //Creazione Query SQL
            String str = query;
            ResultSet rset = stmt.executeQuery(str);
            System.out.println("I record sono:");
            int rowCount = 0;
            String risultato = "";
            while (rset.next()) {   // Repeatedly process each row
                for (int i = 0; i < campi.length; i++) {
                    risultato += rset.getString(campi[i]);
                }
                ++rowCount;
                System.out.println(risultato);
            }
            return risultato;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    public static void main(String args[]) {
        /*
        String[] a={"titolo"};
        String b= "select * from gioco";
        DBQuery(b,a);
         */
        try {
            httpRequest("https://api.mobygames.com/v1/games?api_key=moby_cCHC70MMaHmCQWg58WH2v4Xne4K&limit=1");
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//URLEncoder.encode("api_key=moby_cCHC70MMaHmCQWg58WH2v4Xne4K", "UTF-8")
//https://api.mobygames.com/v1/platforms?api_key=moby_cCHC70MMaHmCQWg58WH2v4Xne4K

    public static void httpRequest(String URL) throws IOException {
        try {

            HttpClient proxy = HttpClient.newBuilder()
                    .proxy(ProxySelector.of(new InetSocketAddress("localhost", 5865)))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .build();

            HttpResponse<String> response = proxy.send(request, BodyHandlers.ofString());
            String responseStr= response.body();
            
            Object file = JSONValue.parse(responseStr);
            JSONObject jo = (JSONObject)file;
            jo.get("alternate_titles");


            
            //System.out.println("response body: " + response.body());
        } catch (InterruptedException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
