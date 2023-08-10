/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.book.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class OwnerBooksScreen extends DisplayState{
    private ObservableList<Book> bookData = FXCollections.observableArrayList(bookManager.getBooks());
    
    private final int BOOKNAME_COLUMN_SIZE = 300;
    private final int BOOKPRICE_COLUMN_SIZE = 300;
    
    @Override
    public void forward(Display d) {}
    
    @Override
    public void backward(Display d)
    {
        d.setState(new OwnerStartScreen());
    } 
    
    @Override
    public void graphics(Stage stage, Display d) 
    {
        TableView<Book> booksTable = new TableView<>();
        booksTable.setMinWidth(600);
        booksTable.getStyleClass().add("tableDisplay");
        
        Pane root = new Pane();
        
        Scene scene = new Scene(root, 600, 700);
        scene.getStylesheets().add("/frontend/css/StyleSheet.css");
        
        // Initializing and setting up the table columns and their characteristics
        TableColumn bookNameColumn = new TableColumn("Book Name");
        TableColumn bookPriceColumn = new TableColumn("Book Price");
        
        bookNameColumn.setPrefWidth(BOOKNAME_COLUMN_SIZE);
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        bookNameColumn.setStyle( "-fx-alignment: CENTER;");
        
        bookPriceColumn.setPrefWidth(BOOKPRICE_COLUMN_SIZE);
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        bookPriceColumn.setStyle( "-fx-alignment: CENTER;");
        
        // Setting all of the books onto the table and placing the columns in the table
        booksTable.setItems(bookData);
        booksTable.getColumns().addAll(bookNameColumn, bookPriceColumn);

        TextField addBookName = new TextField();
        TextField addBookPrice = new TextField();
        
        addBookName.setPromptText("New Book Name");
        addBookName.setMinWidth(184);
        addBookName.setLayoutY(460);
        addBookName.setLayoutX(115);
        addBookName.getStyleClass().add("addNameTXTField");
        
        addBookPrice.setPromptText("New Book Price");
        addBookPrice.setPrefColumnCount(14);
        addBookPrice.setLayoutY(460);
        addBookPrice.setLayoutX(299);
        addBookPrice.getStyleClass().add("addPxxxTXTField");
        
        root.getChildren().addAll(addBookName, addBookPrice);
        
        Button addNewBook = new Button();
        
        Label applicationCommentAdd = new Label();
        applicationCommentAdd.setStyle("-fx-font-size: 14px");
        
        // addNewBook button adds a new book and updates the table and data file if valid input
        addNewBook.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if(addBookName.getText().contains("-")){
                        applicationCommentAdd.setText("Book name CANNOT contain '-'");
                        applicationCommentAdd.setTextFill(Color.CRIMSON);
                        applicationCommentAdd.setLayoutY(435);
                        applicationCommentAdd.setLayoutX(188);                       
                    }
                else if (!addBookName.getText().isEmpty() && !addBookPrice.getText().isEmpty()) //checks if the user inputted values
                {
                    try // checks to make sure user added a number in the price textfield
                    {
                        Book newBook = new Book(addBookName.getText(), Double.parseDouble(addBookPrice.getText()));
                        
                        bookManager.addBook(newBook);
                        
                        addBookName.clear();
                        addBookPrice.clear();
                        
                        applicationCommentAdd.setText("Book added!");
                        applicationCommentAdd.setTextFill(Color.GREEN);
                        applicationCommentAdd.setLayoutY(435);
                        applicationCommentAdd.setLayoutX(250);

                        bookData.add(newBook);
                        booksTable.refresh();
                    }
                    catch (NumberFormatException e)
                    {
                        applicationCommentAdd.setText("The book price is not a number.");
                        applicationCommentAdd.setTextFill(Color.CRIMSON);
                        applicationCommentAdd.setLayoutY(435);
                        applicationCommentAdd.setLayoutX(190);
                    }
                }
                else
                {
                    applicationCommentAdd.setText("Either or both name and price have invalid inputs.");
                    applicationCommentAdd.setTextFill(Color.CRIMSON);
                    applicationCommentAdd.setLayoutY(435);
                    applicationCommentAdd.setLayoutX(125);
                }
            }
        });
        
        addNewBook.setText("Add Book");
        addNewBook.setLayoutY(495);
        addNewBook.setLayoutX((scene.getWidth() - 380)/2 + 5);
        addNewBook.setPrefWidth(370);
        addNewBook.getStyleClass().add("addButton");
        
        root.getChildren().addAll(addNewBook, applicationCommentAdd);
        
        Button deleteBook = new Button();
        
        Label applicationCommentDelete = new Label();
        applicationCommentDelete.setStyle("-fx-font-size: 14px");
        
        deleteBook.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                Book bookToBeDeleted = booksTable.getSelectionModel().getSelectedItem();
                if (bookToBeDeleted!=null){
                    bookManager.removeBook(bookToBeDeleted); 
                    bookData.remove(bookToBeDeleted);
                    booksTable.refresh();
                    applicationCommentDelete.setText("Book deleted.");
                    applicationCommentDelete.setTextFill(Color.GREEN);
                    applicationCommentDelete.setLayoutY(545);
                    applicationCommentDelete.setLayoutX(250);
                }
                else{
                    applicationCommentDelete.setText("Nothing was selected to be deleted.");
                    applicationCommentDelete.setTextFill(Color.CRIMSON);
                    applicationCommentDelete.setLayoutY(545);
                    applicationCommentDelete.setLayoutX(180); 
                }
            }
        });
              
        deleteBook.setText("Delete");
        deleteBook.setLayoutY(570);
        deleteBook.setLayoutX((scene.getWidth() - 380)/2 + 5);
        deleteBook.setPrefWidth(370);
        deleteBook.getStyleClass().add("deleteButton");
        
        Button backToLogin = new Button();
        
        backToLogin.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                d.backward();
            }
        });
        
        backToLogin.setText("Back");
        backToLogin.setLayoutY(640);
        backToLogin.setLayoutX((scene.getWidth() - 380)/2 + 5);
        backToLogin.setPrefWidth(370);
        backToLogin.getStyleClass().add("backButton");
        
        root.getChildren().addAll(deleteBook, applicationCommentDelete, backToLogin);
        
        root.getChildren().add(booksTable);
        stage.setScene(scene);
        stage.show();
    }
}
