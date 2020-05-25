package com.jiro4989.tkite;

import com.jiro4989.tkite.util.MyProperties;
import com.jiro4989.tkite.util.PreferencesKeys;
import com.jiro4989.tkite.util.PropertiesFiles;
import com.jiro4989.tkite.util.ResourceBundleWithUtf8;
import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

  public static final String TITLE = "TKool Image Tile Editor";
  public static final String VERSION = "ver 1.0.0";
  private MainController mainController;
  static ResourceBundle resources;

  @Override
  public void start(Stage primaryStage) { // {{{

    changeLanguages();

    URL location = getClass().getResource("main.fxml");
    resources =
        ResourceBundle.getBundle(
            "com.jiro4989.tkite.res.langs.main",
            Locale.getDefault(),
            ResourceBundleWithUtf8.UTF8_ENCODING_CONTROL);
    FXMLLoader loader = new FXMLLoader(location, resources);

    try {

      VBox root = (VBox) loader.load();
      mainController = (MainController) loader.getController();

      Scene scene = new Scene(root, 950, 550);
      scene.getStylesheets().add(getClass().getResource("res/css/basic.css").toExternalForm());

      primaryStage.setScene(scene);
      primaryStage
          .getIcons()
          .add(
              new Image(
                  getClass().getResource("res/img/com.jiro4989.tkite_icon.png").toExternalForm()));
      primaryStage.setTitle(TITLE);
      primaryStage.setMinWidth(480);
      primaryStage.setMinHeight(270);
      primaryStage.setOnCloseRequest(
          e -> {
            mainController.closeRequest();
          });

      MyProperties mainMp = new MyProperties("properties/main.xml");
      if (mainMp.load()) mainMp.customStage(primaryStage);

      primaryStage.show();
      mainController.setDividerPosition();

    } catch (IOException e) {
      e.printStackTrace();
    }
  } // }}}

  public static void main(String... args) { // {{{
    launch(args);
  } // }}}

  private void changeLanguages() { // {{{

    MyProperties preferences = new MyProperties(PropertiesFiles.PREFERENCES.FILE);
    if (preferences.load()) {

      String ja = Locale.JAPAN.getLanguage();
      String langs = preferences.getProperty(PreferencesKeys.LANGS.KEY).orElse(ja);
      if (!langs.equals(ja)) {

        Locale.setDefault(Locale.ENGLISH);
      }
    }
  } // }}}
}
