package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {

        Button btnregister = new Button("Register");
        TextField usernameBox = new TextField();
        TextField passwordBox = new TextField();
        Label usernameType= new Label("Type your username");
        Label passwordType= new Label("Type your password");
        Label loginText= new Label("Already have an account?");
        Button btnlogin = new Button("Login");
        usernameType.setLayoutX(75);
        usernameType.setLayoutY(80);
        passwordType.setLayoutX(75);
        passwordType.setLayoutY(135);
        usernameBox.setLayoutX(75);
        usernameBox.setLayoutY(100);
        passwordBox.setLayoutX(75);
        passwordBox.setLayoutY(155);
        btnregister.setLayoutX(120);
        btnregister.setLayoutY(195);
        loginText.setLayoutX(80);
        loginText.setLayoutY(230);
        btnlogin.setLayoutX(125);
        btnlogin.setLayoutY(255);
        btnregister.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                System.out.println("Register");
                System.out.println(usernameBox.getText());
                System.out.println(passwordBox.getText());
            }
        }
        );
        Pane root = new Pane();
        root.getChildren().add(btnregister);
        root.getChildren().add(usernameBox);
        root.getChildren().add(passwordBox);
        root.getChildren().add(usernameType);
        root.getChildren().add(passwordType);
        root.getChildren().add(loginText);
        root.getChildren().add(btnlogin);
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}