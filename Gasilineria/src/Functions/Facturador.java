package Functions;

import Model.Gasolinera;

public class Facturador implements Runnable{
    private Gasolinera gasolinera;
    public Facturador(Gasolinera gasolinera){
        this.gasolinera = gasolinera;
    }
    @Override
    public void run() {
        while(true){
            gasolinera.despachar();
        }
    }
}
