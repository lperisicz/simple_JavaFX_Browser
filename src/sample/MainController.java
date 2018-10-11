package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class MainController {

    @FXML
    private TabPane tabPane;

    public void initialize() {
        addTab();
        addNewTabAddingTab();
    }

    private void addTab() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Tab/Window.fxml"));
        try {
            Tab newTab = fxmlLoader.load();
            if (tabPane.getTabs().size() == 0) {
                tabPane.getTabs().add(newTab);
            } else {
                tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addNewTabAddingTab() {
        Tab newTab = new Tab();
        newTab.setClosable(false);
        Label lbl = new Label("\u2795");
        newTab.setGraphic(lbl);
        tabPane.getTabs().add(newTab);
        newTab.selectedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue) {
                        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
                    }
                });
        lbl.setOnMouseClicked(mouseEvent -> {
            addTab();
            tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
        });
    }

}