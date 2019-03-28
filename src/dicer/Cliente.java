package dicer;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.*;

class Trabajador extends Thread {
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    
    public Trabajador(String ip) {
        try {
            socket = new Socket(ip, 10578);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            Dicer.mensaje("Conexion realizada.", "CLIENTE");
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desconnectar() {
        try {
            dos.writeUTF("DES|");
            dos.close();
            dis.close();
            socket.close();
            Cliente.terminar();
            Dicer.mensaje("Enviiado DES", "CLIENTE");
        } catch (IOException ex) {
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        try {
            String msg;
            while(!isInterrupted()){
                msg = dis.readUTF();
                System.out.println("RECIVO: "+msg);
                String[] data = msg.split("\\|");
                if(data[0].equals("ACT")){
                    Dicer.mensaje("Recivido ACT", "CLIENTE");
                    Simulador.setHilos(Integer.parseInt(data[1]));
                    Simulador.setDados(Integer.parseInt(data[2]));
                    Simulador.setCaras(Integer.parseInt(data[3]));
                    Simulador.setObjetivo(Integer.parseInt(data[4]),Integer.parseInt(data[5]),Integer.parseInt(data[6]));
                    Simulador.simular();
                }else if(data[0].equals("STP")){
                    Dicer.mensaje("Recivido STP", "CLIENTE");
                    Simulador.detener();
                }
            }
            desconnectar();
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Cliente.terminar();
    }

    void enviar(long favorables, long totales) {
        try {
            dos.writeUTF("VOL|"+favorables+"|"+totales);
        } catch (IOException ex) {
            Logger.getLogger(Trabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
public class Cliente {
    
    private static Trabajador cliente;
    
    public static void init(String ip) {
        cliente = new Trabajador(ip);
        cliente.start();
    }
    
    public static void detener(){
        if(cliente != null)
            cliente.interrupt();
        cliente = null;
    }
    
    public static String estado(){
        return "Estado del trabajador: "+((cliente == null)?"apagado":"encendido");
    }
    
    protected static void terminar(){
        cliente = null;
    }
    
    public static void enviar(long favorables, long totales){
        cliente.enviar(favorables, totales);
    }
}