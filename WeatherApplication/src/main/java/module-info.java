module weatherapp.restapi.weatherapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires json.simple;
    requires java.net.http;
    requires com.google.gson;


    //opens weatherapp.restapi.weatherapplication to com.google.gson;

    opens weatherapp.restapi.weatherapplication to javafx.fxml, com.google.gson;
    exports weatherapp.restapi.weatherapplication;
}