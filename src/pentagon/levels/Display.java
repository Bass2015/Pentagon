package pentagon.levels;

import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pentagon.elements.Bonus;
import pentagon.game.PentagonConstants;

public class Display extends Parent implements PentagonConstants {
  private static final double Y_DISTANCE = 25.0D;
  
  private static final int START_Y = 3;
  
  private static final int X = 0;
  
  private static final int Y = 1;
  
  private static final double[][] COUNTERS_POS = new double[][] { { 1200.0D, 1230.0D, 1260.0D, 24.0D }, { 85.0D, 55.0D, 25.0D, 660.0D } };
  
  private static final double[][][] BONUS_POS = new double[][][] { { { 1273.0D, 646.0D }, { 1273.0D, 674.0D } }, { { 26.0D, 56.0D }, { 26.0D, 85.0D } } };
  
  private final ArrayList<Counter>[] playersCounters;
  
  private final Image displayImage;
  
  private final ImageView displayView;
  
  private final int[][] counters = new int[][] { { 0, 0, 0 }, { 0, 0, 0 } };
  
  public Display() {
    this.displayImage = new Image("pentagon/images/display.png");
    this.displayView = new ImageView(this.displayImage);
    getChildren().add(this.displayView);
    this.playersCounters = (ArrayList<Counter>[])new ArrayList[2];
    this.playersCounters[0] = new ArrayList<>();
    this.playersCounters[1] = new ArrayList<>();
    for (int player = 0; player < this.counters.length; player++) {
      for (int type = 0; type < (this.counters[player]).length; type++) {
        for (int k = 0; k < 3; k++)
          addCounter(type, player); 
      } 
    } 
  }
  
  public final void addCounter(int type, int player) {
    Counter cntr = new Counter(type);
    switch (type) {
      case 0:
        cntr.setLayoutX(COUNTERS_POS[player][0]);
        cntr.setLayoutY(COUNTERS_POS[player][3] + this.counters[player][0] * ((player == 0) ? 25.0D : -25.0D));
        this.counters[player][0] = this.counters[player][0] + 1;
        break;
      case 1:
        cntr.setLayoutX(COUNTERS_POS[player][1]);
        cntr.setLayoutY(COUNTERS_POS[player][3] + this.counters[player][1] * ((player == 0) ? 25.0D : -25.0D));
        this.counters[player][1] = this.counters[player][1] + 1;
        break;
      case 2:
        cntr.setLayoutX(COUNTERS_POS[player][2]);
        cntr.setLayoutY(COUNTERS_POS[player][3] + this.counters[player][2] * ((player == 0) ? 25.0D : -25.0D));
        this.counters[player][2] = this.counters[player][2] + 1;
        break;
    } 
    this.playersCounters[player].add(cntr);
    getChildren().add(cntr);
  }
  
  public final void removeCounter(int type, int player) {
    for (int i = this.playersCounters[player].size() - 1; i >= 0; i--) {
      Counter counter = this.playersCounters[player].get(i);
      if (counter.type == type) {
        this.playersCounters[player].remove(counter);
        getChildren().remove(counter);
        break;
      } 
    } 
    this.counters[player][type] = this.counters[player][type] - 1;
  }
  
  public final void addBonus(Bonus bonus, int index, int player) {
    bonus.setScaleX(0.7D);
    bonus.setScaleY(0.7D);
    bonus.setLayoutX(BONUS_POS[player][index][0]);
    bonus.setLayoutY(BONUS_POS[player][index][1]);
    getChildren().add(bonus);
  }
  
  public final void removeBonus(Bonus bonus) {
    getChildren().remove(bonus);
  }
  
  public class Counter extends Parent {
    private final String[] urls = new String[] { "fuel.png", "shield.png", "life.png" };
    
    private Image img;
    
    private ImageView imgView;
    
    private int type;
    
    private Counter(int type) {
      this.type = type;
      this.img = new Image("pentagon/images/" + this.urls[type]);
      this.imgView = new ImageView(this.img);
      getChildren().add(this.imgView);
    }
  }
}
