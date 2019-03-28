# Dizer
Simulador de tirar dados de forma aleatoria.

### Comandos Dicer:
+	AYUDA: Muestra este mensaje.
+	HILOS n: Predispone el uso de n hilos.
+	DADOS n a: Establece la forma de los datos. n cantidad, a numero de caras.
+	RESUMEN: Muestra la configuracion del simulador.
+	OBJETIVO SUMAR ALMENOS/HASTA/JUSTO n: Establece el caso exitoso a que la suma sea almenos/hasta/justo n.
+	OBJETIVO SACAR ALMENOS/HASTA/JUSTO a n: Establece el caso exitoso a se saquen almenos/hasta/justo n veces el resultado a.
+	TIRAR: Comienza la simulacion.
+	MIRAR: Devuelve los resultados obtenidos.
+	PARAR: Detiene la simulacion y devuelve los resultados.
+	LIMPIAR: Borra el log.
+   CONTADOR: ACTIVAR/DESACTIVAR
+   SERVIDOR: INICIAR/ESTADO/CERRAR: comienza/detiene el servidor o muestra las conexiones.
+   CLIENTE: INICIAR/ESTADO/CERRAR: comienza/detiene como trabajador o muestra el estado de la conexion.
## Ejemplos
Sacar un seis tirando una vez un dado de seis caras.
````
objetivo sacar justo 6 1
````
Sumar siete tirando cuatro dados de seis caras.
````
dados 4 6
objetivo sumar justo 7
````
Clusterizar una simulación.

Servidor:
````
objetivo sumar justo 1
servidor iniciar
contador activar
tirar
````
Cliente, remplazar la dirección por la IP del servidor:
````
cliente iniciar 127.0.0.1
````