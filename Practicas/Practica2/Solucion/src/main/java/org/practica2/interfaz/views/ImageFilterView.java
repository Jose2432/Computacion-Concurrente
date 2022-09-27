package org.practica2.interfaz.views;

import java.util.Arrays;
import java.util.List;

import org.practica2.ejercicios.filtros.Component;
import org.practica2.ejercicios.filtros.Filter;
import org.practica2.interfaz.model.ImageFilterModel;
import org.practica2.interfaz.views.menus.MenuView;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ImageFilterView extends VBox {

    public final String IMAGE_PLACEHOLDER = getClass()
            .getClassLoader()
            .getResource("icons/placeholder.png")
            .toString();

    private Image defaultImage = new Image(IMAGE_PLACEHOLDER);
    private StackPane preImage = new StackPane();
    private StackPane postImage = new StackPane();
    private ImageView preImageView = new ImageView(defaultImage);
    private ImageView postImageView = new ImageView(defaultImage);
    private SplitPane splitPane = new SplitPane();

    private Image image;
    private Image filteredImage;

    private ColorPicker colorPicker;
    private ChoiceBox<String> choiceBox;

    public ImageFilterView(double width) {
        createView();
        setImageView(width);
    }

    public Image getFilteredImage() {
        return filteredImage;
    }

    public void setImageView(double width) {
        List<ImageView> imageViews = List.of(preImageView, postImageView);
        for (ImageView imageView : imageViews) {
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setFitWidth(width);
        }
    }

    private void createView() {
        MenuView menuView = new MenuView(this);
        MenuBar menuBar = new MenuBar();
        menuView.getMenus().forEach(menu -> menuBar.getMenus().add(menu));

        Label labelPreImage = createLabel("Imágen original");
        StackPane.setAlignment(labelPreImage, Pos.TOP_LEFT);
        StackPane.setAlignment(preImageView, Pos.CENTER);
        preImage.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.50));
        preImage.getChildren().addAll(labelPreImage, preImageView);

        Label labelPostImage = createLabel("Imágen con el filtro");
        StackPane.setAlignment(labelPostImage, Pos.TOP_LEFT);
        StackPane.setAlignment(postImageView, Pos.CENTER);
        postImage.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.50));
        postImage.getChildren().addAll(labelPostImage, postImageView);

        splitPane.getItems().addAll(preImage, postImage);

        this.getChildren().addAll(menuBar, splitPane);
        this.getChildren().forEach(child -> VBox.setVgrow(child, Priority.ALWAYS));
    }

    private Label createLabel(String labelText) {
        Label label = new Label(labelText);

        label.setTranslateX(14);
        label.setTranslateY(14);
        label.setFont(new Font("DejaVu Sans Mono Bold", 18.0));

        return label;
    }

    public void setFilteredImage(String path) {
        postImageView.setImage(new Image(path));
    }

    public void createImage(String filepath) {
        this.image = new Image(filepath, true);
        preImageView.setImage(image);
        postImageView.setImage(defaultImage);
    }

    public ColorPicker createColorPicker() {
       ColorPicker colorPicker= new ColorPicker();

       colorPicker.getStyleClass().add("button");
       colorPicker.setTranslateX(-14);
       colorPicker.setTranslateY(-14);
       StackPane.setAlignment(colorPicker, Pos.BOTTOM_RIGHT);

       return colorPicker;
    }

    public void closeBoxes() {
        for (var box: Arrays.asList(choiceBox, colorPicker)) {
            if (box != null) {
                postImage.getChildren().removeAll(box);
            }
        }
    }

    private ChoiceBox<String> createChoiceBox(Component... elements) {
        ChoiceBox<String> choiceBox = new ChoiceBox<String>();
        String[] elems = Arrays.stream(elements)
            .map(Component::getName)
            .toArray(String[]::new);

        choiceBox.getItems().addAll(elems);
        choiceBox.setTranslateX(-14);
        choiceBox.setTranslateY(-14);
        StackPane.setAlignment(choiceBox, Pos.BOTTOM_RIGHT);

        return choiceBox;
    }

    public void createFilteredImage(Filter filter) {
        if (image != null) {
            ImageFilterModel imageFilterModel = new ImageFilterModel(image, filter);
            this.filteredImage = imageFilterModel.getFilteredImage();
            closeBoxes();

            if (filter == Filter.COMPONENTES_RGB) {
                colorPicker = createColorPicker();
                colorPicker.setOnAction(e -> {
                    imageFilterModel.setColor(colorPicker.getValue());
                    this.filteredImage = imageFilterModel.getFilteredImage();
                    postImageView.setImage(this.filteredImage);
                });
                postImage.getChildren().add(colorPicker);
            } else if (filter == Filter.SINGLE_COLOR) {
                choiceBox = createChoiceBox(Component.values());
                choiceBox.setOnAction(e -> {
                    String component = choiceBox.getSelectionModel().getSelectedItem();
                    imageFilterModel.setComponent(Component.getComponent(component));
                    this.filteredImage = imageFilterModel.getFilteredImage();
                    postImageView.setImage(this.filteredImage);
                });
                postImage.getChildren().add(choiceBox);
            }

            postImageView.setImage(filteredImage);
        }
    }
}
