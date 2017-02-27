package app;

import java.io.IOException;
import java.io.File;
import java.util.*;
import java.util.stream.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

class OutputImagePane {//{{{
  private final GridPane outputImageGridPane;
  private List<StackImageView> stackImageViewList;

  OutputImagePane(GridPane aGridPane) {
    outputImageGridPane = aGridPane;
    stackImageViewList = new ArrayList<>(8);
  }

  /**
   * Propertiesの設定によってレイ・アウトを変更する。
   */
  void setGridCells() {//{{{
    Properties prop = MainController.prop;
    int row    = Integer.parseInt(prop.getProperty("row"));
    int column = Integer.parseInt(prop.getProperty("column"));
    int size   = Integer.parseInt(prop.getProperty("size"));
    int count  = row * column;

    double gridWidth  = (double) (size * column);
    double gridHeight = (double) (size * row);
    outputImageGridPane.setPrefSize(gridWidth, gridHeight);

    IntStream.range(0, count)
      .forEach(i -> {
        final StackImageView siv = new StackImageView(i+1, size);
        stackImageViewList.add(siv);
        final int c = i % column;
        final int r = i / column;
        outputImageGridPane.add(siv, c, r);
      });

  }//}}}
  /**
   * 画像をImageViewに貼り付ける。
   *
   * @param imageFile 画像
   */
  void setImage(File imageFile) {//{{{
    Image image = new Image("file:" + imageFile.getAbsolutePath());
    PixelReader pixel = image.getPixelReader();

    Properties prop = MainController.prop;
    int row    = Integer.parseInt(prop.getProperty("row"));
    int column = Integer.parseInt(prop.getProperty("column"));
    int size   = Integer.parseInt(prop.getProperty("size"));
    int count  = row * column;

    IntStream.range(0, count)
      .forEach(i -> {
        int x = i % column * size;
        int y = i / column * size;
        WritableImage trimmingImage = new WritableImage(pixel, x, y, size, size);
        stackImageViewList.get(i).setImage(trimmingImage);
      });
  }//}}}
  /**
   * 選択した２つのImageViewの画像を交換する。
   */
  void exchangeImage() {//{{{
    Properties prop = MainController.prop;
    int size = Integer.parseInt(prop.getProperty("size"));
    Image image1 = stackImageViewList.get(0).getImage();
    Image image2 = stackImageViewList.get(1).getImage();
    Image tmpImage = new WritableImage(size, size);
  }//}}}
}//}}}
/**
 * マウスクリック可能なImageViewを再現するためのコンポーネントクラス。
 * StackPaneの中に３つのレイヤーが存在し、それぞれ下から番号ラベル、ImageView、
 * 透明なボタンという順序で構成される。透明なボタンをクリックすると、透明なボタ
 * ンの色が微妙に変化し、選択状態を表現する。
 */
class StackImageView extends StackPane {//{{{
  private final Label label;
  private final ImageView imageView;
  private final Button button;
  private boolean isSelected = false;

  StackImageView(int index, double size) {//{{{
    label     = new Label("" + index);
    imageView = new ImageView();
    button    = new Button();

    label.setAlignment(Pos.CENTER);

    button.setOpacity(0.0);
    button.setStyle("-fx-background-color: blue");
    button.setOnAction(e -> buttonOnAction());

    this      .setPrefSize(size, size);
    label     .setPrefSize(size, size);
    imageView .setFitWidth(size);
    imageView .setFitHeight(size);
    button    .setPrefSize(size, size);

    this.getChildren().add(label);
    this.getChildren().add(imageView);
    this.getChildren().add(button);
  }//}}}

  private List<Integer> buttonIndexList = new ArrayList<>();
  private void buttonOnAction() {//{{{
    isSelected = !isSelected;
    button.setOpacity(isSelected ? 0.25 : 0.0);

    int number = Integer.parseInt(label.getText());
    buttonIndexList.add(number);
    for (int i : buttonIndexList) {
      System.out.print("list: " + i + " ");
    }
    System.out.println("");
    if (2 <= buttonIndexList.size()) {
      reverse();
      buttonIndexList.clear();
    }
  }//}}}
  private void reverse() {//{{{
    System.out.println("reverse.");
  }//}}}
  Image getImage() {//{{{
    return imageView.getImage();
  }//}}}
  void setImage(Image image) {//{{{
    imageView.setImage(image);
  }//}}}
}//}}}
