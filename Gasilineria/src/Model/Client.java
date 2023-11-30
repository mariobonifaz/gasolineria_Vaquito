package Model;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

    public class Client implements Runnable {
        private final AnchorPane anchor;
        private Gasolinera gasolinera;
        private static String[] positions;

        public Client(AnchorPane anchor, Gasolinera gasolinera) {
            this.anchor = anchor;
            this.gasolinera = gasolinera;
            positions = new String[]{
                    "256 56", "347 56", "441 56", "540 56",
                    "256 130", "347 130", "441 130", "540 130",
                    "256 414", "347 414", "441 414", "540 414",
                    "256 495", "347 495", "441 495", "540 495",
                    "256 576", "347 576", "441 576", "540 576"
            };
        }

        @Override
        public void run() {
            Image client = new Image(getClass().getResource("/principal/Resource/img/carro.png").toExternalForm());
            ImageView imageView = new ImageView(client);
            Image clientFacturing = new Image(getClass().getResource("/principal/Resource/img/factura.png").toExternalForm());

            imageView.setFitWidth(75); // anchura
            imageView.setFitHeight(50); // altura
            Platform.runLater(() -> {
                imageView.setLayoutX(24);
                imageView.setLayoutY(340);
                anchor.getChildren().add(imageView);
            });

            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), imageView);
            transition2.setOnFinished(event -> {
                imageView.setOpacity(1);
                transition2.setCycleCount(1);
            });

            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    transition2.setToX(imageView.getLayoutX() + 200);
                    transition2.play();
                    transition2.setOnFinished(event -> imageView.setOpacity(1));
                });
            }

            int numBomba = gasolinera.entry(Thread.currentThread().getName());
            String[] layout = positions[numBomba].split(" ");
            Platform.runLater(() -> {
                imageView.setLayoutX(Integer.parseInt(layout[0]));
                imageView.setLayoutY(Integer.parseInt(layout[1]) + 20);
            });

            gasolinera.pedir();

            imageView.setImage(clientFacturing);
            try {
                TranslateTransition transition4 = new TranslateTransition(Duration.seconds(1), imageView);
                Platform.runLater(() -> {
                    transition4.play();
                    transition4.setOnFinished(event -> imageView.setOpacity(1));
                });

                gasolinera.cargar();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            imageView.setImage(client);
            TranslateTransition transition3 = new TranslateTransition(Duration.seconds(1), imageView);
            transition3.setOnFinished(event -> {
                imageView.setOpacity(1);
                transition3.setCycleCount(1);
            });

            gasolinera.salir(numBomba);
            Platform.runLater(() -> {
                anchor.getChildren().remove(imageView);
            });
        }
    }




