package org.practica2.interfaz.views.menus;

import org.practica2.ejercicios.filtros.Filter;
import org.practica2.interfaz.views.ImageFilterView;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class EditMenuView extends Menu {

    private ImageFilterView imageFilterView;

    private MenuItem closeFilter = new MenuItem("_Cerrar filtro");
    private SeparatorMenuItem separator0 = new SeparatorMenuItem();
    private Menu filtersMenu = new Menu("_Filtros");

    private MenuItem promedio = new MenuItem("Promedio");
    private MenuItem correctud = new MenuItem("Correctud");
    private MenuItem correctud2 = new MenuItem("Correctud 2");
    private MenuItem singleColor = new MenuItem("Single Color");

    private SeparatorMenuItem separator1 = new SeparatorMenuItem();

    private MenuItem blur = new MenuItem("Blur");
    private MenuItem blur2 = new MenuItem("Blur 2");
    private MenuItem motionBlur = new MenuItem("Motion Blur");

    private SeparatorMenuItem separator2 = new SeparatorMenuItem();

    private MenuItem sharpen = new MenuItem("Sharpen");
    private MenuItem componentesRGB = new MenuItem("Componentes RGB");
    private MenuItem altoContraste = new MenuItem("Alto Contraste");

    public EditMenuView(String string, ImageFilterView imageFilterView) {
        super(string);
        this.imageFilterView = imageFilterView;
    }

    public void createMenus() {
        this.setMnemonicParsing(true);
        this.getItems().addAll(closeFilter, separator0, filtersMenu);

        filtersMenu.getItems().addAll(
            promedio,
            correctud,
            correctud2,
            singleColor,
            separator1,
            blur,
            blur2,
            motionBlur,
            separator2,
            sharpen,
            componentesRGB,
            altoContraste
        );

        createMenuItems();
    }

    private void createMenuItems() {
        closeFilter.setOnAction(e -> {
            imageFilterView.closeBoxes();
            imageFilterView.setFilteredImage(imageFilterView.IMAGE_PLACEHOLDER);
        });

        filtersMenu.getItems().forEach(filter ->
            filter.setOnAction(actionEvent -> {
                String menuItemText = filter.getText().toUpperCase().replaceAll("\\s+", "_");
                imageFilterView.createFilteredImage(Filter.valueOf(menuItemText));
            })
        );
    }
}
