package org.practica2.ejercicios.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;

public class Correctud extends Filters {

    public Correctud(Image image) {
        super(image);
    }

    public Correctud(Image image, int hilos) {
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
                double gray = (color.getRed() * 0.3) + (color.getGreen() * 0.59) + (color.getBlue() * 0.11);
                pixelWriter.setColor(x, y, Color.gray(gray));
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
                        Color color = pixelReader.getColor(x,Integer.parseInt(Thread.currentThread().getName()));
                        double gray = (color.getRed() * 0.3) + (color.getGreen() * 0.59) + (color.getBlue() * 0.11);
                        pixelWriter.setColor(x,Integer.parseInt(Thread.currentThread().getName()), Color.gray(gray));
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
