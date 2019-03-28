package dicer;

public class Dicer {

    private static Consola consola;
    
    public static void main(String[] args) {
        Simulador.iniciar();
        consola = new Consola();
        consola.setVisible(true);
    }
    
    public static void mensaje(String log, String nombre){
        consola.mensaje(log, nombre);
    }
    
}
