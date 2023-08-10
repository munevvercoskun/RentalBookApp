/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.user.Owner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class LoginScreen extends DisplayState{
    
    @Override
    public void forward(Display d)
    {
        if (account instanceof Owner)
        {
            d.setState(new OwnerStartScreen());
        }
        else
        {
            d.setState(new CustomerStartScreen());
        }
    }
    
    @Override
    public void backward(Display d) {}    
    
    @Override
    public void graphics(Stage stage, Display d) 
    {
        Pane root = new Pane();
        TextField username = new TextField();
        TextField password = new TextField();
        
        Scene scene = new Scene(root, 400, 200);
        scene.getStylesheets().add("/frontend/css/StyleSheet.css");
        
        Label usernameLabel = new Label();
        Label passwordLabel = new Label();
        
        // Username textbox settings
        username.setPromptText("Enter your username");
        username.setPrefColumnCount(12);
        username.setLayoutY(40);
        username.setLayoutX(scene.getWidth() - 235);
        username.getStyleClass().add("loginUserNTXTField");
        
        // Username label settings
        usernameLabel.setText("Username:");
        usernameLabel.setLayoutY(40);
        usernameLabel.setLayoutX(scene.getWidth() - 318);
        usernameLabel.getStyleClass().add("loginUserLabel");
        
        // Password textbox settings
        password.setPromptText("Enter your password");
        password.setPrefColumnCount(12);
        password.setLayoutY(75);
        password.setLayoutX(scene.getWidth() - 235);
        password.getStyleClass().add("loginPassTXTField");
        
        // Password label settings
        passwordLabel.setText("Password:");
        passwordLabel.setLayoutY(75);
        passwordLabel.setLayoutX(scene.getWidth() - 318);
        passwordLabel.getStyleClass().add("loginPassLabel");
                
        // Placing the labels onto the root node
        root.getChildren().addAll(usernameLabel, passwordLabel);
        
        // Placing the textboxes onto the root node
        root.getChildren().add(username);
        root.getChildren().add(password);        
        
        Label applicationComment = new Label(); // Application commentary label only used if user inputs wrong credentials
        
        root.getChildren().add(applicationComment); // Placing the application commentary onto the root node
        
        Button logIn = new Button();
        logIn.getStyleClass().add("loginButton");
        
        logIn.setOnAction(new EventHandler<ActionEvent>() // setting what logIn button does on click
        {
            @Override
            public void handle(ActionEvent event) 
            {
                account = userManager.authenticate(username.getText(), password.getText());
                if (account == null)
                {
                    applicationComment.setText("Either or both inputted username and password are wrong.");
                    applicationComment.setTextFill(Color.CRIMSON);
                    applicationComment.setLayoutY(18);
                    applicationComment.setLayoutX(24);                    
                }
                else
                {
                    d.forward();
                }
            }
        });
        
        logIn.setText("Log In");
        logIn.setMinHeight(20);
        logIn.setMinWidth(250);
        logIn.setLayoutY(120);
        logIn.setLayoutX((scene.getWidth() - 250)/2 + 5);
        
        root.getChildren().add(logIn);
        
        stage.setScene(scene);
        stage.show();
    }
}
