package Functions;

import Model.Client;
import Model.Gasolinera;
import javafx.scene.layout.AnchorPane;
import Controller.Controller;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CreateClient implements Runnable {
    private final AnchorPane anchor;
    private final Gasolinera gasolinera;
    private final Controller controller;

    public CreateClient(AnchorPane anchor, Gasolinera gasolinera, Controller controller) {
        this.anchor = anchor;
        this.gasolinera = gasolinera;
        this.controller = controller;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            Client client = new Client(anchor, gasolinera);
            Thread clientThread = new Thread(client);
            clientThread.setName("Client " + (i + 1));

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(8000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clientThread.setDaemon(true);
            clientThread.start();
        }
    }
}

