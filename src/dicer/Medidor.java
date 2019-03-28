package dicer;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

class Contador extends Thread{
    
    private long tmp, time;
    private final DecimalFormat formato;
    private boolean encendido;
    
    public Contador(){
        encendido = true;
        tmp = 0;
        formato = new DecimalFormat("###,###,###,###");
        time = System.currentTimeMillis();
    }
    
    @Override
    public void run(){
        while(encendido){
            try {
                long diff = System.currentTimeMillis()-time;
                if(diff > 4000){
                    Dicer.mensaje("Velocidad: "+(formato.format(tmp/diff*1000))+" operaciones/segundo", "CONTADOR");
                    tmp = 0;
                    time = System.currentTimeMillis();
                }
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Contador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void interrupt(){
        encendido = false;
    }
    
    public void actualizar(long cantidad){
        tmp += cantidad;
    }
    
}

public class Medidor {

    private Contador contador;
    
    void iniciar() {
        contador = new Contador();
        contador.start();
    }
    
    void detener() {
        contador.interrupt();
        contador = null;
    }

    void actualizar(long total) {
        if(contador != null)
            contador.actualizar(total);
    }
    
}
