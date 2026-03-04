module tp.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    opens school.coda.overlord.javafx to javafx.graphics, javafx.fxml;
    exports school.coda.overlord.javafx;
}