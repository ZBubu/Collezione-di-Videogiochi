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
            return "non va";
        }
        
    }
    public ArrayList<String> PrendiDati(String json){
        //CODICE GENERATO CON CHATGPT E MODIFICATO
        
        ArrayList<String> DatiGiochi = new ArrayList<>();

        try {
            Gson gson = new Gson();
            GameResponse gameResponse = gson.fromJson(json,GameResponse.class);
            //https://api.mobygames.com/v1/games/1/platforms/3
            String platformURL="https://api.mobygames.com/v1/games/";
            for(Game game : gameResponse.games){
                platformURL+=game.gameId+"/platforms/";
                for(Platform platform : game.platforms){
                    platformURL+=platform.platformId;
                }
            }
            String risultato="";
            for (Game game : gameResponse.games) {
                risultato="";
                risultato+="<html>Titolo: "+game.title+"<br>";
                risultato+="Generi: <br>";
                for(Genre genre : game.genres){
                    risultato+=genre.genreName+", ";
                }
                risultato+="<br>Piattaforme: <br>";
                for (Platform platform : game.platforms) {
                    risultato+="- " + platform.platformName + "(Data di rilascio: "+platform.firstReleaseDate+")<br>";
                }
                risultato+="Descrizione: "+game.description;
                risultato+="</html>";
                DatiGiochi.add(risultato);
            }
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
    public static ArrayList<String> TrovaImmagini(String json,String tipo) {

        // Definizione dell'espressione regolare per trovare i campi "image"
        //le espressioni regolari le ha generate chatgpt.
        String regex="";
        if(tipo.equals("immagini")){
            regex = "\"sample_cover\"\\s*:\\s*\\{[^}]*?\"image\"\\s*:\\s*\"(https?:\\/\\/[^\"\\\\]+)\"";
        }
        if(tipo.equals("titolo")){
            regex = "\"title\":\\s*\"([^\"]*)\"";
        }

        // Creazione del pattern e del matcher
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);

        ArrayList<String> imagesUrl =new ArrayList<>();
        while (matcher.find()) {
            imagesUrl.add(matcher.group(1));

        }
        return imagesUrl;

    }
}

