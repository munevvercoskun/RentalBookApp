/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.user;

import backend.user.state.*;
import backend.book.*;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class Customer extends User {

    private double points;
    private State status;

    public Customer(String username, String password) {
        super(username, password);
        points = 0;
        status = new Silver();
    }

    public Customer(String username, String password, double points) {
        super(username, password);
        this.points = points;
        status = new Silver();
        updateStatus();
    }

    public double getPoints() {
        return points;
    }

    public State getStatus() {
        return status;
    }

    public void setStatus(State status) {
        this.status = status;
    }

    public double buyBook() 
    {
        double cost = 0;
        for (Book book : BookManager.getInstance().getBooks()) {
            if (book.getSelect().isSelected()) {
                cost += book.getPrice();
                BookManager.getInstance().removeBook(book);
            }
        }
        points += cost * 10;
        updateStatus();

        return cost;
    }

    public double buyBookWithPoints()
    {
        double cost = 0;
        double cadValueoFPoints = points / 100;

        for (Book book : BookManager.getInstance().getBooks()) {
            if (book.getSelect().isSelected()) {
                cost += book.getPrice();
                BookManager.getInstance().removeBook(book);
            }
        }

        if (cost < cadValueoFPoints) {
            points = (cadValueoFPoints - cost) * 100;
            return 0;
        }

        cost = cost - cadValueoFPoints;
        points = cost * 10;
        updateStatus();

        return cost;
    }

    private void updateStatus() // updates the customer's status
    {
        if (points >= 1000) {
            status.toGold(this);
        } else {
            status.toSilver(this);
        }
    }

    @Override
    public String toString() {
        return username + "-" + password + "-" + points + "\n";
    }
}
