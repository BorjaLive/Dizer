package dicer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;

class Conn extends Thread {
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    
    public Conn(Socket socket) {
        this.socket = socket;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            Dicer.mensaje("Conexion realizada.", "SERVIDOR");
        } catch (IOException ex) {
            desconnectar();
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desconnectar() {
        try {
            dos.close();
            dis.close();
            socket.close();
            Servidor.terminar(this);
        } catch (IOException ex) {
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        String msg = "";
        try {
            while(!isInterrupted()){
                msg = dis.readUTF();
                System.out.println("RECIVO: "+msg);
                String[] data = msg.split("\\|");
                if(data[0].equals("VOL")){
                    Simulador.volcar(Long.valueOf(data[1]), Long.valueOf(data[2]));
                }else if(data[0].equals("DES")){
                    desconnectar();
                    Dicer.mensaje("Un cliente se ha desconectado.", "SERVIDOR");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }
    
    public void tirar(){
        try {
            dos.writeUTF("ACT|"+Simulador.getHilos()+"|"+Simulador.getDados()+"|"+Simulador.getCaras()+"|"+
                    Simulador.getObjetivo());
            Dicer.mensaje("Enviado ACT", "SERVIDOR");
        } catch (IOException ex) {
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void parar(){
        try {
            dos.writeUTF("STP");
            Dicer.mensaje("Enviado STP", "SERVIDOR");
        } catch (IOException ex) {
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

public class Servidor {
    
    private static boolean encendido = false, tirando = false;
    private static ArrayList<Conn> conexiones;
    
    public static void init() {
        conexiones = new ArrayList<>();
        
        new Thread(){
            @Override
            public void run(){
                System.out.print("Inicializando servidor... ");
                try {
                    ServerSocket escucha = new ServerSocket(10578);
                    System.out.println("\t[OK]");
                    encendido = true;
                    while (true) {
                        Socket socket;
                        socket = escucha.accept();
                        System.out.println("Nueva conexión entrante: "+socket);
                        Conn conexion = new Conn(socket);
                        conexiones.add(conexion);
                        conexion.start();
                        if(tirando)
                            conexion.tirar();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    public static void detener(){
        for(Conn c:conexiones)
            c.interrupt();
        conexiones.clear();
    }
    
    public static String estado(){
        return "Estado del servidor: "+(encendido?("activo\nNumero de conexiones: "+conexiones.size()):"inactivo");
    }
    
    public static void tirar(){
        tirando = true;
        for(Conn c:conexiones)
            c.tirar();
    }
    public static void parar(){
        tirando = false;
        for(Conn c:conexiones)
            c.parar();
    }
    
    protected static void terminar(Conn c){
        conexiones.remove(c);
    }
}