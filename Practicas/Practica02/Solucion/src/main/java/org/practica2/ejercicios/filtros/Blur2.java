package org.practica2.ejercicios.filtros;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;

public class Blur2 extends Filters {

    public Blur2(Image image) {
        super(image);
    }

    public Blur2(Image image, int hilos) {
    super(image, hilos);
    }

    @Override
    public Image createImage() {
        int height = Double.valueOf(image.getHeight()).intValue();
        int width = Double.valueOf(image.getWidth()).intValue();

        WritableImage writableImage = new WritableImage(width, height);

        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        double blur[][] = {
            { 0, 0, 1, 0, 0 },
            { 0, 1, 1, 1, 0 },
            { 1, 1, 1, 1, 1 },
            { 0, 1, 1, 1, 0 },
            { 0, 0, 1, 0, 0 }
        };

        for (int y = 0; y < height; y++) {
            int red = 0;
            int green = 0;
            int blue = 0;

            for (int x = 0; x < width; x++) {
                // pixel (0,-2)
                Color c = pixelReader.getColor(x % width, ((y - 2) % height < 0) ? height - 2 : y - 2);
                red += getRed(c) * blur[0][2];
                green += getGreen(c) * blur[0][2];
                blue += getBlue(c) * blur[0][2];

                // pixel (-1,-1)
                c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1,
                        ((y - 1) % height < 0) ? height - 1 : y - 1);
                red += getRed(c) * blur[1][1];
                green += getGreen(c) * blur[1][1];
                blue += getBlue(c) * blur[1][1];

                // pixel (0,-1)
                c = pixelReader.getColor(x % width, ((y - 1) % height < 0) ? height - 1 : y - 1);
                red += getRed(c) * blur[1][2];
                green += getGreen(c) * blur[1][2];
                blue += getBlue(c) * blur[1][2];

                // pixel (1,-1)
                c = pixelReader.getColor((x + 1) % width, ((y - 1) % height < 0) ? height - 1 : y - 1);
                red += getRed(c) * blur[1][3];
                green += getGreen(c) * blur[1][3];
                blue += getBlue(c) * blur[1][3];

                // pixel (-2,0)
                c = pixelReader.getColor(((x - 2) % width < 0) ? width - 2 : x - 2, y % height);
                red += getRed(c) * blur[2][0];
                green += getGreen(c) * blur[2][0];
                blue += getBlue(c) * blur[2][0];

                // pixel (-1,0)
                c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1, y % height);
                red += getRed(c) * blur[2][1];
                green += getGreen(c) * blur[2][1];
                blue += getBlue(c) * blur[2][1];

                // pixel (0,0)
                c = pixelReader.getColor(x % width, y % height);
                red += getRed(c) * blur[2][2];
                green += getGreen(c) * blur[2][2];
                blue += getBlue(c) * blur[2][2];

                // pixel (1,0)
                c = pixelReader.getColor((x + 1) % width, y % height);
                red += getRed(c) * blur[2][3];
                green += getGreen(c) * blur[2][3];
                blue += getBlue(c) * blur[2][3];

                // pixel (2,0)
                c = pixelReader.getColor((x + 2) % width, y % height);
                red += getRed(c) * blur[2][4];
                green += getGreen(c) * blur[2][4];
                blue += getBlue(c) * blur[2][4];

                // pixel (-1,1)
                c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1, (y + 1) % height);
                red += getRed(c) * blur[3][1];
                green += getGreen(c) * blur[3][1];
                blue += getBlue(c) * blur[3][1];

                // pixel (0,1)
                c = pixelReader.getColor(x % width, (y + 1) % height);
                red += getRed(c) * blur[3][2];
                green += getGreen(c) * blur[3][2];
                blue += getBlue(c) * blur[3][2];

                // pixel (1,1)
                c = pixelReader.getColor((x + 1) % width, (y + 1) % height);
                red += getRed(c) * blur[3][3];
                green += getGreen(c) * blur[3][3];
                blue += getBlue(c) * blur[3][3];

                // pixel (0,2)
                c = pixelReader.getColor(x % width, (y + 2) % height);
                red += getRed(c) * blur[4][2];
                green += getGreen(c) * blur[4][2];
                blue += getBlue(c) * blur[4][2];

                red /= 13;
                green /= 13;
                blue /= 13;

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

        for (int i = 0; i < height; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    PixelReader pixelReader = image.getPixelReader();
                    PixelWriter pixelWriter = writableImage.getPixelWriter();

                    double blur[][] = { { 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
                            { 0, 0, 1, 0, 0 } };
                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    int y = Integer.parseInt(Thread.currentThread().getName());

                    for (int x = 0; x < width; x++) {
                        // pixel (0,-2)
                        Color c = pixelReader.getColor(x % width, ((y - 2) % height < 0) ? height - 2 : y - 2);
                        red += getRed(c) * blur[0][2];
                        green += getGreen(c) * blur[0][2];
                        blue += getBlue(c) * blur[0][2];

                        // pixel (-1,-1)
                        c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1,
                                ((y - 1) % height < 0) ? height - 1 : y - 1);
                        red += getRed(c) * blur[1][1];
                        green += getGreen(c) * blur[1][1];
                        blue += getBlue(c) * blur[1][1];

                        // pixel (0,-1)
                        c = pixelReader.getColor(x % width, ((y - 1) % height < 0) ? height - 1 : y - 1);
                        red += getRed(c) * blur[1][2];
                        green += getGreen(c) * blur[1][2];
                        blue += getBlue(c) * blur[1][2];

                        // pixel (1,-1)
                        c = pixelReader.getColor((x + 1) % width, ((y - 1) % height < 0) ? height - 1 : y - 1);
                        red += getRed(c) * blur[1][3];
                        green += getGreen(c) * blur[1][3];
                        blue += getBlue(c) * blur[1][3];

                        // pixel (-2,0)
                        c = pixelReader.getColor(((x - 2) % width < 0) ? width - 2 : x - 2, y % height);
                        red += getRed(c) * blur[2][0];
                        green += getGreen(c) * blur[2][0];
                        blue += getBlue(c) * blur[2][0];

                        // pixel (-1,0)
                        c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1, y % height);
                        red += getRed(c) * blur[2][1];
                        green += getGreen(c) * blur[2][1];
                        blue += getBlue(c) * blur[2][1];

                        // pixel (0,0)
                        c = pixelReader.getColor(x % width, y % height);
                        red += getRed(c) * blur[2][2];
                        green += getGreen(c) * blur[2][2];
                        blue += getBlue(c) * blur[2][2];

                        // pixel (1,0)
                        c = pixelReader.getColor((x + 1) % width, y % height);
                        red += getRed(c) * blur[2][3];
                        green += getGreen(c) * blur[2][3];
                        blue += getBlue(c) * blur[2][3];

                        // pixel (2,0)
                        c = pixelReader.getColor((x + 2) % width, y % height);
                        red += getRed(c) * blur[2][4];
                        green += getGreen(c) * blur[2][4];
                        blue += getBlue(c) * blur[2][4];

                        // pixel (-1,1)
                        c = pixelReader.getColor(((x - 1) % width < 0) ? width - 1 : x - 1, (y + 1) % height);
                        red += getRed(c) * blur[3][1];
                        green += getGreen(c) * blur[3][1];
                        blue += getBlue(c) * blur[3][1];

                        // pixel (0,1)
                        c = pixelReader.getColor(x % width, (y + 1) % height);
                        red += getRed(c) * blur[3][2];
                        green += getGreen(c) * blur[3][2];
                        blue += getBlue(c) * blur[3][2];

                        // pixel (1,1)
                        c = pixelReader.getColor((x + 1) % width, (y + 1) % height);
                        red += getRed(c) * blur[3][3];
                        green += getGreen(c) * blur[3][3];
                        blue += getBlue(c) * blur[3][3];

                        // pixel (0,2)
                        c = pixelReader.getColor(x % width, (y + 2) % height);
                        red += getRed(c) * blur[4][2];
                        green += getGreen(c) * blur[4][2];
                        blue += getBlue(c) * blur[4][2];

                        red /= 13;
                        green /= 13;
                        blue /= 13;

                        red = (red > 255) ? 255 : (red < 0) ? 0 : red;
                        green = (green > 255) ? 255 : (green < 0) ? 0 : green;
                        blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;

                        Color color = Color.rgb(red, green, blue);
                        pixelWriter.setColor(x, y, color);
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

        return writableImage;
    }
}
