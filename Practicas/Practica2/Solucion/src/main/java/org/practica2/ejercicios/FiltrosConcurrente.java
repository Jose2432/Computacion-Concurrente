package org.practica2.ejercicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.practica2.ejercicios.filtros.Component;
import org.practica2.ejercicios.filtros.Filter;
import org.practica2.interfaz.model.ImageFilterModel;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class FiltrosConcurrente {

    public static void main(String[] args) {
        var imagePath = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("icons/test.png");
        var image = new Image(imagePath);

        long tiempoInicial, tiempoTotal;
        HashMap<Filter, List<Long>> map = new HashMap<Filter, List<Long>>();

        for (int hilos : List.of(1, 5, 10, 100)) {
            for (Filter filter : Filter.values()) {
                for (int i = 0; i < 20; i++) {
                    ImageFilterModel model = new ImageFilterModel(image, filter, hilos);

                    if (filter == Filter.COMPONENTES_RGB) {
                        model.setColor(Color.BLUE);
                    } else if (filter == Filter.SINGLE_COLOR) {
                        model.setComponent(Component.BLUE);
                    }

                    tiempoInicial = System.nanoTime();
                    model.getFilteredImage();
                    tiempoTotal = System.nanoTime() - tiempoInicial;

                    map.computeIfAbsent(filter, k -> new ArrayList<>()).add(tiempoTotal);
                }
            }

            map.forEach((key, value) -> {
                long suma = value.stream().mapToLong(Long::longValue).sum();
                System.out.printf("En promedio, el filtro %s con %d hilos tomó %2.9f segundos\n",
                                key, hilos,  (suma / (1000000000.0 * 20)));
            });
        }
    }
}
