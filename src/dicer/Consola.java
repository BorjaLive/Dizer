/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicer;

/**
 *
 * @author Arlin-T2
 */
public class Consola extends javax.swing.JFrame {

    /**
     * Creates new form Consola
     */
    public Consola() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        input = new javax.swing.JTextField();
        send = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        output.setColumns(20);
        output.setRows(5);
        jScrollPane1.setViewportView(output);

        send.setText("Ejecutar");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(send)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(send))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        //Parsear el texto de entrada
        String[] data = input.getText().toUpperCase().split("\\ ");
        input.setText("");
        
        if(data[0].equals("AYUDA")){
            log("Comandos Dicer:\n\tAYUDA: Muestra este mensaje."
                    + "\n\tHILOS n: Predispone el uso de n hilos."
                    + "\n\tDADOS n a: Establece la forma de los datos. n cantidad, a numero de caras."
                    + "\n\tRESUMEN: Muestra la configuracion del simulador."
                    + "\n\tOBJETIVO SUMAR ALMENOS/HASTA/JUSTO n: Establece el caso exitoso a que la suma sea almenos/hasta/justo n."
                    + "\n\tOBJETIVO SACAR ALMENOS/HASTA/JUSTO a n: Establece el caso exitoso a se saquen almenos/hasta/justo n veces el resultado a."
                    + "\n\tTIRAR: Comienza la simulacion."
                    + "\n\tMIRAR: Devuelve los resultados obtenidos."
                    + "\n\tPARAR: Detiene la simulacion y devuelve los resultados."
                    + "\n\tLIMPIAR: Borra el log."
                    + "\n\tCONTADOR: ACTIVAR/DESACTIVAR"
                    + "\n\tSERVIDOR: INICIAR/ESTADO/CERRAR: comienza/detiene el servidor o muestra las conexiones."
                    + "\n\tCLIENTE: INICIAR/ESTADO/CERRAR: comienza/detiene como trabajador o muestra el estado de la conexion.");
        }else if(data[0].equals("HILOS")){
            if(data.length >= 2){
                try {
                    if(Simulador.setHilos(Integer.parseInt(data[1])))
                        log("Numero de hilos actualizado correctamente.");
                    else
                        log("El numero de hilos debe estar entre 1 y 100.");
                } catch (Exception e) {
                    log("Introduzca un numero en el primer parametro.");
                }
            }else{
                log("Error de sintaxis.");
            }
        }else if(data[0].equals("DADOS")){
            boolean error = false;
            if(data.length >= 3){
                try {
                    if(!Simulador.setDados(Integer.parseInt(data[1]))){
                        log("El numero de dados debe ser mayor o igual que 1.");
                        error = true;
                    }else if(!Simulador.setCaras(Integer.parseInt(data[2]))){
                        log("El numero de caras debe ser mayor o igual que 1.");
                        error = true;
                    }
                    if(!error)
                        log("Dados actualizados correctamente.");
                } catch (Exception e) {
                    log("Introduzca un numero en el primer y segundo parametro.");
                }
            }else{
                log("Error de sintaxis.");
            }
        }else if(data[0].equals("RESUMEN")){
            log("Resumen del simulador:"
                    + "\n\tNumero de hilos: "+Simulador.getHilos()
                    + "\n\tNumero de dados: "+Simulador.getDados()
                    + "\n\tNumero de caras: "+Simulador.getCaras());
        }else if(data[0].equals("OBJETIVO")){
            boolean error = false;
            if(data.length >= 2){
                if(data[1].equals("SUMAR")){
                    if(data.length >= 4){
                        try {
                            int d1 = Integer.parseInt(data[3]);
                            if(data[2].equals("ALMENOS")){
                                Simulador.setObjetivo(1, d1, 0);
                            }else if(data[2].equals("HASTA")){
                                Simulador.setObjetivo(2, d1, 0);
                            }else if(data[2].equals("JUSTO")){
                                Simulador.setObjetivo(3, d1, 0);
                            }else{
                                log("No se reconoce el segundo parametro.");
                                error = true;
                            }
                            if(!error)
                                log("Objetivo actualizado correctamente");
                        } catch (Exception e) {
                            log("Introduzca un numero en el tercer parametro.");
                        }
                    }else
                        log("Error de sintaxis.");
                }else if(data[1].equals("SACAR")){
                    if(data.length >= 5){
                        try {
                            int d1 = Integer.parseInt(data[3]);
                            int d2 = Integer.parseInt(data[4]);
                            if(data[2].equals("ALMENOS")){
                                Simulador.setObjetivo(4, d1, d2);
                            }else if(data[2].equals("HASTA")){
                                Simulador.setObjetivo(5, d1, d2);
                            }else if(data[2].equals("JUSTO")){
                                Simulador.setObjetivo(6, d1, d2);
                            }else{
                                log("No se reconoce el segundo parametro.");
                                error = true;
                            }
                            
                            if(!error)
                                log("Objetivo actualizado correctamente");
                        } catch (Exception e) {
                            log("Introduzca un numero en el tercer y cuarto parametro.");
                        }
                    }else
                        log("Error de sintaxis.");
                }else
                    log("Primer parametro no reconocido");
            }else{
                log("Error de sintaxis.");
            }
        }else if(data[0].equals("TIRAR")){
            if(Simulador.simular())
                log("La simulacion ha empezado.");
            else
                log("Error al iniciar la simulacion.");
        }else if(data[0].equals("PARAR")){
            log("Simulacion detenida.\n"+Simulador.detener());
        }else if(data[0].equals("MIRAR")){
            log("Resultados parciales.\n"+Simulador.calcular());
        }else if(data[0].equals("LIMPIAR")){
            output.setText("");
        }else if(data[0].equals("SERVIDOR")){
            if(data.length >= 2){
                if(data[1].equals("INICIAR")){
                    Simulador.iniciarServidor();
                    log("Servidor iniciado.");
                }else if(data[1].equals("ESTADO")){
                    log(Simulador.estadoServidor());
                }else if(data[1].equals("CERRAR")){
                    Simulador.detenerServidor();
                    log("Servidor apagado.");
                }
            }else
                log("Error de sintaxis.");
        }else if(data[0].equals("CLIENTE")){
            //Importante incluir la IP
            if(data.length >= 2){
                if(data[1].equals("INICIAR")){
                    if(data.length >= 3){
                        Simulador.iniciarCliente(data[2]);
                        log("Cliente iniciado");
                    }else
                        log("Se necesita una direccion IP.");
                }else if(data[1].equals("ESTADO")){
                    log(Simulador.estadoCliente());
                }else if(data[1].equals("CERRAR")){
                    Simulador.detenerCliente();
                    log("Cliente apagado");
                }
            }else
                log("Error de sintaxis.");
        }else if(data[0].equals("CONTADOR")){
            //Importante incluir la IP
            if(data.length >= 2){
                if(data[1].equals("ACTIVAR")){
                    Simulador.setMedidor(true);
                    log("Contador activado.");
                }else if(data[1].equals("DESACTIVAR")){
                    Simulador.setMedidor(false);
                    log("Contador desactivado.");
                }
            }else
                log("Error de sintaxis.");
        }else{
            log("Comando no reconocido. Prueve AYUDA.");
        }
        
    }//GEN-LAST:event_sendActionPerformed

    private void log(String text){
        output.setText(output.getText()+text+"\n");
        output.setCaretPosition(output.getDocument().getLength());
    }
    public void mensaje(String log, String nombre){
        log("["+nombre+"]: "+log);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Consola().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField input;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea output;
    private javax.swing.JButton send;
    // End of variables declaration//GEN-END:variables
}
