package Functions;

import Model.Gasolinera;

public class Guardia implements Runnable{
    private Gasolinera gasolinera;
    public Guardia(Gasolinera gasolinera){
        this.gasolinera = gasolinera;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gasolinera.guardia();

        }
    }
}
