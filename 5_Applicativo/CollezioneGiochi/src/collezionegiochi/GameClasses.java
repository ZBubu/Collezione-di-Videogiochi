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
    @SerializedName("description")
    String description;
    @SerializedName("sample_cover")
    SampleCover sample_cover;
    @SerializedName("platforms")
    List<GameClasses> platforms;
    @SerializedName("genres")
    List<Genre> genres;
    

}
class SampleCover{
    @SerializedName("image")
    String image;
}
class Genre{
    @SerializedName("genre_id")
    int genreId;
    @SerializedName("genre_name")
    String genreName;
}
class GameClasses {
    @SerializedName("platform_id")
    int platformId;
    @SerializedName("platform_name")
    String platformName;
    @SerializedName("first_release_date")
    String firstReleaseDate;
}

class Releases {
    @SerializedName("Relases")
    List<Company> companies;
}
class Company {
    @SerializedName("company_id")
    String companyId;
    @SerializedName("company_name")
    String companyName;
    @SerializedName("role")
    String role;
}
class Screenshots {
    @SerializedName("image")
    String image;
    @SerializedName("caption")
    String caption;
}
