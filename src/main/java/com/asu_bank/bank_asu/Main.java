package com.asu_bank.bank_asu;

import com.asu_bank.bank_asu.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {
    static Bank bank = Bank.getInstance();
    @Override


    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("ASU Bank");
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop(){
        DataLoader.saveDataToFiles(bank);
    }


    public static void main(String[] args) throws Exception {

        // Run data loading asynchronously

        DataLoader.loadData(bank);

        // Launch the JavaFX application
        launch();
    }
}
