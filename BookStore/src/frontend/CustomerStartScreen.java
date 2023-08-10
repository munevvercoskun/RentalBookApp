/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.book.Book;
import backend.user.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class CustomerStartScreen extends DisplayState{
    
    
    private ObservableList<Book> bookData = FXCollections.observableArrayList(bookManager.getBooks());
    private final int CHECKBOX_COLUMN_SIZE = 100;
    private final int BOOKNAME_COLUMN_SIZE = 250;
    private final int BOOKPRICE_COLUMN_SIZE = 250;

    @Override
    public void forward(Display d)
    {
        resetAllSelectedBooks();
        d.setState(new CustomerCostScreen());
    }
    
    @Override
    public void backward(Display d)
    {
        resetAllSelectedBooks();
        d.setState(new LoginScreen());
    } 
    
    @Override
    public void graphics(Stage stage, Display d) 
    {
        if (!(account instanceof Customer)) // if the incoming account is not of object customer, send them back to the login (non customer should not be in a customer state)
        {
            d.backward();
        }
        
        Pane root = new Pane();
        
        Scene scene = new Scene(root, 600, 700);
        scene.getStylesheets().add("/frontend/css/StyleSheet.css");
        
        Label welcomeTag = new Label();
        welcomeTag.setText("Welcome " + account.getUsername() + ". You have " + Math.round(((Customer) account).getPoints() * 100.0) / 100.0 + " points. Your status is " + ((Customer) account).getStatus() + ".");
        
        if (welcomeTag.getText().length() > 84)
        {
            welcomeTag.setStyle("-fx-font-size: 12px;");
        }
        else if (welcomeTag.getText().length() > 66)
        {
            welcomeTag.setStyle("-fx-font-size: 14px;");
        }
        else
        {
            welcomeTag.setStyle("-fx-font-size: 16px;");
        }
        
        welcomeTag.setLayoutY(8);
        welcomeTag.setLayoutX(Math.abs((scene.getWidth() - welcomeTag.getText().length()*8)/2 - 5));
        
        root.getChildren().add(welcomeTag);
        
        // the table of books instantiation and characteristics
        TableView<Book> booksTable = new TableView<>();
        booksTable.setMinWidth(600);
        booksTable.setLayoutY(40);
        
        // Initializing and setting up the table columns and their characteristics
        TableColumn checkboxColumn = new TableColumn("Select");
        TableColumn bookNameColumn = new TableColumn("Book Name");
        TableColumn bookPriceColumn = new TableColumn("Book Price");
        
        checkboxColumn.setPrefWidth(CHECKBOX_COLUMN_SIZE);
        checkboxColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
        checkboxColumn.setStyle( "-fx-alignment: CENTER;");
        
        bookNameColumn.setPrefWidth(BOOKNAME_COLUMN_SIZE);
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookNameColumn.setStyle( "-fx-alignment: CENTER;");
        
        bookPriceColumn.setPrefWidth(BOOKPRICE_COLUMN_SIZE);
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookPriceColumn.setStyle( "-fx-alignment: CENTER;");
        
        // Setting all of the books onto the table and placing the columns in the table
        booksTable.setItems(bookData);
        booksTable.getColumns().addAll(bookNameColumn, bookPriceColumn, checkboxColumn);
        
        Button buyWithoutPoints = new Button();
        Button buyWithPoints = new Button();
        Button logOut = new Button();
        
        Label applicationComment = new Label();
        applicationComment.setStyle("-fx-font-size: 16px;");
        
        buyWithoutPoints.setOnAction(new EventHandler<ActionEvent>() // sets what happens when buyWithoutPoints does on click
        {
            @Override
            public void handle(ActionEvent event) 
            { 
                if(somethingBought()) // if something was bought, send user to the next state
                {
                    cost = ((Customer) account).buyBook();
                    
                    booksTable.refresh();
                    d.forward();
                }
                else // if nothing was selected
                {
                    applicationComment.setText("Nothing was selected to be bought.");
                    applicationComment.setTextFill(Color.CRIMSON);
                    applicationComment.setLayoutY(450);
                    applicationComment.setLayoutX(160);
                }
            }
        });
        
        buyWithPoints.setOnAction(new EventHandler<ActionEvent>() // setting what buyWithPoints does on click
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(somethingBought()) // if something was bought, send user to the next state
                {
                    cost = ((Customer) account).buyBookWithPoints();
                    
                    booksTable.refresh();
                    d.forward();
                }
                else // if nothing was selected
                {
                    applicationComment.setText("Nothing was selected to be bought.");
                    applicationComment.setTextFill(Color.CRIMSON);
                    applicationComment.setLayoutY(450);
                    applicationComment.setLayoutX(160);
                }
            }
        });
      
        logOut.setOnAction(new EventHandler<ActionEvent>() // setting what logOut button does on click
        {
            @Override
            public void handle(ActionEvent event) 
            {
                d.backward();
            }
        });
        
        // buyWithoutPoints button characters
        buyWithoutPoints.setText("Buy");
        buyWithoutPoints.setLayoutY(480);
        buyWithoutPoints.setLayoutX(25);
        buyWithoutPoints.setMinHeight(130);
        buyWithoutPoints.setMinWidth(275);
        buyWithoutPoints.getStyleClass().add("buyButton");
        
        // buyWithPoints button characteristics
        buyWithPoints.setText("Buy with Points");
        buyWithPoints.setLayoutY(480);
        buyWithPoints.setLayoutX(305);
        buyWithPoints.setMinHeight(130);
        buyWithPoints.setMinWidth(275);
        buyWithPoints.getStyleClass().add("buyPointsButton");
        
        // logOut button characteristics
        logOut.setText("Log Out");
        logOut.setLayoutY(635);
        logOut.setMinWidth(555);
        logOut.setLayoutX(25);
        logOut.getStyleClass().add("logOutButton");
        
        root.getChildren().addAll(booksTable, buyWithoutPoints, buyWithPoints, logOut, applicationComment); // add the table, the buttons and the application label to the root node
        stage.setScene(scene);
        stage.show();
    }
    
    private boolean somethingBought(){
            
        for (Book b : bookData) // looks through all the books to see if something was selected
        {
            if(b.getSelect().isSelected())
            {
                return true;
            }
        }
        return false;
    }
    private void resetAllSelectedBooks()
    {
        bookManager.resetAllSelectedBooks();
        
        for (Book b : bookData)
        {
            b.getSelect().setSelected(false);
        }
    }
    
    private void removeBook(Book b) // specific removeBook method for the special JavaFX Book object collection (does the same thing as the one in BookStore)
    {
        ObservableList<Book> bookDataTemp = bookData;
        for (Book bk : bookDataTemp)
        {
            if (b.getName().equals(bk.getName()) && b.getPrice() == bk.getPrice() && bk.getSelect().isSelected())
            {
                bookData.remove(bk);
                return;
            }
        }
    }
}
