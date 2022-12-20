package org.practica2.interfaz.views.menus;

import java.util.List;

import org.practica2.interfaz.views.ImageFilterView;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuView extends Menu {

    private FileMenuView fileMenu;
    private EditMenuView editMenu;
    private Menu helpMenu = new Menu("A_yuda");

    private ImageFilterView imageFilterView;

    public MenuView(ImageFilterView imageFilterView) {
        this.imageFilterView = imageFilterView;
        createMenus();
    }

    private void createMenus() {
        fileMenu = new FileMenuView("_Archivo", imageFilterView);
        fileMenu.createMenus();

        editMenu = new EditMenuView("_Editar", imageFilterView);
        editMenu.createMenus();

        helpMenu.setMnemonicParsing(true);
        MenuItem aboutApp = new MenuItem("Acerca de Filtros de Imágenes");
        helpMenu.getItems().add(aboutApp);
    }

    public List<Menu> getMenus() {
        return List.of(fileMenu, editMenu, helpMenu);
    }
}
