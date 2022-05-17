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

        Button btn1 = new Button("Register");
        TextField usernameBox = new TextField("Type your username");
        TextField passwordBox = new TextField("Type your password");
        usernameBox.setLayoutX(450);
        usernameBox.setLayoutY(600);
        passwordBox.setLayoutX(450);
        passwordBox.setLayoutY(650);
        btn1.setLayoutX(450);
        btn1.setLayoutY(700);
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                System.out.println("Register");
                System.out.println(usernameBox.getText());
                System.out.println(passwordBox.getText());
            }
        }
        );
        Pane root = new Pane();
        root.getChildren().add(btn1);
        root.getChildren().add(usernameBox);
        root.getChildren().add(passwordBox);
        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}