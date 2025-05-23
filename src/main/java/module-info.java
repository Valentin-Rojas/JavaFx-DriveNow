module com.example.ra3projeto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.ra3projeto to javafx.fxml;
    exports com.example.ra3projeto;
}