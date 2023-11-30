package Controller;

import Functions.Despachador;
import Functions.Facturador;
import Functions.CreateClient;
import Functions.Guardia;
import Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


import java.util.Observable;
import java.util.Observer;


public class Controller implements Observer {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button btnInit;

    @FXML
    private Label lbldespacher1;

    @FXML
    void Finalizar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void IniciarAnimacion(ActionEvent event) {
        btnInit.setDisable(true);
        System.out.println("Inicio de animacion");

        Gasolinera gasolinera = new Gasolinera();
        gasolinera.addObserver(this);

        Despachador despachador = new Despachador(anchor, gasolinera);
        Thread hiloDespachador = new Thread(despachador);
        hiloDespachador.setDaemon(true);
        hiloDespachador.start();

        Guardia guardia = new Guardia(gasolinera);
        Facturador facturador = new Facturador(gasolinera);
        CreateClient createClient = new CreateClient(anchor, gasolinera, this);

        startDaemonThread(guardia);
        startDaemonThread(facturador);
        startDaemonThread(createClient);
    }

    private void startDaemonThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    Alert alert = new Alert((Alert.AlertType.CONFIRMATION));

    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            if (((String) arg).contains("ocupadoDespachador")) {
                String[] cadena = ((String) arg).split(" ");
                int numBomba = Integer.parseInt(cadena[1]);
                Platform.runLater(() -> lbldespacher1.setText("Atendiendo: " + (numBomba + 1)));
            } else if (((String) arg).contains("libreDespachador")) {
                Platform.runLater(() -> lbldespacher1.setText(""));
            } else if (((String) arg).contains("ocupado")) {
                // Procesar según el estado 'ocupado'
            } else if (((String) arg).contains("libre")) {
                // Procesar según el estado 'libre'
            } else if (((String) arg).contains("seat")) {
                String[] cadena = ((String) arg).split(" ");
                int numBomba = Integer.parseInt(cadena[1]);
                // Realizar acciones para 'seat'
            } else {
                int dato = Integer.parseInt((String) arg);
                System.out.println("Observer " + dato);
            }
        }
    }
}


