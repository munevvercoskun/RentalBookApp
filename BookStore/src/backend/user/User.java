/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.user;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public abstract class User {

    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User authenticate(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            return this;
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User Name: ";
    }

}
