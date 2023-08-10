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
public final class Owner extends User {

    private static Owner instance;

    private Owner() {
        super("admin", "admin");
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new Owner();
        }
        return instance;
    }
}
