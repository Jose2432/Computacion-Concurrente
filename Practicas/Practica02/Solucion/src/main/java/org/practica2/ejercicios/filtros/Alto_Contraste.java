package org.practica2.ejercicios.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;

public class Alto_Contraste extends Filters {

    public Alto_Contraste(Image image) {
        super(image);
    }

    public Alto_Contraste(Image image, int hilos) {
    super(image, hilos);
    }

    @Override
    public Image createImage() {

        int height = Double.valueOf(image.getHeight()).intValue();
        int width = Double.valueOf(image.getWidth()).intValue();

        WritableImage writableImage = new WritableImage(width, height);

        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                pixelWriter.setColor(x, y, Color.gray(gray));
                if ((color.getRed() * 255) > 127 && (color.getGreen() * 255) > 127 && (color.getBlue() * 255) > 127) {
                    pixelWriter.setColor(x, y, Color.gray(1));
                } else {
                    pixelWriter.setColor(x, y, Color.gray(0));
                }
            }
        }

        return writableImage;
    }

    @Override
    public Image concurrentCreateImage() {
        int height = Double.valueOf(image.getHeight()).intValue();
        int width = Double.valueOf(image.getWidth()).intValue();

        WritableImage writableImage = new WritableImage(width, height);

        List<Thread> hilosL = new ArrayList<>();
        
        for(int i = 0; i < height; ++i){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    PixelReader pixelReader = image.getPixelReader();
                    PixelWriter pixelWriter = writableImage.getPixelWriter();
                    for (int x = 0; x < width; x++) {
                        Color color = pixelReader.getColor(x, Integer.parseInt(Thread.currentThread().getName()));
                        double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                        pixelWriter.setColor(x, Integer.parseInt(Thread.currentThread().getName()), Color.gray(gray));
                        if ((color.getRed() * 255) > 127 && (color.getGreen() * 255) > 127 && (color.getBlue() * 255) > 127) {
                            pixelWriter.setColor(x, Integer.parseInt(Thread.currentThread().getName()), Color.gray(1));
                        } else {
                            pixelWriter.setColor(x,Integer.parseInt(Thread.currentThread().getName()), Color.gray(0));
                        }
                    }        
                }
            },""+i);
            t.start();
            hilosL.add(t);

            if(hilos == hilosL.size()){
                for(Thread threads : hilosL){
                    try {
                        threads.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hilosL.clear();
            }
        }

        for(Thread threads : hilosL){
            try {
                threads.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return writableImage;
    }
}
