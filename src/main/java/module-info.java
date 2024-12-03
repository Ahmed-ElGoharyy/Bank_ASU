module com.asu_bank.bank_asu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.asu_bank.bank_asu to javafx.fxml;
    exports com.asu_bank.bank_asu;
    exports com.asu_bank.bank_asu.Controllers;
    opens com.asu_bank.bank_asu.Controllers to javafx.fxml;
}