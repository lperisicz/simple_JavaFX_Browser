package sample.Tab;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sample.History.Data;
import sample.History.History;
import sample.History.LoggerAbstractions.AbstractLogger;
import sample.History.LoggerImplementation.TxtFileLogger;
import sample.History.LoggerImplementation.TxtFileReader;
import sample.History.LoggerImplementation.TxtFileWriter;
import sample.SearchPopup.CustomTextField;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    private WebView browser;
    @FXML
    private Tab tabView;
    @FXML
    private Slider sliderZoom;
    @FXML
    private ToolBar toolbar;
    @FXML
    private Button backButton;
    @FXML
    private Button forwardButton;
    @FXML
    private ProgressBar progressBar;

    private WebEngine engine;
    private History history;
    private String historyFilePath = "POVIJEST.txt";
    private AbstractLogger txtFileLogger;
    private CustomTextField searchField = new CustomTextField(5, historyFilePath);

    public void initialize() {
        engine = browser.getEngine();
        engine.setUserAgent("Chrome/41.0.2228.0");
        engine.locationProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            history.addToHistory(newValue);
            txtFileLogger.write(new Data(newValue),historyFilePath);
            setFavicon(newValue);
        });

        history = new History();
        txtFileLogger = new TxtFileLogger(historyFilePath, new TxtFileReader(), new TxtFileWriter());
        toolbar.getItems().add(2, searchField);
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchForPage();
            }
        });
        searchField.setMinWidth(500);

        tabView.textProperty().bind(engine.titleProperty());
        progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
        //backButton.disableProperty().bind(history.isFirst());
        //forwardButton.visibleProperty().bind(history.isLast());
        goToHomePage();
    }

    public void searchForPage() {
        String url = searchField.getText();
        if (url.length() != 0) {
            if (!isWebPage(url)) {
                url = "www.google.com/search?q=" + url;
            }
            StringBuilder sb = new StringBuilder();
            if (url.contains("://")) {
                sb.append(url);
            } else {
                sb.append("https://").append(url);
            }
            engine.load(sb.toString());
        }
    }

    public void goToHomePage() {
        engine.load("http://www.google.com");
        searchField.setText("http://www.google.com");

    }

    public void previousPage() {
        Data current = history.getCurrent();
        history.previous();
        Data newCurrent = history.getCurrent();
        if (current != null && newCurrent != null) {
            if (current != newCurrent) {
                System.out.println(current.getPageVisited() + "------>    " + newCurrent.getPageVisited());
                engine.load(newCurrent.getPageVisited());
            }else
            System.out.println(current.getPageVisited() + " <---xxxxx---> " + newCurrent.getPageVisited());
        }
    }

    public void nextPage() {
        Data current = history.getCurrent();
        history.next();
        Data newCurrent = history.getCurrent();
        if (current != null && newCurrent != null) {
            if (current != newCurrent) {
                System.out.println(current.getPageVisited() + "------>  " + newCurrent.getPageVisited());
                engine.load(newCurrent.getPageVisited());
            }
            else System.out.println(current.getPageVisited() + " <----xxxx---> " + newCurrent.getPageVisited());
        }

    }

    public void zoomView() {
        browser.setZoom(sliderZoom.getValue());
    }

    private void setFavicon(String location){
        try{
            String faviconUrl = String.format("http://www.google.com/s2/favicons?domain_url=%s", URLEncoder.encode(location, "UTF-8"));
            Image favicon = new Image(faviconUrl, true);
            ImageView img = new ImageView(favicon);
            tabView.setGraphic(img);
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private boolean isWebPage(String url) {
        String regex = "^(https?|ftp|file)://www.[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher("https://" + url);
            Matcher matcher2 = patt.matcher(url);
            return matcher.matches() || matcher2.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void printHistory() {
        for (Data data : txtFileLogger.read(historyFilePath)) {
            System.out.println(data.toString());
        }
    }
}