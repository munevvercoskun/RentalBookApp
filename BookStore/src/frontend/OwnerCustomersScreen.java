/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.user.Customer;
import backend.user.User;
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
public class OwnerCustomersScreen extends DisplayState{
    private ObservableList<User> customerData = FXCollections.observableArrayList(userManager.getCustomers());
    

    private final int USERNAME_COLUMN_SIZE = 200;
    private final int PASSWORD_COLUMN_SIZE = 200;
    private final int POINTS_COLUMN_SIZE = 200;
    
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
        TableView<User> customerTable = new TableView<>();
        customerTable.setMinWidth(600);
        
        Pane root = new Pane();
        
        Scene scene = new Scene(root, 600, 700);
        scene.getStylesheets().add("/frontend/css/StyleSheet.css");
        
        // Initializing and setting up the table columns and their characteristics
        TableColumn usernameColumn = new TableColumn("Username");
        TableColumn passwordColumn = new TableColumn("Password");
        TableColumn pointsColumn = new TableColumn("Points");
        

        
        usernameColumn.setPrefWidth(USERNAME_COLUMN_SIZE);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setStyle( "-fx-alignment: CENTER;");
        
        passwordColumn.setPrefWidth(PASSWORD_COLUMN_SIZE);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setStyle( "-fx-alignment: CENTER;");
        
        pointsColumn.setPrefWidth(POINTS_COLUMN_SIZE);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        pointsColumn.setStyle( "-fx-alignment: CENTER;");
        // Setting all of the customers onto the table and placing the columns in the table
        customerTable.setItems(customerData);
        customerTable.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);

        TextField addUsername = new TextField();
        TextField addPassword = new TextField();
        
        addUsername.setPromptText("New Username");
        addUsername.setMinWidth(184);
        addUsername.setLayoutY(460);
        addUsername.setLayoutX(115);
        addUsername.getStyleClass().add("addNameTXTField");
        
        addPassword.setPromptText("New Password");
        addPassword.setPrefColumnCount(14);
        addPassword.setLayoutY(460);
        addPassword.setLayoutX(299);
        addPassword.getStyleClass().add("addPxxxTXTField");
        
        root.getChildren().addAll(addUsername, addPassword);
        
        Button addNewCustomer = new Button();
        Label applicationCommentAdd = new Label();
        applicationCommentAdd.setStyle("-fx-font-size: 14px");
        
        // addNewBook button adds a new book and updates the table and data file if valid input
        addNewCustomer.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if (addUsername.getText().equals("admin") || addPassword.getText().equals("admin")) //checks if the user inputted values
                {
                    applicationCommentAdd.setText("Either or both username and password are admin.");
                    applicationCommentAdd.setTextFill(Color.CRIMSON);
                    applicationCommentAdd.setLayoutY(435);
                    applicationCommentAdd.setLayoutX(122);           
                }
                else if (!addUsername.getText().isEmpty() && !addPassword.getText().isEmpty())
                {
                    User newCustomer = new Customer(addUsername.getText(), addPassword.getText());
                    
                    if(addPassword.getText().contains("-") || addUsername.getText().contains("-") ){
                        applicationCommentAdd.setText("Password or Username CANNOT contain '-'");
                        applicationCommentAdd.setTextFill(Color.CRIMSON);
                        applicationCommentAdd.setLayoutY(435);
                        applicationCommentAdd.setLayoutX(150);                       
                    }
                    else if(userManager.addCustomer(newCustomer))
                    {

                        addUsername.clear();
                        addPassword.clear();
                        
                        applicationCommentAdd.setText("Customer added!");
                        applicationCommentAdd.setTextFill(Color.GREEN);
                        applicationCommentAdd.setLayoutY(435);
                        applicationCommentAdd.setLayoutX(240);
                        customerData.add(newCustomer);
                        customerTable.refresh();
                    }
                    else
                    {
                        applicationCommentAdd.setText("A customer with this username already exists.");
                        applicationCommentAdd.setTextFill(Color.CRIMSON);
                        applicationCommentAdd.setLayoutY(435);
                        applicationCommentAdd.setLayoutX(140);
                    }
                }
                else
                {
                    applicationCommentAdd.setText("Either or both username and password have invalid inputs.");
                    applicationCommentAdd.setTextFill(Color.CRIMSON);
                    applicationCommentAdd.setLayoutY(435);
                    applicationCommentAdd.setLayoutX(95);
                }
            }
        });
        
        addNewCustomer.setText("Add Customer");
        addNewCustomer.setLayoutY(495);
        addNewCustomer.setLayoutX((scene.getWidth() - 380)/2 + 5);
        addNewCustomer.setPrefWidth(370);
        addNewCustomer.getStyleClass().add("addButton");
        
        root.getChildren().addAll(addNewCustomer, applicationCommentAdd);
        
        Button deleteCustomer = new Button();
        
        Label applicationCommentDelete = new Label();
        applicationCommentDelete.setStyle("-fx-font-size: 14px");
        
        deleteCustomer.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                User userToBeDeleted = customerTable.getSelectionModel().getSelectedItem();
                if (userToBeDeleted!=null){
                    userManager.deleteCustomer(userToBeDeleted); 
                    customerData.remove(userToBeDeleted);
                    customerTable.refresh();
                    applicationCommentDelete.setText("Customer deleted.");
                    applicationCommentDelete.setTextFill(Color.GREEN);
                    applicationCommentDelete.setLayoutY(545);
                    applicationCommentDelete.setLayoutX(235);
                }
                else
                {
                    applicationCommentDelete.setText("Nothing was selected to be deleted.");
                    applicationCommentDelete.setTextFill(Color.CRIMSON);
                    applicationCommentDelete.setLayoutY(545);
                    applicationCommentDelete.setLayoutX(180);
                }
            }
        });
              
        deleteCustomer.setText("Delete");
        deleteCustomer.setLayoutY(570);
        deleteCustomer.setLayoutX((scene.getWidth() - 380)/2 + 5);
        deleteCustomer.setPrefWidth(370);
        deleteCustomer.getStyleClass().add("deleteButton");
        
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
        
        root.getChildren().addAll(deleteCustomer, applicationCommentDelete, backToLogin);
        
        root.getChildren().add(customerTable);
        stage.setScene(scene);
        stage.show();
    }
}
