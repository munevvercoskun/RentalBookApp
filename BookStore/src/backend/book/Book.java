/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.book;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class Book {

    private double price;
    private String name;
    private CheckBox select;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
        select = new CheckBox();
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public CheckBox getSelect() {
        return select;
    }

    @Override
    public String toString() {
        return name + "-" + price + "\n";
    }
}
