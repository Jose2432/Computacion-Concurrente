package org.practica2.interfaz.model;

import org.practica2.ejercicios.filtros.Filters;
import org.practica2.ejercicios.filtros.MotionBlur;
import org.practica2.ejercicios.filtros.Promedio;
import org.practica2.ejercicios.filtros.Correctud;
import org.practica2.ejercicios.filtros.Correctud_2;
import org.practica2.ejercicios.filtros.Single_Color;
import org.practica2.ejercicios.filtros.Blur;
import org.practica2.ejercicios.filtros.Alto_Contraste;
import org.practica2.ejercicios.filtros.ComponentesRGB;
import org.practica2.ejercicios.filtros.Sharpen;
import org.practica2.ejercicios.filtros.Filter;
import org.practica2.ejercicios.filtros.Blur2;
import org.practica2.ejercicios.filtros.Component;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ImageFilterModel {

    private Color color;
    private Filter filter;
    private Image image;
    private Component component;
    private int hilos;

    public ImageFilterModel(Image image, Filter filter) {
        this.image = image;
        this.filter = filter;
    }

    public ImageFilterModel(Image image, Filter filter, int hilos) {
        this(image, filter);
        this.hilos = hilos;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    private Filters createFilters() {
        switch(filter) {
            case PROMEDIO: return new Promedio(image, hilos);
            case CORRECTUD: return new Correctud(image, hilos);
            case CORRECTUD_2: return new Correctud_2(image, hilos);
            case SINGLE_COLOR: return new Single_Color(image, hilos, component);
            case BLUR: return new Blur(image, hilos);
            case BLUR_2: return new Blur2(image, hilos);
            case MOTION_BLUR: return new MotionBlur(image, hilos);
            case SHARPEN: return new Sharpen(image, hilos);
            case COMPONENTES_RGB: return new ComponentesRGB(image, hilos, color);
            case ALTO_CONTRASTE: return new Alto_Contraste(image, hilos);
            default: return null;
        }
    }

    private Image createImage() {
        if (filter == Filter.SINGLE_COLOR && component == null) {
            return image;
        } else if (filter == Filter.COMPONENTES_RGB && color == null) {
            return image;
        } else {
            return createFilters().createImage();
        }
    }

    public Image getFilteredImage() {
        return createImage();
    }
}
