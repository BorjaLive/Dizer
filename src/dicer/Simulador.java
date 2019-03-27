package dicer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class Simulador {
    
    private static final long CASOS_POR_VUELCO = (long) 1e6;
    private static DecimalFormat ENTERO, PROBABILIDAD;
    
    private static int hilos = 1;
    private static int dados = 1;
    private static int caras = 6;
    
    private static int objetivo = 0, objD1 = 0, objD2 = 0;
    
    /* Codigos de objetivos
        0 nada
        1 sumar almenos, 2 sumar hasta, 3 sumar justo objD1
        4 scar almenos, 5 sacar hasta, 6 sacar justo objD1  objD2 veces
    */
    
    private static BigInteger casosExito, casosTotal;
    private static Thread[] threads;
    
    public static void iniciar(){
        threads = null;
        ENTERO = new DecimalFormat("###,###,###,###");
        PROBABILIDAD = new DecimalFormat();
        PROBABILIDAD.setMaximumFractionDigits(20);
        PROBABILIDAD.setMinimumFractionDigits(0);
        PROBABILIDAD.setGroupingUsed(false);
    }
    
    public static boolean setHilos(int n){
        if(n < 1 || n > 100)
            return false;
        
        hilos = n;
        return true;
    }
    public static boolean setDados(int n){
        if(n < 1)
            return false;
        
        dados = n;
        return true;
    }
    public static boolean setCaras(int n){
        if(n < 1)
            return false;
        
        caras = n;
        return true;
    }
    public static int getHilos(){
        return hilos;
    }
    public static int getDados(){
        return dados;
    }
    public static int getCaras(){
        return caras;
    }
    
    public static void setObjetivo(int obj, int d1, int d2){
        objetivo = obj;
        objD1 = d1;
        objD2 = d2;
    }
    
    public static boolean simular(){
        if(objetivo == 0 || threads != null)
            return false;
        
        casosExito = new BigInteger("0");
        casosTotal = new BigInteger("0");
        Random rand = new Random();
        
        threads = new Thread[hilos];
        for (int i = 0; i < hilos; i++) {
            threads[i] = new Thread(){
                @Override
                public void run() {
                    long total, exito;
                    while(!isInterrupted()){
                        total = 0;
                        exito = 0;
                        while(total < CASOS_POR_VUELCO && !Thread.currentThread().isInterrupted()){
                            total++;
                            switch(objetivo){
                                case 1:
                                case 2:
                                case 3://El exito depende de la suma
                                    int suma = 0;
                                    for(int i = 0; i < dados; i++)
                                        suma += rand.nextInt(caras) + 1;
                                    switch(objetivo){
                                        case 1: if(suma >= objD1) exito++;break;
                                        case 2: if(suma <= objD1) exito++;break;
                                        case 3: if(suma == objD1) exito++;break;
                                    }
                                    break;
                                case 4:
                                case 5:
                                case 6://El exito depende de sacar numeros
                                    int cantidad = 0;
                                    for(int i = 0; i < dados; i++)
                                        if(rand.nextInt(caras) + 1 == objD1)
                                            cantidad++;
                                    switch(objetivo){
                                        case 4: if(cantidad >= objD2) exito++;break;
                                        case 5: if(cantidad <= objD2) exito++;break;
                                        case 6: if(cantidad == objD2) exito++;break;
                                    }
                                    break;
                            }
                        }
                        volcar(exito, total);
                    }
                }
            };
            threads[i].start();
        }
        
        return true;
    }
    public static String detener(){
        for(int i = 0; i < threads.length; i++){
            threads[i].interrupt();
        }
        threads = null;
        
        return calcular();
    }
    public static String calcular(){
        try {
            BigDecimal resultado = new BigDecimal(casosExito).divide(new BigDecimal(casosTotal), 21, RoundingMode.HALF_EVEN);
            return "Casos totales:  "+ENTERO.format(casosTotal)+"\nCasos exitosos: "+ENTERO.format(casosExito)+"\nProbabilidad: "+PROBABILIDAD.format(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener resultados.";
        }
    }
    
    public static void volcar(long exito, long total){
        casosExito = casosExito.add(BigInteger.valueOf(exito));
        casosTotal = casosTotal.add(BigInteger.valueOf(total));
    }
    
}
