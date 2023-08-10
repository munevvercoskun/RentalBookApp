/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;


import backend.user.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class CustomerCostScreen extends DisplayState{
    
    @Override
    public void forward(Display d) {}
    
    @Override
    public void backward(Display d)
    {
        d.setState(new LoginScreen());
    } 
    
    @Override
    public void graphics(Stage stage, Display d) 
    {
        Pane root = new Pane();
        
        Scene scene = new Scene(root, 400, 200);
        scene.getStylesheets().add("/frontend/css/StyleSheet.css");
        
        Label totalCostLabel = new Label();
        Label addedPointsLabel = new Label();
        
        // totalCostLabel characteristics
        totalCostLabel.setText("Total Cost " + cost);
        totalCostLabel.setLayoutY(10);
        totalCostLabel.setStyle("-fx-font-size: 20px;");
        totalCostLabel.setLayoutX(Math.abs((scene.getWidth() - totalCostLabel.getText().length()*10)/2 - 5));
           
        // addedPointsLabel characteristics
        addedPointsLabel.setText("Points: " + Math.round(((Customer) account).getPoints() * 100.0) / 100.0 + "  |  Status: " + ((Customer) account).getStatus().toString());
        addedPointsLabel.setLayoutY(40);
        addedPointsLabel.setStyle("-fx-font-size: 20px;");
        addedPointsLabel.setLayoutX(Math.abs((scene.getWidth() - addedPointsLabel.getText().length()*10)/2 - 5));
        
        Button logOut = new Button();
        
        logOut.setOnAction(new EventHandler<ActionEvent>() // setting what logOut button does on click
        {
            @Override
            public void handle(ActionEvent event) 
            {
                cost = 0;
                d.backward();
            }
        });
        
        // logOut button characteristics
        logOut.setText("Log Out");
        logOut.setLayoutY(120);
        logOut.setLayoutX((scene.getWidth() - 225)/2 + 5);
        logOut.setMinWidth(225);
        logOut.getStyleClass().add("logOutButton");
        
        root.getChildren().addAll(totalCostLabel, addedPointsLabel, logOut); // adds the labels and log out button to the rot node
        
        stage.setScene(scene);
        stage.show();
    }
}