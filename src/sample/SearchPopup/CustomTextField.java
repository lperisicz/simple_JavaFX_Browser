package sample.SearchPopup;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.History.Data;
import sample.History.LoggerAbstractions.IFileReader;
import sample.History.LoggerImplementation.TxtFileReader;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class CustomTextField extends TextField{

    private SortedSet<String> suggestedList;

    private ContextMenu contextMenu;
    private IFileReader fileReader;
    private int maxNumberOfResults;
    private String source;

    public CustomTextField(int maxNumberOfResults, String sourceFile){
        super();
        this.contextMenu = new ContextMenu();
        this.maxNumberOfResults = maxNumberOfResults;
        this.fileReader = new TxtFileReader();
        this.source = sourceFile;
        this.suggestedList = new TreeSet<>((o1, o2) -> {
            if(o1.length() < o2.length()) return -1;
            else if(o1.length() == o2.length()) return 0;
            return 1;
        });
        textProperty().addListener((observable, oldValue, newValue) -> {
            if(getText().length() == 0){
                contextMenu.hide();
            }else{
                suggestedList = processResults(getText());
                if(suggestedList.size() > 0) {
                    populatePopup(suggestedList);
                    if (!contextMenu.isShowing()){
                        contextMenu.show(CustomTextField.this, Side.BOTTOM, 0, 0);
                    }
                }else{
                    contextMenu.hide();
                }
            }
        });
        focusedProperty().addListener((observable, oldValue, newValue) -> contextMenu.hide());
    }

    private SortedSet<String> processResults(String filter){
        SortedSet<String> results = new TreeSet<>();
        List<Data> data = fileReader.read(source);
        String page;
        for (Data item: data) {
            page = item.getPageVisited();
            if(page.contains(filter)){
                results.add(page);
            }
        }
        return results;
    }

    private void populatePopup(SortedSet<String> processedResults){
        List<CustomMenuItem> menuItems = new LinkedList<>();
        contextMenu.getItems().clear();
        int size = Math.min(maxNumberOfResults,processedResults.size());
        int br = 0;
        for (String item:processedResults) {
            br++;
            Label label = new Label(item);
            CustomMenuItem customMenuItem = new CustomMenuItem(label,true);
            customMenuItem.setOnAction(event -> {
                setText(item);
                contextMenu.hide();
            });
            contextMenu.getItems().add(customMenuItem);
            if(br == size) break;
        }
    }
}
