/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collezionegiochi;

/**
 *
 * @author sidney.canonica
 */
//CREATO CON CHATGPT
import com.google.gson.annotations.SerializedName;
import java.util.List;

class GameResponse {
    @SerializedName("games")
    List<Game> games;
}

class Game {
    @SerializedName("game_id")
    int gameId;
    @SerializedName("title")
    String title;
    @SerializedName("platforms")
    List<Platform> platforms;
    @SerializedName("genres")
    List<Genre> genres;
}
class Genre{
    @SerializedName("genre_id")
    int genreId;
    @SerializedName("genre_name")
    String genreName;
}
class Platform {
    @SerializedName("platform_id")
    int platformId;
    @SerializedName("platform_name")
    String platformName;
    @SerializedName("first_release_date")
    String firstReleaseDate;
}
