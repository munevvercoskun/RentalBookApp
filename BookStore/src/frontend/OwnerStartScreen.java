/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class OwnerStartScreen extends DisplayState{
    private enum requestedState{neither, toBooks, toCustomer};
    private requestedState buttonPressed = requestedState.neither;
    
    @Override
    public void forward(Display d)
    {
        switch(buttonPressed)   // multiple forward states to differentiate where the user wants to go
        {
            case toBooks :
                d.setState(new OwnerBooksScreen());
                break;
            case toCustomer :
                d.setState(new OwnerCustomersScreen());
                break;    
            default :
                System.out.println("The button pressed in OwnerStartScreen is unknown");
                d.backward();
        }
    }
    
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
        
        // instantiating new buttons
        Button enterOwnerBooks = new Button();
        Button enterOwnerCustomers = new Button();
        Button logOut = new Button();
        
        // enterOwnerBooks button characteristics
        enterOwnerBooks.setText("Books");
        enterOwnerBooks.setLayoutY(20);
        enterOwnerBooks.setMinWidth(300);
        enterOwnerBooks.setLayoutX((scene.getWidth() - 300)/2 + 5);
        enterOwnerBooks.getStyleClass().add("ownerBooksButton");
        
        enterOwnerBooks.setOnAction(new EventHandler<ActionEvent>() // setting what enterOwnerBooks button does on click
        {
            @Override
            public void handle(ActionEvent event) 
            {
                buttonPressed = requestedState.toBooks; // sets the buttonPressed so the state knows to go to the OwnerBooksScreen state
                d.forward();
            }
        });
        
        // enterOwnerCustomers button characteristics
        enterOwnerCustomers.setText("Customers");
        enterOwnerCustomers.setLayoutY(70);
        enterOwnerCustomers.setMinWidth(300);
        enterOwnerCustomers.setLayoutX((scene.getWidth() - 300)/2 + 5);
        enterOwnerCustomers.getStyleClass().add("ownerCustomersButton");
        
        enterOwnerCustomers.setOnAction(new EventHandler<ActionEvent>() // setting what enterOwnerCustomers button does on click
        {
            @Override
            public void handle(ActionEvent event) 
            {
                buttonPressed = requestedState.toCustomer; // sets the buttonPressed so the state knows to go to the OwnerCustomersScreen state
                d.forward();
            }
        });
        
        // logOut button characteristics
        logOut.setText("Log Out");
        logOut.setLayoutY(130);
        logOut.setPrefWidth(300);
        logOut.setLayoutX((scene.getWidth() - 300)/2 + 5);
        logOut.getStyleClass().add("logOutButton");
        
        logOut.setOnAction(new EventHandler<ActionEvent>() // setting what logOut button does on click
        {
            @Override
            public void handle(ActionEvent event) 
            {
                d.backward();
            }
        });
        
        root.getChildren().addAll(enterOwnerBooks, enterOwnerCustomers, logOut); // sets buttons on the root node

        stage.setScene(scene);
        stage.show();
    }
}
