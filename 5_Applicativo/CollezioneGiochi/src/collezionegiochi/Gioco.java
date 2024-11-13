/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collezionegiochi;

import java.util.List;

/**
 *
 * @author sidney.canonica
 */
public class Gioco {
    private List<Platform> platforms;

    public Gioco(List<Platform> users) {
        this.platforms = users;
    }

    public List<Platform> getUsers() {
        return platforms;
    }

    public void setUsers(List<Platform> users) {
        this.platforms = users;
    }

    @Override
    public String toString() {
        return "Gioco{" + "platforms=" + platforms + '}';
    }
    
}
