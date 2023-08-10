/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.user;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import backend.Manager;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class UserManager implements Manager {

    private static UserManager instance;
    private static String fileName = "customers.txt";
    private ArrayList<User> customers;
    private User owner;

    private UserManager() {
        owner = Owner.getInstance();
        customers = new ArrayList<User>();
        
        try {
            File file = new File(fileName);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static UserManager getInstance() {
        if (UserManager.instance == null) {
            UserManager.instance = new UserManager();
        }
        return instance;
    }

    public User authenticate(String username, String password) {
        if (owner.authenticate(username, password) != null) {
            return owner;
        }
        for (User customer : customers) {
            if (customer.authenticate(username, password) != null) {
                return customer;
            }
        }
        return null;
    }

    public ArrayList<User> getCustomers() {
        return (ArrayList<User>) customers.clone();
    }

    private boolean isNewPerson(User newUser) {
        for (User user : customers) {
            if (user.getUsername().equals(newUser.getUsername())) {
                return false;
            }
        }
        return true;
    }

    public boolean addCustomer(User user) {
        if (isNewPerson(user) && !(user instanceof Owner)) {
            customers.add(user);
            return true;
        }
        return false;
    }

    public void deleteCustomer(User user) {
        customers.remove(user);
    }

    public void readData() {
        try {
            File textFile = new File(fileName);
            Scanner myReader = new Scanner(textFile);
            System.out.print("System Users:\n");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataParts = data.split("-");
                User newUser = new Customer(dataParts[0], dataParts[1], Double.valueOf(dataParts[2]));
                addCustomer(newUser);
                System.out.print(newUser);
            }
            myReader.close();
            System.out.print("\n");
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
            for (User customer : customers) {
                textFile.write(customer.toString());
            }
            textFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
