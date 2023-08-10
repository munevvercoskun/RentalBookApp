/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.book.BookManager;
import backend.user.UserManager;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public final class BookStore {

    private BookManager bookManager;
    private UserManager userManager;
    private static BookStore instance;

    private BookStore() {
        bookManager = BookManager.getInstance();
        userManager = UserManager.getInstance();
        bookManager.readData();
        userManager.readData();
    }

    public static BookStore getInstance() {
        if (instance == null) {
            instance = new BookStore();
        }
        return instance;
    }

    public BookManager getBookManager() {
        return bookManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void saveData() {
        bookManager.saveData();
        userManager.saveData();
    }

}
