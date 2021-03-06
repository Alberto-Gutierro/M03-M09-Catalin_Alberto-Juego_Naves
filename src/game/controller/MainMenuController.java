package game.controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import statVars.Strings;
import game.SceneStageSetter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends SceneStageSetter{

    @FXML public void playGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("game/fxml/game.fxml"));
            Parent root = loader.load();

            stage.setMaximized(true);

            scene = new Scene(root, stage.getWidth(), stage.getHeight());

            GameController gameController = loader.getController();
            gameController.beforeStartGame(stage,scene, 1, "", gameController.getPane(), null);
            gameController.start(false);

            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML public void openMultiplayer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("game/fxml/multiplayerMenu.fxml"));
            Parent root = loader.load();

            scene = new Scene(root, stage.getWidth(), stage.getHeight());

            MultiplayerMenuController multiplayerMenuController = loader.getController();
            multiplayerMenuController.setScene(scene);
            multiplayerMenuController.setStage(stage);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML public void closeGame() {
        Platform.exit();
        System.exit(0);
    }

}
