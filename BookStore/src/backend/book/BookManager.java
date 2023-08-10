/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.book;

import backend.Manager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public final class BookManager implements Manager {

    private static BookManager instance;
    private ArrayList<Book> books;   // where all of the store's books are
    private static String fileName = "books.txt";

    private BookManager() {

        books = new ArrayList<Book>();

        try {
            File file = new File(fileName);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static BookManager getInstance() {
        if (BookManager.instance == null) {
            BookManager.instance = new BookManager();
        }
        return instance;
    }

    public ArrayList<Book> getBooks() {
        return (ArrayList<Book>) books.clone();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void resetAllSelectedBooks() {
        for (Book book : books) {
            book.getSelect().setSelected(false);
        }
    }

    public void readData() {
        try {
            File textFile = new File(fileName);
            Scanner myReader = new Scanner(textFile);
            System.out.print("System Books:\n");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataParts = data.split("-");
                Book newBook = new Book(dataParts[0], Double.valueOf(dataParts[1]));
                addBook(newBook);
                System.out.print(newBook);
            }
            myReader.close();
            System.out.println("\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void resetFile() {
        try {
            FileWriter textFile = new FileWriter(fileName, false);
            textFile.write("");
            textFile.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            resetFile();
            FileWriter textFile = new FileWriter(fileName);
            for (Book book : books) {
                textFile.write(book.toString());
            }
            textFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
