package org.practica2.ejercicios.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;

public class Sharpen extends Filters {

    public Sharpen(Image image) {
        super(image);
    }

    public Sharpen(Image image, int hilos) {
        super(image, hilos);
    }

    @Override
    public Image createImage() {
        int height = Double.valueOf(image.getHeight()).intValue();
        int width = Double.valueOf(image.getWidth()).intValue();

        WritableImage writableImage = new WritableImage(width, height);

        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        double sharpen[][] = {
                { -1, -1, -1 },
                { -1, 9, -1 },
                { -1, -1, -1 }
        };

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double red = 0;
                double green = 0;
                double blue = 0;

                // pixel (-1,-1)
                Color c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1,
                        ((y - 1) % height < 0) ? height - 1 : y - 1);
                red += getRed(c) * sharpen[0][0];
                green += getGreen(c) * sharpen[0][0];
                blue += getBlue(c) * sharpen[0][0];

                // pixel (0,-1)
                c = pixelReader.getColor(x % width, ((y - 1) % height < 0) ? height - 1 : y - 1);
                red += getRed(c) * sharpen[0][1];
                green += getGreen(c) * sharpen[0][1];
                blue += getBlue(c) * sharpen[0][1];

                // pixel (1,-1)
                c = pixelReader.getColor((x + 1) % width, ((y - 1) % height < 0) ? height - 1 : y - 1);
                red += getRed(c) * sharpen[0][2];
                green += getGreen(c) * sharpen[0][2];
                blue += getBlue(c) * sharpen[0][2];

                // --------------------------------------------

                // pixel (-1,0)
                c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1, y % height);
                red += getRed(c) * sharpen[1][0];
                green += getGreen(c) * sharpen[1][0];
                blue += getBlue(c) * sharpen[1][0];

                // pixel (0,0)
                c = pixelReader.getColor(x % width, y % height);
                red += getRed(c) * sharpen[1][1];
                green += getGreen(c) * sharpen[1][1];
                blue += getBlue(c) * sharpen[1][1];

                // pixel (1,0)
                c = pixelReader.getColor((x + 1) % width, y % height);
                red += getRed(c) * sharpen[1][2];
                green += getGreen(c) * sharpen[1][2];
                blue += getBlue(c) * sharpen[1][2];

                // --------------------------------------------

                // pixel (-1,1)
                c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1, (y + 1) % height);
                red += getRed(c) * sharpen[2][0];
                green += getGreen(c) * sharpen[2][0];
                blue += getBlue(c) * sharpen[2][0];

                // pixel (0,1)
                c = pixelReader.getColor(x % width, (y + 1) % height);
                red += getRed(c) * sharpen[2][1];
                green += getGreen(c) * sharpen[2][1];
                blue += getBlue(c) * sharpen[2][1];

                // pixel (1,1)
                c = pixelReader.getColor((x + 1) % width, (y + 1) % height);
                red += getRed(c) * sharpen[2][2];
                green += getGreen(c) * sharpen[2][2];
                blue += getBlue(c) * sharpen[2][2];

                red = (red > 255) ? 255 : (red < 0) ? 0 : red;
                green = (green > 255) ? 255 : (green < 0) ? 0 : green;
                blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;

                Color color = Color.rgb((int) red, (int) green, (int) blue);
                pixelWriter.setColor(x, y, color);
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

        for(int i = 0; i < height; i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    PixelReader pixelReader = image.getPixelReader();
                    PixelWriter pixelWriter = writableImage.getPixelWriter();
                    int y = Integer.parseInt(Thread.currentThread().getName());
                    double sharpen[][] = {
                        { -1, -1, -1 },
                        { -1, 9, -1 },
                        { -1, -1, -1 }
                };
                for (int x = 0; x < width; x++) {
                    double red = 0;
                    double green = 0;
                    double blue = 0;
    
                    // pixel (-1,-1)
                    Color c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1,
                            ((y - 1) % height < 0) ? height - 1 : y - 1);
                    red += getRed(c) * sharpen[0][0];
                    green += getGreen(c) * sharpen[0][0];
                    blue += getBlue(c) * sharpen[0][0];
    
                    // pixel (0,-1)
                    c = pixelReader.getColor(x % width, ((y - 1) % height < 0) ? height - 1 : y - 1);
                    red += getRed(c) * sharpen[0][1];
                    green += getGreen(c) * sharpen[0][1];
                    blue += getBlue(c) * sharpen[0][1];
    
                    // pixel (1,-1)
                    c = pixelReader.getColor((x + 1) % width, ((y - 1) % height < 0) ? height - 1 : y - 1);
                    red += getRed(c) * sharpen[0][2];
                    green += getGreen(c) * sharpen[0][2];
                    blue += getBlue(c) * sharpen[0][2];
    
                    // --------------------------------------------
    
                    // pixel (-1,0)
                    c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1, y % height);
                    red += getRed(c) * sharpen[1][0];
                    green += getGreen(c) * sharpen[1][0];
                    blue += getBlue(c) * sharpen[1][0];
    
                    // pixel (0,0)
                    c = pixelReader.getColor(x % width, y % height);
                    red += getRed(c) * sharpen[1][1];
                    green += getGreen(c) * sharpen[1][1];
                    blue += getBlue(c) * sharpen[1][1];
    
                    // pixel (1,0)
                    c = pixelReader.getColor((x + 1) % width, y % height);
                    red += getRed(c) * sharpen[1][2];
                    green += getGreen(c) * sharpen[1][2];
                    blue += getBlue(c) * sharpen[1][2];
    
                    // --------------------------------------------
    
                    // pixel (-1,1)
                    c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1, (y + 1) % height);
                    red += getRed(c) * sharpen[2][0];
                    green += getGreen(c) * sharpen[2][0];
                    blue += getBlue(c) * sharpen[2][0];
    
                    // pixel (0,1)
                    c = pixelReader.getColor(x % width, (y + 1) % height);
                    red += getRed(c) * sharpen[2][1];
                    green += getGreen(c) * sharpen[2][1];
                    blue += getBlue(c) * sharpen[2][1];
    
                    // pixel (1,1)
                    c = pixelReader.getColor((x + 1) % width, (y + 1) % height);
                    red += getRed(c) * sharpen[2][2];
                    green += getGreen(c) * sharpen[2][2];
                    blue += getBlue(c) * sharpen[2][2];
    
                    red = (red > 255) ? 255 : (red < 0) ? 0 : red;
                    green = (green > 255) ? 255 : (green < 0) ? 0 : green;
                    blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;
    
                    Color color = Color.rgb((int) red, (int) green, (int) blue);
                    pixelWriter.setColor(x, y, color);
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
