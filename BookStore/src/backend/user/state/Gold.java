/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.user.state;

import backend.user.Customer;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class Gold extends State {

    @Override
    public void toGold(Customer c) {
    }

    @Override
    public void toSilver(Customer c) {
        c.setStatus(new Silver());
    }

    @Override
    public String toString() {
        return "Gold";
    }
}
