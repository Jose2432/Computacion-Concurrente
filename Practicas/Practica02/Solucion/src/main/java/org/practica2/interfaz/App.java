package org.practica2.interfaz;

import org.practica2.interfaz.views.ImageFilterView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    private static final int IMAGE_SPACING = 40;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 900;
    private static final String ICONO_CIENCIAS = "icons/fc.png";

    private double calcImageWidth(double width) {
        return (width - IMAGE_SPACING) / 2;
    }

    @Override
    public void start(Stage stage) {
        String iconPath = getClass()
            .getClassLoader()
            .getResource(ICONO_CIENCIAS)
            .toString();
        stage.getIcons().add(new Image(iconPath));
        stage.setTitle("Filtros de Imágenes");

        ImageFilterView view = new ImageFilterView(calcImageWidth(DEFAULT_WIDTH));
        var scene = new Scene(view, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            view.setImageView(calcImageWidth(scene.getWidth()));
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
