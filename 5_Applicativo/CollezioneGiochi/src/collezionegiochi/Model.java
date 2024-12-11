/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collezionegiochi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.ProxySelector;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

public class Model {
    private static final String urldb = "jdbc:mysql://localhost:3306/progettogiochi";
    private static final String username = "sidney";
    private static final String password = "Admin";
    
    public static void DBInsert(String query){
        Connection c = null;
        Statement stmt = null;
        int n = 0;
        try {
            System.out.println("Query eseguita: "+query);
            //Connessione
            c = DriverManager.getConnection(urldb, username, password);
            //Creazione statement
            stmt = c.createStatement();
            n= stmt.executeUpdate(query);                
            System.out.println("Sono state cambiate "+n+" righe.") ;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally{            
            if(stmt != null){
                try{
                    stmt.close();
                } catch(SQLException sqlEx){ }
            }
            stmt = null;
        }
    }
    
    public static ResultSet DBQuery(String query) {
        Connection c=null;
        ResultSet rset= null;
        try {
            //Connessione
            c = DriverManager.getConnection(urldb, username, password);
            //Creazione statement
            Statement stmt = c.createStatement();
            //Creazione Query SQL
             rset = stmt.executeQuery(query);
            
        }
            catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rset;
    }
            
            /*System.out.println("I record sono:");
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
        }*/
    

    public static String httpRequest(String URL) throws IOException {
        try {

            HttpClient proxy = HttpClient.newBuilder()
                    .proxy(ProxySelector.of(new InetSocketAddress("localhost", 5865)))
                    .build();
            HttpClient noproxy= HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .build();

            HttpResponse<String> response = proxy.send(request, BodyHandlers.ofString());
            String responseStr= response.body();
            return responseStr;
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
        
    }
    public ArrayList<String> PrendiImmagini(String json){
        ArrayList<String> arrImm = new ArrayList<>();
        Gson gson = new Gson();
        GameResponse gameResponse = gson.fromJson(json,GameResponse.class);
        String risultato="";
        String platformURL="https://api.mobygames.com/v1/games/";
        for(Game game : gameResponse.games){
            arrImm.add(game.sample_cover.image);
        }
        return arrImm;
    }
    
    public ArrayList<Game> PrendiGiochiNull(String json){
    ArrayList<Game> arGiochi= new ArrayList<>();
    Gson gson = new Gson();
    GameResponse gameResponse = gson.fromJson(json,GameResponse.class);
    for(Game game : gameResponse.games){
        if(game.sample_cover==null){
            arGiochi.add(game);
        }
    }
    return arGiochi;
    }
    
    public ArrayList<Game> PrendiGiochi(String json){
        ArrayList<Game> arGiochi= new ArrayList<>();
        Gson gson = new Gson();
        GameResponse gameResponse = gson.fromJson(json,GameResponse.class);
        for(Game game : gameResponse.games){
            if(game.sample_cover != null){
                arGiochi.add(game);
            }
        }
        return arGiochi;
    }
       public ArrayList<String> PrendiDati(Game game){
        //CODICE GENERATO CON CHATGPT E MODIFICATO
        
        ArrayList<String> DatiGiochi = new ArrayList<>();

        try {
            
            String risultato="";

            risultato="";
            risultato+="<html>Titolo: "+game.title+"<br>";
            risultato+="Generi: <br>";
            for(Genre genre : game.genres){
                risultato+=genre.genreName+", ";
            }
            risultato+="<br>Piattaforme: <br>";
            for (GameClasses platform : game.platforms) {
                risultato+="- " + platform.platformName + "(Data di rilascio: "+platform.firstReleaseDate+")<br>";
            }
            risultato+="Descrizione: "+game.description;
            risultato+="</html>";
            DatiGiochi.add(risultato);
            
            /*String platformURL="https://api.mobygames.com/v1/games/";
            String g=game.gameId+"";
                for(GameClasses platform : game.platforms){
                    platformURL+=platform.platformId;    
            }*/
            //System.out.println(platformURL);
            //String APIPlatforms=Model.httpRequest(platformURL);
            //Releases releases = gson.fromJson(APIPlatforms, Releases.class);
            /*for(Company company : releases){
                
            }*/
            
        } catch (Exception ex) {
            System.out.println(ex.toString()); 
        }
        return DatiGiochi;
    }
}

