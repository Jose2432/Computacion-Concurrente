package org.practica2.ejercicios.filtros;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Filters {

    protected Image image;
    protected int hilos;

    public Filters(Image image) {
        this.image = image;
    }

    public Filters(Image image, int hilos) {
        this(image);
        this.hilos = hilos;
    }

    public int getRed(Color c) {
        return (int) Math.round(c.getRed() * 255);
    }

    public int getGreen(Color c) {
        return (int) Math.round(c.getGreen() * 255);
    }

    public int getBlue(Color c) {
        return (int) Math.round(c.getBlue() * 255);
    }

    public abstract Image createImage();
    public abstract Image concurrentCreateImage();
}
