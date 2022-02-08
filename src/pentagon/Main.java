package pentagon;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pentagon.levels.Space;

public class Main extends Application {
  private Space scene;
  
  public void start(Stage primaryStage) {
    Group root = new Group();
    this.scene = new Space((Parent)root);
    primaryStage.setScene((Scene)this.scene);
    primaryStage.show();
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}
