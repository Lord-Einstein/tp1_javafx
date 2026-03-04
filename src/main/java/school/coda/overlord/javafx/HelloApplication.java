package school.coda.overlord.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        HBox topMenu = new HBox(10);
        topMenu.setStyle("-fx-background-color : #333; -fx-padding : 10px");

        Button btnSave = new Button("Enregistrer");
        Button btnOptions = new Button("Options");

        topMenu.getChildren().addAll(btnSave, btnOptions);
        root.setTop(topMenu);

        VBox leftBar = new VBox(15);
        leftBar.setStyle("-fx-background-color : #f4f4f4; -fx-padding : 15px");

        Text label = new Text("Catégories");
        Button cat1 = new Button("Utilisateurs");
        Button cat2 = new Button("Réglages");

        leftBar.getChildren().addAll(label, cat1, cat2);
        root.setLeft(leftBar);


        GridPane centerGrid = new GridPane();
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerGrid.setStyle("-fx-padding : 20px;");

        centerGrid.add(new Text("Nom: "), 0, 0);
        centerGrid.add(new TextField(), 1, 0);

        centerGrid.add(new Text("Email: "), 0, 1);
        centerGrid.add(new TextField(), 1, 1);

        root.setCenter(centerGrid);

        Text status = new Text("Ready !");
        HBox bottomBar = new HBox(10, status);
        bottomBar.setStyle("-fx-padding: 5; -fx-border-color: #ccc; -fx-border-width: 1 0 0 0;");
        root.setBottom(bottomBar);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Mon Dashboard JavaFX");
        stage.setScene(scene);
        stage.show();

    }

    static void main(String[] args){
        Application.launch(HelloApplication.class, args);
    }

}

