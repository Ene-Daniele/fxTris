module fxtris.fxtris {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;

    opens fxtris.Main to javafx.fxml;
    exports fxtris.Main;
}