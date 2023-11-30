package Functions;

import Model.Gasolinera;
import javafx.scene.layout.AnchorPane;

public class Despachador implements Runnable{
    private Gasolinera gasolinera;
    private AnchorPane padre;
    public Despachador(AnchorPane padre, Gasolinera gasolinera){
        this.gasolinera = gasolinera;
        this.padre=padre;
    }

    @Override
    public void run() {


        while (true){
            gasolinera.servicioPedir();
        }
    }
}
