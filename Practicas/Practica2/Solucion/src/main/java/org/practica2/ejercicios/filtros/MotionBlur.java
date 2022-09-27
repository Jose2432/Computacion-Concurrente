package org.practica2.ejercicios.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;

public class MotionBlur extends Filters {

    public MotionBlur(Image image) {
        super(image);
    }

    public MotionBlur(Image image, int hilos) {
    super(image, hilos);
    }

    @Override
    public Image createImage() {
        int height = Double.valueOf(image.getHeight()).intValue();
        int width = Double.valueOf(image.getWidth()).intValue();

        WritableImage writableImage = new WritableImage(width, height);

        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        double blur[][] ={
            {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1}
        };

        for (int y = 0; y < height; y++) {
            int red = 0;
            int green = 0;
            int blue = 0;

            for (int x = 0; x < width; x++) {
                Color c = pixelReader.getColor(((x - 4) % width < 0) ? width - 4 : x - 4,((y - 4) % height < 0) ? height - 4 : y - 4);
                red += getRed(c) * blur[0][0];
                green += getGreen(c) * blur[0][0];
                blue += getBlue(c) * blur[0][0];

                //Pixel2 ( - 3, - 3)
                c = pixelReader.getColor(((x - 3) % width < 0) ? width - 3 : x - 3,((y - 3) % height < 0) ? height - 3 : y - 3);
                red += getRed(c) * blur[1][1];
                green += getGreen(c) * blur[1][1];
                blue += getBlue(c) * blur[1][1];

                //Pixel3 ( - 2, - 2)
                c = pixelReader.getColor(((x - 2) % width < 0) ? width - 2 : x - 2,((y - 2) % height < 0) ? height - 2 : y - 2);
                red += getRed(c) * blur[2][2];
                green += getGreen(c) * blur[2][2];
                blue += getBlue(c) * blur[2][2];

                //Pixel4 ( - 1, - 1)
                c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1,((y - 1) % height < 0) ? height - 1 : y - 1);
                red += getRed(c) * blur[3][3];
                green += getGreen(c) * blur[3][3];
                blue += getBlue(c) * blur[3][3];

                //Pixel5 (0,0)
                c = pixelReader.getColor((x) % width,(y) % height);
                red += getRed(c) * blur[4][4];
                green += getGreen(c) * blur[4][4];
                blue += getBlue(c) * blur[4][4];

                //Pixel6 (1,1)
                c = pixelReader.getColor((x + 1) % width,(y + 1) % height);
                red += getRed(c) * blur[5][5];
                green += getGreen(c) * blur[5][5];
                blue += getBlue(c) * blur[5][5];

                //Pixel7 (2,2)
                c = pixelReader.getColor((x + 2) % width,(y + 2) % height);
                red += getRed(c) * blur[6][6];
                green += getGreen(c) * blur[6][6];
                blue += getBlue(c) * blur[6][6];

                //Pixel8 (3,3)
                c = pixelReader.getColor((x + 3) % width,(y + 3) % height);
                red += getRed(c) * blur[7][7];
                green += getGreen(c) * blur[7][7];
                blue += getBlue(c) * blur[7][7];

                //Pixel9 (4,4)
                c = pixelReader.getColor((x + 4) % width,(y + 4) % height);
                red += getRed(c) * blur[8][8];
                green += getGreen(c) * blur[8][8];
                blue += getBlue(c) * blur[8][8];

                red /= 9;
                green /= 9;
                blue /= 9;

                red = (red > 255) ? 255 : (red < 0) ? 0 : red;
                green = (green > 255) ? 255 : (green < 0) ? 0 : green;
                blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;

                Color color = Color.rgb(red, green, blue);
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
        
        for(int i = 0; i < height; ++i){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    PixelReader pixelReader = image.getPixelReader();
                    PixelWriter pixelWriter = writableImage.getPixelWriter();
            
                    double blur[][] ={
                        {1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1}
                    };
                        int red = 0;
                        int green = 0;
                        int blue = 0;
            
                        for (int x = 0; x < width; x++) {
                            Color c = pixelReader.getColor(((x - 4) % width < 0) ? width - 4 : x - 4,((Integer.parseInt(Thread.currentThread().getName()) - 4) % height < 0) ? height - 4 : Integer.parseInt(Thread.currentThread().getName()) - 4);
                            red += getRed(c) * blur[0][0];
                            green += getGreen(c) * blur[0][0];
                            blue += getBlue(c) * blur[0][0];
            
                            //Pixel2 ( - 3, - 3)
                            c = pixelReader.getColor(((x - 3) % width < 0) ? width - 3 : x - 3,((Integer.parseInt(Thread.currentThread().getName()) - 3) % height < 0) ? height - 3 : Integer.parseInt(Thread.currentThread().getName()) - 3);
                            red += getRed(c) * blur[1][1];
                            green += getGreen(c) * blur[1][1];
                            blue += getBlue(c) * blur[1][1];
            
                            //Pixel3 ( - 2, - 2)
                            c = pixelReader.getColor(((x - 2) % width < 0) ? width - 2 : x - 2,((Integer.parseInt(Thread.currentThread().getName()) - 2) % height < 0) ? height - 2 : Integer.parseInt(Thread.currentThread().getName()) - 2);
                            red += getRed(c) * blur[2][2];
                            green += getGreen(c) * blur[2][2];
                            blue += getBlue(c) * blur[2][2];
            
                            //Pixel4 ( - 1, - 1)
                            c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1,((Integer.parseInt(Thread.currentThread().getName()) - 1) % height < 0) ? height - 1 : Integer.parseInt(Thread.currentThread().getName()) - 1);
                            red += getRed(c) * blur[3][3];
                            green += getGreen(c) * blur[3][3];
                            blue += getBlue(c) * blur[3][3];
            
                            //Pixel5 (0,0)
                            c = pixelReader.getColor((x) % width,(Integer.parseInt(Thread.currentThread().getName())) % height);
                            red += getRed(c) * blur[4][4];
                            green += getGreen(c) * blur[4][4];
                            blue += getBlue(c) * blur[4][4];
            
                            //Pixel6 (1,1)
                            c = pixelReader.getColor((x + 1) % width,(Integer.parseInt(Thread.currentThread().getName()) + 1) % height);
                            red += getRed(c) * blur[5][5];
                            green += getGreen(c) * blur[5][5];
                            blue += getBlue(c) * blur[5][5];
            
                            //Pixel7 (2,2)
                            c = pixelReader.getColor((x + 2) % width,(Integer.parseInt(Thread.currentThread().getName()) + 2) % height);
                            red += getRed(c) * blur[6][6];
                            green += getGreen(c) * blur[6][6];
                            blue += getBlue(c) * blur[6][6];
            
                            //Pixel8 (3,3)
                            c = pixelReader.getColor((x + 3) % width,(Integer.parseInt(Thread.currentThread().getName()) + 3) % height);
                            red += getRed(c) * blur[7][7];
                            green += getGreen(c) * blur[7][7];
                            blue += getBlue(c) * blur[7][7];
            
                            //Pixel9 (4,4)
                            c = pixelReader.getColor((x + 4) % width,(Integer.parseInt(Thread.currentThread().getName()) + 4) % height);
                            red += getRed(c) * blur[8][8];
                            green += getGreen(c) * blur[8][8];
                            blue += getBlue(c) * blur[8][8];
            
                            red /= 9;
                            green /= 9;
                            blue /= 9;
            
                            red = (red > 255) ? 255 : (red < 0) ? 0 : red;
                            green = (green > 255) ? 255 : (green < 0) ? 0 : green;
                            blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;
            
                            Color color = Color.rgb(red, green, blue);
                            pixelWriter.setColor(x, Integer.parseInt(Thread.currentThread().getName()), color);
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
