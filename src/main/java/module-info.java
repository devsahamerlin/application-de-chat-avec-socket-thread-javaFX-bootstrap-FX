module com.merlin.applicationchatavecsocketetthread {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.merlin.applicationchatavecsocketetthread to javafx.fxml;
    exports com.merlin.applicationchatavecsocketetthread;
}