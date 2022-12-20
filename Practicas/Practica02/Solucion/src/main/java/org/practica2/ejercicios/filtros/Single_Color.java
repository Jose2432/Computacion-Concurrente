package org.practica2.ejercicios.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;

public class Single_Color extends Filters {

    private Component component;
    private Color color;

    public Single_Color(Image image, int hilos) {
        super(image, hilos);
    }

    public Single_Color(Image image, int hilos, Component component) {
        this(image, hilos);
        this.component = component;
    }

    private double getColor() {
        switch (component) {
        case RED:
            return color.getRed();
        case GREEN:
            return color.getGreen();
        case BLUE:
            return color.getBlue();
        default:
            return 0.0;
        }
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
                color = pixelReader.getColor(x, y);
                double gray = getColor();
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

        switch (component) {
        case RED:
            for (int i = 0; i < height; ++i) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PixelReader pixelReader = image.getPixelReader();
                        PixelWriter pixelWriter = writableImage.getPixelWriter();
                        int y = Integer.parseInt(Thread.currentThread().getName());
                        for (int x = 0; x < width; x++) {
                            color = pixelReader.getColor(x, y);
                            double gray = color.getRed();
                            pixelWriter.setColor(x, y, Color.gray(gray));
                        }
                    }
                }, "" + i);
                t.start();
                hilosL.add(t);

                if (hilos == hilosL.size()) {
                    for (Thread threads : hilosL) {
                        try {
                            threads.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    hilosL.clear();
                }
            }

            for (Thread threads : hilosL) {
                try {
                    threads.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            break;
        case GREEN:
            for (int i = 0; i < height; ++i) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PixelReader pixelReader = image.getPixelReader();
                        PixelWriter pixelWriter = writableImage.getPixelWriter();
                        int y = Integer.parseInt(Thread.currentThread().getName());
                        for (int x = 0; x < width; x++) {
                            color = pixelReader.getColor(x, y);
                            double gray = color.getGreen();
                            pixelWriter.setColor(x, y, Color.gray(gray));
                        }
                    }
                }, "" + i);
                t.start();
                hilosL.add(t);
                if (hilos == hilosL.size()) {
                    for (Thread threads : hilosL) {
                        try {
                            threads.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    hilosL.clear();
                }
            }

            for (Thread threads : hilosL) {
                try {
                    threads.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            break;
        case BLUE:
            for (int i = 0; i < height; ++i) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PixelReader pixelReader = image.getPixelReader();
                        PixelWriter pixelWriter = writableImage.getPixelWriter();
                        int y = Integer.parseInt(Thread.currentThread().getName());
                        for (int x = 0; x < width; x++) {
                            color = pixelReader.getColor(x, y);
                            double gray = color.getBlue();
                            pixelWriter.setColor(x, y, Color.gray(gray));
                        }
                    }
                }, "" + i);
                t.start();
                hilosL.add(t);

                if (hilos == hilosL.size()) {
                    for (Thread threads : hilosL) {
                        try {
                            threads.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    hilosL.clear();
                }
            }

            for (Thread threads : hilosL) {
                try {
                    threads.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            break;
        }

        return writableImage;
    }

}
