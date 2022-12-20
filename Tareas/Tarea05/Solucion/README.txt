                               ━━━━━━━━━━
                                TAREA 5
                               ━━━━━━━━━━


Equipo: Computólogos Unidos

Para ejecutar los archivos, primero los compilamos con el siguiente
comando:

┌────
│ mvn compile
└────

Después ejecutamos el archivo con las pruebas:

┌────
│ mvn -q exec:java -f pom.xml -Dexec.mainClass=org.concurrente.tarea5.LockTest
└────

Es necesario descomentar la prueba en el archivo. Para obtener el tiempo
que tarda la prueba, podemos usar el comando `time':

┌────
│ time mvn -q exec:java -f pom.xml -Dexec.mainClass=org.concurrente.tarea5.LockTest
└────
