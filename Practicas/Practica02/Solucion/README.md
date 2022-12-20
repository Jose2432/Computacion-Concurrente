# Práctica 2
-   **Equipo:** Computólogos Unidos

Para ejecutar la interfaz gráfica nos situamos en esta ruta y ejecutamos el
siguiente comando dentro de la terminal:

    mvn clean javafx:run

Es necesario java 11 y javafx 11.

Para ejecutar los archivos para probar el tiempo de los ejercicios se utiliza el
siguientes comandos:

-   Filtros secuenciales:

        mvn -q exec:java -f pom.xml -Dexec.mainClass=org.practica2.ejercicios.FiltrosSecuencial

-   Filtros concurrentes:

        mvn -q exec:java -f pom.xml -Dexec.mainClass=org.practica2.ejercicios.FiltrosConcurrentes

-   Ejercicios secuenciales:

        mvn -q exec:java -f pom.xml -Dexec.mainClass=org.practica2.ejercicios.Secuencial

-   Ejercicios concurrentes:

        mvn -q exec:java -f pom.xml -Dexec.mainClass=org.practica2.ejercicios.Concurrente

