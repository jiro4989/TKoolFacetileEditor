package app.image;

import java.util.List;
import javafx.scene.image.*;

public class ReverseStrategy implements ControlOutputPaneStrategy {

  /**
   * 選択したペインの画像を左右反転する。
   *
   * @param list 選択状態にあるStackViewPaneのリスト
   */
  @Override
  public void invoke(List<StackImageView> list) {

    list.stream()
        .forEach(
            siv -> {
              MyImage myimg = new MyImage(siv.getImage());
              Image image = myimg.toReversedImage().image;
              siv.setImage(image);
            });

    list.clear();
    OutputImagePane.clearSelectedStackImageView();
  }
}
