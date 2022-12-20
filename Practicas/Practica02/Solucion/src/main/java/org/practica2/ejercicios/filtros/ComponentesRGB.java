package org.practica2.ejercicios.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;
public class ComponentesRGB extends Filters {

    private Color color;

    public ComponentesRGB(Image image, int hilos) {
        super(image, hilos);
    }

    public ComponentesRGB(Image image, int hilos, Color color) {
        this(image, hilos);
        this.color = color;
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
                Color colorImage = pixelReader.getColor(x, y);

                int red = getRed(colorImage) + getRed(color);
                int green = getGreen(colorImage) + getGreen(color);
                int blue = getBlue(colorImage) + getBlue(color);

                red = (red > 255) ? 255 : (red < 0) ? 0 : red;
                green = (green > 255) ? 255 : (green < 0) ? 0 : green;
                blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;

                Color newColor = Color.rgb(red, green, blue);
                pixelWriter.setColor(x, y, newColor);
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
                        Color colorImage = pixelReader.getColor(x,Integer.parseInt(Thread.currentThread().getName()));
            
                        int red = getRed(colorImage) + getRed(color);
                        int green = getGreen(colorImage) + getGreen(color);
                        int blue = getBlue(colorImage) + getBlue(color);
            
                        red = (red > 255) ? 255 : (red < 0) ? 0 : red;
                        green = (green > 255) ? 255 : (green < 0) ? 0 : green;
                        blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;
            
                        Color newColor = Color.rgb(red, green, blue);
                        pixelWriter.setColor(x,Integer.parseInt(Thread.currentThread().getName()), newColor);
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

    public void currentPixelLine(WritableImage image,int y){
        int width = Double.valueOf(image.getWidth()).intValue();
    
        WritableImage writableImage = image;

        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int x = 0; x < width; x++) {
            Color colorImage = pixelReader.getColor(x, y);

            int red = getRed(colorImage) + getRed(color);
            int green = getGreen(colorImage) + getGreen(color);
            int blue = getBlue(colorImage) + getBlue(color);

            red = (red > 255) ? 255 : (red < 0) ? 0 : red;
            green = (green > 255) ? 255 : (green < 0) ? 0 : green;
            blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;

            Color newColor = Color.rgb(red, green, blue);
            pixelWriter.setColor(x, y, newColor);
        }
        
    }
}
