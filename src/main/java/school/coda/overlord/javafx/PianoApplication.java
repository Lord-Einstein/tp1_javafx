package school.coda.overlord.javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;


public class PianoApplication extends Application{

    private final Map<KeyCode, Button> keyCodeButtonMap = new HashMap<>();
    private final Map<Button, String> buttonNoteMap = new HashMap<>();

    private static final String STYLE_NORMAL = """
         -fx-background-color: linear-gradient(to bottom, #1a1a1a 0%, #806010 60%, #ffd700 100%);
         -fx-background-radius: 0 0 10 10;
         -fx-border-color: #333;
         -fx-border-width: 0 1 1 1;
         -fx-border-radius: 0 0 10 10;
         -fx-text-fill: white;
         -fx-font-weight: bold;
         -fx-font-size: 14px;
         -fx-alignment: BOTTOM_CENTER;
         -fx-padding: 0 0 30 0;
         -fx-cursor: hand;
""";

    private static final String STYLE_HOVER = """
        -fx-background-color: linear-gradient(to bottom, #333333 0%, #b8860b 60%, #ffe066 100%);
        -fx-background-radius: 0 0 10 10;
        -fx-border-color: #555;
        -fx-border-width: 0 1 1 1;
        -fx-border-radius: 0 0 10 10;
        -fx-text-fill: white;
        -fx-font-weight: bold;
        -fx-font-size: 14px;
        -fx-alignment: BOTTOM_CENTER;
        -fx-padding: 0 0 30 0;
""";

    private static final String STYLE_PRESSED = """
        -fx-background-color: linear-gradient(to bottom, #f1c40f 0%, #b7950b 100%);
        -fx-background-radius: 0 0 15 15;
        -fx-text-fill: white;
        -fx-font-weight: bold;
        -fx-font-size: 14px;
        -fx-alignment: BOTTOM_CENTER;
        -fx-padding: 0 0 30 0;
""";


    public void start(Stage stage){

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #111111;");

        HBox pianoKeyBoard = new HBox(30);
        pianoKeyBoard.setAlignment(Pos.CENTER);
        pianoKeyBoard.setStyle("-fx-padding: 20;");

        String[] notes = {"DO", "RE", "MI", "FA", "SOL", "LA", "SI"};
        KeyCode[] keyCodes = {KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7};

        for(int i = 0; i < notes.length; i++){
            String note = notes[i];
            KeyCode keyCode = keyCodes[i];

            Button key = createGoldKey(note);

            keyCodeButtonMap.put(keyCode, key);
            buttonNoteMap.put(key, note);

            pianoKeyBoard.getChildren().add(key);
        }

        root.getChildren().add(pianoKeyBoard);

        Scene scene = new Scene(root, 900, 600);

        scene.setOnKeyPressed(event -> {
           Button btn = keyCodeButtonMap.get(event.getCode());
           if(btn != null){
               pressKeyAction(btn);
           }
        });

        scene.setOnKeyReleased(event -> {
            Button btn = keyCodeButtonMap.get(event.getCode());
            if(btn != null){
                releaseKeyAction(btn);
            }
        });

        stage.setTitle("Overlord GoldFx Piano");
        stage.setScene(scene);
        stage.show();

    }

    private void releaseKeyAction(Button key) {
        key.setStyle(STYLE_NORMAL);
        applyNormalEffect(key);
    }

    private void pressKeyAction(Button key) {
        String note = buttonNoteMap.get(key);
        playSound(note);

        key.setStyle(STYLE_PRESSED);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.GOLD);
        glow.setWidth(50);
        glow.setHeight(50);
        glow.setRadius(40);
        glow.setSpread(0.5);
        key.setEffect(glow);
    }

    private void applyNormalEffect(Button key) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setRadius(20);
        shadow.setOffsetY(10);
        shadow.setSpread(0.2);
        key.setEffect(shadow);
    }

    private Button createGoldKey(String note) {
        Button key = new  Button(note);
        key.setPrefSize(80, 350);

        applyNormalEffect(key);
        key.setStyle(STYLE_NORMAL);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setRadius(20);
        shadow.setOffsetY(10);
        shadow.setSpread(0.2);

        key.setEffect(shadow);
        key.setStyle(STYLE_NORMAL);

        key.setOnMouseEntered(event -> {
            key.setStyle(STYLE_HOVER);
            key.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.GOLD, 10, 0.3, 0, 10));
        });

        key.setOnMouseExited(e -> {
            key.setStyle(STYLE_NORMAL);
            key.setEffect(shadow);
        });

        key.setOnMousePressed(event -> {
            pressKeyAction(key);
        });

        key.setOnMouseReleased(event -> {
            releaseKeyAction(key);
        });

        return key;
    }

    private void playSound(String note) {
        try {
            String fileName = note.toLowerCase() + ".wav";
            var resource = getClass().getResource("/sounds/" + fileName);
            if (resource != null) {
                new AudioClip(resource.toExternalForm()).play();
            }
        } catch (Exception e) {
            System.err.println("Erreur son: " + e.getMessage());
        }
    }

    static void main(String[] args){
        Application.launch(PianoApplication.class, args);
    }
}
