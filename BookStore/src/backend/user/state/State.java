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
public abstract class State {

    public abstract void toGold(Customer c);

    public abstract void toSilver(Customer c);

    public abstract String toString();
}
