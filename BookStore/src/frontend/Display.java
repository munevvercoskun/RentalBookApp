/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 *
 * @author Andrej Zivkovic, Benjamin Fredette, Munevver Coskun, Sepehr Safa
 */
public class Display extends Application {
    private DisplayState currentState;
    Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image("/frontend/img/logo.jpg"));

        primaryStage.setTitle("Ryerson Book Store");
        primaryStage.setResizable(false);
        
        this.primaryStage = primaryStage;
        
        currentState = new LoginScreen();

        currentState.graphics(this.primaryStage, this);
    }

    public void forward()
    {
        currentState.forward(this);
        updateGraphics();
    }
    
    public void backward()
    {
        currentState.backward(this);
        updateGraphics();
    }
    
    public void setState(DisplayState d)
    {
        currentState = d;
        updateGraphics();
    }
    
    public DisplayState getState()
    {
        return currentState;
    }
    
    public void updateGraphics()
    {
        currentState.graphics(primaryStage, this);
    }
    
    @Override
    public void stop(){
        DisplayState.bookStoreInstance.saveData();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}