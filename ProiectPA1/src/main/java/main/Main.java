package main;

import dao.NodeDAO;
import dao.StreetDAO;
import dao.UserDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import models.Node;
import models.Street;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        UserDAO user = new UserDAO();
        NodeDAO nodes = new NodeDAO();
        StreetDAO streets = new StreetDAO();
        Button btnregister = new Button("Register");
        TextField usernameBox = new TextField();
        TextField passwordBox = new TextField();
        Label usernameType = new Label("Type your username");
        Label passwordType = new Label("Type your password");
        Label loginText = new Label("Already have an account?");
        Button btnLogin = new Button("Login");
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
        btnLogin.setLayoutX(125);
        btnLogin.setLayoutY(255);
        Label regSucces = new Label("Registration completed succesfully\nGo further by pressing the login button");
        Label regFail = new Label("Registration has failed\nPlease try again using a different name");
        btnregister.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                System.out.println("Register");
                try {
                    if (user.register(usernameBox.getText(), passwordBox.getText()) == true) {
                        regSucces.setLayoutX(75);
                        regSucces.setLayoutY(30);
                        root.getChildren().remove(regFail);
                        root.getChildren().add(regSucces);
                    } else {
                        regFail.setLayoutX(75);
                        regFail.setLayoutY(30);
                        root.getChildren().remove(regSucces);
                        root.getChildren().add(regFail);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(usernameBox.getText());
                System.out.println(passwordBox.getText());

            }
        });
        root.getChildren().add(btnregister);
        root.getChildren().add(usernameBox);
        root.getChildren().add(passwordBox);
        root.getChildren().add(usernameType);
        root.getChildren().add(passwordType);
        root.getChildren().add(loginText);
        root.getChildren().add(btnLogin);
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().remove(btnregister);
                root.getChildren().remove(loginText);
                root.getChildren().remove(btnLogin);
                root.getChildren().remove(regFail);
                root.getChildren().remove(regSucces);
                Button btnLoginNou = new Button("Login");
                btnLoginNou.setLayoutX(120);
                btnLoginNou.setLayoutY(195);
                root.getChildren().add(btnLoginNou);
                btnLoginNou.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int userCurentId = 0;
                        try {
                            userCurentId = user.login(usernameBox.getText(), passwordBox.getText());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (userCurentId != 0) {
                            Pane root2 = new Pane();
                            Scene scenaHarta = new Scene(root2, 1000, 1000);
                            primaryStage.setTitle("RouteSeeker");
                            primaryStage.setScene(scenaHarta);
                            primaryStage.show();
                            ArrayList<Node> puncte=new ArrayList<>();
                            try {
                                puncte = nodes.genereazaHarta();
                                for (Node i : puncte) {
                                    Circle cerc = new Circle();
                                    cerc.setCenterX(i.getX());
                                    cerc.setCenterY(i.getY());
                                    cerc.setRadius(10);
                                    root2.getChildren().add(cerc);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            ArrayList<Street> strazi = new ArrayList<>();
                            try {
                                strazi = streets.genereazaStrazi();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            for (Street i : strazi) {
                                Line linie = new Line();
                                linie.setStrokeWidth(2);
                                linie.setStroke(Color.GRAY);
                                for (Node j : puncte) {
                                    if (j.getId() == i.getIdNodeStart()) {
                                        linie.setStartX(j.getX());
                                        linie.setStartY(j.getY());
                                    }
                                    if (j.getId() == i.getIdNodeEnd()) {
                                        linie.setEndX(j.getX());
                                        linie.setEndY(j.getY());
                                    }
                                }
                                root2.getChildren().add(linie);
                            }

                        } else {
                            Label loginFailed = new Label("Login has failed.\nPlease review your credentials");
                            loginFailed.setLayoutX(75);
                            loginFailed.setLayoutY(30);
                            root.getChildren().add(loginFailed);
                        }
                    }
                });
            }
        });
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setTitle("Autentification");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}