/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.BookStore;
import backend.user.UserManager;
import backend.book.BookManager;
import backend.user.User;
import javafx.stage.Stage;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public abstract class DisplayState {
    static User account;   
    static double cost;
    static BookStore bookStoreInstance = BookStore.getInstance();
    static UserManager userManager = bookStoreInstance.getUserManager();
    static BookManager bookManager = bookStoreInstance.getBookManager();

    
    public abstract void forward(Display d);
    public abstract void backward(Display d);
    public abstract void graphics(Stage stage, Display d);
}
