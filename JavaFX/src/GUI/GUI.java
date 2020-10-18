package GUI;

import SDMMainPage.SDMMainPageController;
import SDMSystem.SDMSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SDMMainPage/SDMMainPage.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Super Duper Market");
        primaryStage.setScene(new Scene(root, 1200, 900));
        primaryStage.getIcons().add(new Image("/GUI/Resources/cartIcon.png"));

        primaryStage.getScene().getStylesheets().add(getClass().getResource("/SDMMainPage/DarkMode.css").toExternalForm());

        SDMMainPageController sdmMainPageController = fxmlLoader.getController();
        sdmMainPageController.setPrimaryStage(primaryStage);
        sdmMainPageController.setStyleSheet(primaryStage.getScene().getStylesheets().get(0));

        SDMSystem sdmSystem = new SDMSystem();
        sdmMainPageController.setLogic(sdmSystem);
        //To add gui to engine?????

        primaryStage.show();
    }
}