module ntrx.planifier {
    requires kotlin.stdlib;
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlinx.coroutines.core.jvm;
    requires com.google.gson;
    requires kotlinx.coroutines.javafx;
    
    
    exports com.neoterux.espfier;
    exports com.neoterux.espfier.view.controllers;
    exports com.neoterux.espfier.custom;
    exports com.neoterux.espfier.utils;
    exports com.neoterux.espfier.data;
    exports com.neoterux.espfier.data.adapter;
    
    opens com.neoterux.espfier.view.controllers;
    opens com.neoterux.espfier.data;
    opens com.neoterux.espfier.data.adapter;
    opens com.neoterux.espfier.custom;
}
