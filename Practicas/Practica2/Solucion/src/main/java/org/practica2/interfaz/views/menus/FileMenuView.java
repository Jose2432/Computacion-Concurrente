package org.practica2.interfaz.views.menus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.practica2.interfaz.views.ImageFilterView;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

public class FileMenuView extends Menu {

    private FileChooser fileChooser = new FileChooser();

    private ImageFilterView imageFilterView;

    private MenuItem openFile = new MenuItem("_Abrir...");
    private Menu openRecentFile = new Menu("Abrir _reciente");

    private SeparatorMenuItem separator1 = new SeparatorMenuItem();

    private MenuItem closeFile = new MenuItem("_Cerrar imágen");
    private MenuItem saveFile = new MenuItem("_Guardar");
    private MenuItem saveAsFile = new MenuItem("G_uardar como...");

    private SeparatorMenuItem separator2 = new SeparatorMenuItem();

    private MenuItem quitFile = new MenuItem("_Salir");

    public FileMenuView(String string, ImageFilterView imageFilterView) {
        super(string);
        this.imageFilterView = imageFilterView;
    }

    public void createMenus() {
        this.setMnemonicParsing(true);
        this.getItems().addAll(
             openFile,
             openRecentFile,
             separator1,
             closeFile,
             saveFile,
             saveAsFile,
             separator2,
             quitFile
        );
        createMenuItems();
    }

    private void createMenuItems() {
        openFile.setAccelerator(KeyCombination.keyCombination("CTRL+O"));
        openFile.setOnAction(actionEvent -> {
            imageFilterView.closeBoxes();
            fileChooser.setTitle("Abrir archivo");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png")
            );
            File file = fileChooser.showOpenDialog(getParentPopup());
            if (file != null) {
                imageFilterView.createImage(file.toURI().toString());
            }
        });

        saveFile.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
        saveFile.setOnAction(actionEvent -> {
            File file = new File("~/", "Imagen.png");
            if (file != null) {
                BufferedImage image = SwingFXUtils.fromFXImage(imageFilterView.getFilteredImage(), null);
                try {
                    ImageIO.write(image, "png", file);
                } catch (IOException e) {
                    System.out.println("Error al guardar el archivo");
                }
            }

        });

        saveAsFile.setAccelerator(KeyCombination.keyCombination("CTRL+SHIFT+S"));
        saveAsFile.setOnAction(actionEvent -> {
            imageFilterView.closeBoxes();
            fileChooser.setTitle("Guardar archivo");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
            );
            File file = fileChooser.showSaveDialog(getParentPopup());
            if (file != null) {
                BufferedImage image = SwingFXUtils.fromFXImage(imageFilterView.getFilteredImage(), null);
                try {
                    ImageIO.write(image, "png", file);
                } catch (IOException e) {
                    System.out.println("Error al guardar el archivo");
                }
            }
        });

        closeFile.setAccelerator(KeyCombination.keyCombination("CTRL+W"));
        closeFile.setOnAction(actionEvent -> {
            imageFilterView.closeBoxes();
            imageFilterView.createImage(imageFilterView.IMAGE_PLACEHOLDER);
        });

        quitFile.setAccelerator(KeyCombination.keyCombination("CTRL+Q"));
        quitFile.setOnAction(actionEvent -> System.exit(0));
    }
}
