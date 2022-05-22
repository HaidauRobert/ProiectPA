package main;

import dao.NodeDAO;
import dao.StreetDAO;
import dao.UserDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Node;
import models.Street;
import javafx.scene.input.MouseEvent;
import utils.ImportData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    int nrMap;

    public Node checkNodeInGraph(int x, int y, int nrMap) throws SQLException {
        NodeDAO nodesDao = new NodeDAO();
        List<Node> nodes = nodesDao.generateNodes(nrMap);
        int nodeSize = 10;

        for (Node node : nodes) {
            if (x >= (node.getX() - nodeSize / 2) && x <= (node.getX() + nodeSize / 2) &&
                    y > (node.getY() - nodeSize / 2) && y < (node.getY() + nodeSize / 2)) {
                return new Node(node.getX(), node.getY());
            }
        }
        return null;
    }

    public void displayButtons(Pane root, Stage primaryStage) throws Exception {

        MenuButton btnChooseMap = new MenuButton("Choose map:");
        MenuItem menuItem1 = new MenuItem("Map 1");
        MenuItem menuItem2 = new MenuItem("Map 2");
        MenuItem menuItem3 = new MenuItem("Map 3");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane newRoot = new Pane();
                Scene mapScene = new Scene(newRoot, 100, 100);
                primaryStage.setScene(mapScene);
                primaryStage.show();
                nrMap = 1;
                try {

                    displayNodes(newRoot, 1);
                    displayStreets(newRoot, 1);
                    displayButtons(newRoot, primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Pane newRoot = new Pane();
                Scene mapScene = new Scene(newRoot, 1000, 1000);
                primaryStage.setScene(mapScene);
                primaryStage.show();
                nrMap = 2;
                try {
                    displayNodes(newRoot, 2);
                    displayStreets(newRoot, 2);
                    displayButtons(newRoot, primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane newRoot = new Pane();
                Scene mapScene = new Scene(newRoot, 1000, 1000);
                primaryStage.setScene(mapScene);
                primaryStage.show();
                nrMap = 3;
                try {
                    displayNodes(newRoot, 3);
                    displayStreets(newRoot, 3);
                    displayButtons(newRoot, primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Font fontText = new Font(12);
        btnChooseMap.getItems().addAll(menuItem1, menuItem2, menuItem3);
        btnChooseMap.setLayoutY(600);
        btnChooseMap.setLayoutX(50);
        btnChooseMap.setPrefHeight(50);
        btnChooseMap.setPrefWidth(170);
        btnChooseMap.setFont(fontText);
        root.getChildren().add(btnChooseMap);

        Button btnChooseNode = new Button("Choose the start point.");
        btnChooseNode.setLayoutY(600);
        btnChooseNode.setLayoutX(270);
        btnChooseNode.setPrefHeight(50);
        btnChooseNode.setPrefWidth(170);
        btnChooseNode.setFont(fontText);
        root.getChildren().add(btnChooseNode);

        btnChooseNode.setOnAction(new EventHandler<ActionEvent>() {
            Boolean oneChoose = false;

            @Override
            public void handle(ActionEvent actionEvent) {
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println(mouseEvent.getX());
                        System.out.println(mouseEvent.getY());
                        try {
                            Node choseCircle = checkNodeInGraph((int) mouseEvent.getX(), (int) mouseEvent.getY(), nrMap);
                            if (choseCircle != null && !oneChoose) {
                                Circle circleNode = new Circle();
                                circleNode.setCenterX(choseCircle.getX());
                                circleNode.setCenterY(choseCircle.getY());
                                circleNode.setFill(Color.RED);
                                circleNode.setRadius(10);
                                oneChoose = true;
                                root.getChildren().add(circleNode);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        Label descLength = new Label("Write approximate length in m:");
        descLength.setLayoutY(600);
        descLength.setLayoutX(490);
        root.getChildren().add(descLength);

        TextField lengthField = new TextField();
        lengthField.setLayoutY(625);
        lengthField.setLayoutX(490);
        lengthField.setPrefWidth(250);
        root.getChildren().add(lengthField);

        Button findRoute = new Button("Find route");
        findRoute.setLayoutY(600);
        findRoute.setLayoutX(790);
        findRoute.setPrefHeight(50);
        findRoute.setPrefWidth(170);
        findRoute.setFont(fontText);
        root.getChildren().add(findRoute);

    }

    public void displayNodes(Pane sceneRoot, int nrMap) throws Exception {
        NodeDAO nodesDao = new NodeDAO();
        List<Node> nodes;
        try {
            nodes = nodesDao.generateNodes(nrMap);
            for (Node node : nodes) {
                Circle circleNode = new Circle();
                circleNode.setCenterX(node.getX());
                circleNode.setCenterY(node.getY());
                circleNode.setRadius(10);
                sceneRoot.getChildren().add(circleNode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayStreets(Pane sceneRoot, int nrMap) throws Exception {

        StreetDAO streetsDao = new StreetDAO();
        NodeDAO nodesDao = new NodeDAO();

        List<Node> nodes;
        nodes = nodesDao.generateNodes(nrMap);

        List<Street> streets;
        streets = streetsDao.generateStreets(nrMap);

        for (Street street : streets) {

            Line lineStreet = new Line();
            lineStreet.setStrokeWidth(2);
            lineStreet.setStroke(Color.GRAY);

            for (Node node : nodes) {

                System.out.println(node);
                if (node.getId() == street.getIdNodeStart()) {

                    lineStreet.setStartX(node.getX());
                    lineStreet.setStartY(node.getY());
                }
                if (node.getId() == street.getIdNodeEnd()) {

                    lineStreet.setEndX(node.getX());
                    lineStreet.setEndY(node.getY());
                }
            }

            sceneRoot.getChildren().add(lineStreet);

        }
    }

    public void start(Stage primaryStage) throws Exception {
        Pane startRoot = new Pane();
        UserDAO userDao = new UserDAO();

        Button btnRegister = new Button("Register");
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
        btnRegister.setLayoutX(120);
        btnRegister.setLayoutY(195);
        loginText.setLayoutX(80);
        loginText.setLayoutY(230);
        btnLogin.setLayoutX(125);
        btnLogin.setLayoutY(255);
        Label successStatus = new Label("Registration completed succesfully\nGo further by pressing the login button");
        Label failStatus = new Label("Registration has failed\nPlease try again using a different name");
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                System.out.println("Register");
                try {
                    if (userDao.register(usernameBox.getText(), passwordBox.getText()) == true) {
                        successStatus.setLayoutX(75);
                        successStatus.setLayoutY(30);
                        startRoot.getChildren().remove(failStatus);
                        startRoot.getChildren().add(successStatus);
                    } else {
                        failStatus.setLayoutX(75);
                        failStatus.setLayoutY(30);
                        startRoot.getChildren().remove(successStatus);
                        startRoot.getChildren().add(failStatus);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(usernameBox.getText());
                System.out.println(passwordBox.getText());

            }
        });
        startRoot.getChildren().add(btnRegister);
        startRoot.getChildren().add(usernameBox);
        startRoot.getChildren().add(passwordBox);
        startRoot.getChildren().add(usernameType);
        startRoot.getChildren().add(passwordType);
        startRoot.getChildren().add(loginText);
        startRoot.getChildren().add(btnLogin);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startRoot.getChildren().remove(btnRegister);
                startRoot.getChildren().remove(loginText);
                startRoot.getChildren().remove(btnLogin);
                startRoot.getChildren().remove(failStatus);
                startRoot.getChildren().remove(successStatus);

                Button newLoginBtn = new Button("Login");
                newLoginBtn.setLayoutX(120);
                newLoginBtn.setLayoutY(195);
                startRoot.getChildren().add(newLoginBtn);

                newLoginBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int userCurrentId = 0;
                        try {
                            userCurrentId = userDao.login(usernameBox.getText(), passwordBox.getText());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (userCurrentId != 0) {
                            Pane mapRoot = new Pane();
                            Scene mapScene = new Scene(mapRoot, 1000, 1000);
                            primaryStage.setTitle("RouteSeeker");
                            primaryStage.setScene(mapScene);
                            primaryStage.show();
                            nrMap = 1;
                            try {
                                System.out.println("salutare");
                                displayNodes(mapRoot, 1);
                                displayStreets(mapRoot, 1);
                                displayButtons(mapRoot, primaryStage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Label loginFailed = new Label("Login has failed.\nPlease review your credentials");
                            loginFailed.setLayoutX(75);
                            loginFailed.setLayoutY(30);
                            startRoot.getChildren().add(loginFailed);
                        }
                    }
                });
            }
        });

        Scene scene = new Scene(startRoot, 300, 400);
        primaryStage.setTitle("Authentication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void populateTables() throws IOException, SQLException {

        NodeDAO nodeDao = new NodeDAO();
        StreetDAO streetDAO = new StreetDAO();
        ImportData infos = new ImportData();

        for (Node node : infos.nodesImport()) {
            nodeDao.createNode(node);
        }

        for (Street street : infos.streetsImport()) {
            streetDAO.createStreet(street);
        }

    }

    public static void main(String[] args) throws IOException, SQLException {

        populateTables();
        launch(args);
    }
}