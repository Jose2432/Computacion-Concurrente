package org.practica2.ejercicios.filtros;

public enum Component {
    RED("Componente Rojo"),
    GREEN("Componente Verde"),
    BLUE("Componente Azul");

    private String name;

    private Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Component getComponent(String name) {
        if (name == "Componente Rojo") {
            return RED;
        } else if (name == "Componente Verde") {
            return GREEN;
        } else {
            return BLUE;
        }
    }
}
