package Model;

import java.util.Arrays;
import java.util.Observable;

public class Gasolinera extends Observable {
    public boolean client;
    public int count=0;
    public boolean confirm;
    public int maxnumClient;
    public boolean[] bombs;
    public int bombsAux;
    public int numClient;
    public boolean Access;
    public int peticiones;
    public int solicitation;
    public int gas;

    public Gasolinera(){
        bombs = new boolean[20];
        Arrays.fill(bombs, false);
        solicitation =0;
        gas =0;
        confirm=false;
        maxnumClient = 0;
        peticiones=0;
        client=false;
        Access=false;
        numClient=0;
        bombsAux = -1;
    }


    public synchronized int entry(String nombre){
        int numBomba = -1;
        try {
            while (maxnumClient > 20) {
                wait();
            }
            numClient++;
            maxnumClient++;
            Access = true;
            client = true;
            for (int i = 0; i < 21; i++) {
                if (!bombs[i]) {
                    numBomba = i;
                    bombsAux = i;
                    bombs[i] = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("seat " + numBomba);
        System.out.println(numBomba);
        return numBomba;
    }

    public int pedir(){
        synchronized (this) {
            solicitation++;
            notifyAll();
            int rest= solicitation -1;
            return rest  ;
        }
    }

    public void servicioPedir() {
        synchronized (this) {
            if (solicitation <= 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notifyAll();
                setChanged();
                notifyObservers("libreDespachador " + bombsAux);
            } else {
                solicitation--;
                peticiones++;
                notifyAll();
                setChanged();
                notifyObservers("ocupadoDespachador " + bombsAux);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void despachar() {
        boolean notify = false;
        synchronized (this) {
            if (peticiones > 0) {
                gas++;
                peticiones--;
                notify = true;
            }
        }
        if (notify) {
            synchronized (this) {
                setChanged();
                notifyObservers("ocupado");
                notifyAll();
            }
        }
    }



    public synchronized void cargar() throws InterruptedException {
        while (gas <= 0) {
            wait();
        }
        gas--;
        Thread.sleep(6000
        );
    }

    public void salir(int numBombaLibre){
        synchronized (this) {
            if(!confirm){
                confirm=true;
                System.out.println(numClient+"  se fue");
            }else{
                numClient--;
                maxnumClient--;
                client=false;
                System.out.println(numClient+" Clientes en fila");
            }
            bombs[numBombaLibre] = false;
            notifyAll();
            count++;
            setChanged();
            notifyObservers("" + count);
        }
    }

    public void guardia(){
        synchronized (this) {
            while(numClient < 1 || client){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Access=false;
        }
        synchronized (this) {
            notifyAll();
        }
    }


}
