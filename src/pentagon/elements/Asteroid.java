package pentagon.elements;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import pentagon.game.PentagonConstants;
import pentagon.game.Utils;
import pentagon.levels.Level;

public class Asteroid extends GameObject implements Hittable, MovingElement, PentagonConstants {
  private double direction;
  
  private long lastMoved;
  
  private final double speed;
  
  private Bonus bonus;
  
  public Asteroid(double size, double x, double y, Level level) {
    this.level = level;
    this.speed = 0.8333333333333334D;
    this.size = size;
    if (size == 17.0D && 
      Math.random() < 0.3D) {
      int type = RND.nextInt(13);
      this.bonus = Bonus.buildBonus(type, level);
    } 
    double[] buildingPoints = points();
    this.body = (Shape)new Polygon(buildingPoints);
    this.body.setOpacity(0.0D);
    this.points = Utils.doubleToPointArray(buildingPoints);
    getChildren().add(this.body);
    double grey = 0.8D - Math.random() * 0.7D;
    this.body.setFill((Paint)Color.color(grey, grey, grey));
    Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(15.0D), new KeyValue[] { new KeyValue((WritableValue)rotateProperty(), Integer.valueOf(360), Interpolator.LINEAR) }) });
    tl.setCycleCount(-1);
    tl.setAutoReverse(true);
    tl.play();
    Timeline tl2 = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(2.0D), new KeyValue[] { new KeyValue((WritableValue)this.body.opacityProperty(), Integer.valueOf(1), Interpolator.LINEAR) }) });
    tl2.setCycleCount(1);
    tl2.play();
    setLayoutX(x);
    setLayoutY(y);
    setEffect((Effect)new DropShadow(25.0D, Color.BLACK));
  }
  
  public GameObject[] getHit(GameObject obj) {
    GameObject[] toReturn = null;
    if (this.size != 17.0D) {
      toReturn = divide();
    } else {
      toReturn = new GameObject[1];
      if (this.bonus != null) {
        this.bonus.setLayoutX(getLayoutX());
        this.bonus.setLayoutY(getLayoutY());
        toReturn[0] = this.bonus;
      } 
      this.removable = true;
    } 
    return toReturn;
  }
  
  public void move() {
    decideDirection();
    double newX = getLayoutX() + this.speed * Math.sin(this.direction);
    double newY = getLayoutY() - this.speed * Math.cos(this.direction);
    if (newX < 0.0D || newX > 1300.0D || newY < 0.0D || newY > 700.0D)
      this.removable = true; 
    move(newX, newY);
  }
  
  private void decideDirection() {
    long currentTime = System.nanoTime();
    if (this.lastMoved == 0L || currentTime - this.lastMoved > 6000000000L) {
      this.lastMoved = currentTime;
      this.direction = RND.nextDouble() * Math.PI * 2.0D;
    } 
  }
  
  private GameObject[] divide() {
    this.removable = true;
    double x = getLayoutX();
    double y = getLayoutY();
    Asteroid[] ast = { new Asteroid(this.size / 2.0D, x, y, this.level), new Asteroid(this.size / 2.0D, x, y, this.level) };
    (ast[0]).direction = this.direction;
    (ast[1]).direction = -this.direction;
    return (GameObject[])ast;
  }
  
  private double[] points() {
    double[] points = { 
        this.size, 0.0D, this.size * Math.cos(Math.toRadians(AST_ANGLES[0])), this.size * Math.sin(Math.toRadians(AST_ANGLES[0])), this.size * Math.cos(Math.toRadians(AST_ANGLES[1])), this.size * Math.sin(Math.toRadians(AST_ANGLES[1])), this.size * Math.cos(Math.toRadians(AST_ANGLES[2])), this.size * Math.sin(Math.toRadians(AST_ANGLES[2])), this.size * Math.cos(Math.toRadians(AST_ANGLES[3])), this.size * Math.sin(Math.toRadians(AST_ANGLES[3])), 
        this.size * Math.cos(Math.toRadians(AST_ANGLES[4])), this.size * Math.sin(Math.toRadians(AST_ANGLES[4])), this.size * Math.cos(Math.toRadians(AST_ANGLES[5])), this.size * Math.sin(Math.toRadians(AST_ANGLES[5])), this.size * Math.cos(Math.toRadians(AST_ANGLES[6])), this.size * Math.sin(Math.toRadians(AST_ANGLES[6])), this.size * Math.cos(Math.toRadians(AST_ANGLES[7])), this.size * Math.sin(Math.toRadians(AST_ANGLES[7])) };
    return points;
  }
}
