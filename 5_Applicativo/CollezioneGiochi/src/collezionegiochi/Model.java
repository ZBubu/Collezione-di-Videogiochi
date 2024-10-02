/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collezionegiochi;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author sidney.canonica
 */
public class Model {
    private URL url;
    private String apiKey;
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
}
