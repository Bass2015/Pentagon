package pentagon.elements;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import pentagon.game.Player;
import pentagon.game.Utils;

public class MiniShip extends Ship implements MovingElement {
  private MainShip mainShip;
  
  private final int type;
  
  private long lastMoved;
  
  private double direction;
  
  private boolean destroyed;
  
  private Point2D initPoint;
  
  private double initOrientation;
  
  public MiniShip(int type, Player pilot) {
    super(pilot);
    if (type == 0) {
      this.body = (Shape)new Polygon(MINISHIP_POINTS);
      this.points = Utils.doubleToPointArray(MINISHIP_POINTS);
    } else if (type % 2 == 0) {
      this.body = (Shape)new Polygon(SHORT_TRI_POINTS);
      this.points = Utils.doubleToPointArray(SHORT_TRI_POINTS);
    } else {
      this.body = (Shape)new Polygon(TALL_TRI_POINTS);
      this.points = Utils.doubleToPointArray(TALL_TRI_POINTS);
    } 
    this.speedMod = 1.5D;
    this.shootingSpeedMod = 2.0D;
    this.type = type;
//    setSides();
    getChildren().add(this.body);
  }
  
  public Point2D getInitPoint() {
    return this.initPoint;
  }
  
  public double getInitOrientation() {
    return this.initOrientation;
  }
  
  public boolean getDestroyed() {
    return this.destroyed;
  }
  
  public void setMainShip(MainShip ship) {
    this.mainShip = ship;
  }
  
  protected static MiniShip[] generateMiniShips(MainShip ship) {
    MiniShip[] ms = new MiniShip[11];
    for (int i = 0; i < ms.length; i++) {
      ms[i] = new MiniShip(i, ship.pilot);
      (ms[i]).mainShip = ship;
      if (i == 0) {
        (ms[i]).initPoint = new Point2D(0.0D, 0.0D);
      } else {
        (ms[i]).initPoint = Utils.miniShipPosition(MINI_ANGLES[i - 1], DEFAULT_MODE);
        ms[i].setLayoutX((ms[i]).initPoint.getX());
        ms[i].setLayoutY((ms[i]).initPoint.getY());
        ms[i].setRotate(MINI_ORIENTATIONS[i - 1]);
        (ms[i]).initOrientation = ms[i].getRotate();
      } 
    } 
    return ms;
  }
  
  public void orientate(double sceneX, double sceneY) {
    if (this.mainShip.isScattered()) {
      Point2D position = sceneToLocal(sceneX, sceneY);
      double angle = getRotate() + Math.toDegrees(Math.atan2(position.getY(), position.getX())) + 90.0D;
      rotate(angle);
    } 
  }
  
  public void move() {
    if (this.mainShip.isScattered()) {
      decideDirection();
      double correctSpeed = 5.0D * this.speedMod;
      double newX = getLayoutX() + correctSpeed * Math.sin(this.direction);
      double newY = getLayoutY() - correctSpeed * Math.cos(this.direction);
      keepInScene(newX, newY);
      move(newX, newY);
    } 
  }
  
  private void decideDirection() {
    long currentTime = System.nanoTime();
    if (this.lastMoved == 0L || currentTime - this.lastMoved > 500000000L) {
      this.lastMoved = currentTime;
      this.direction = RND.nextDouble() * Math.PI * 2.0D;
    } 
  }
  
  private void keepInScene(double x, double y) {
    Point2D scatteredAt = this.mainShip.getScatteredAt();
    if (x < 27.0D - scatteredAt.getX() || x > 1270.0D - scatteredAt
      .getX() || y < 30.0D - scatteredAt
      .getY() || y > 677.0D - scatteredAt
      .getY())
      this.direction -= Math.PI; 
  }
  
  public void relocate(Point2D point) {
    if (point == null)
      point = this.initPoint; 
    Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(1.0D), new KeyValue[] { new KeyValue((WritableValue)layoutXProperty(), Double.valueOf(point.getX()), Interpolator.EASE_OUT), new KeyValue((WritableValue)layoutYProperty(), Double.valueOf(point.getY()), Interpolator.EASE_OUT), new KeyValue((WritableValue)rotateProperty(), Double.valueOf(this.initOrientation), Interpolator.EASE_OUT), new KeyValue((WritableValue)opacityProperty(), Integer.valueOf(1), Interpolator.EASE_OUT), new KeyValue((WritableValue)this.mainShip.body.opacityProperty(), Integer.valueOf(1), Interpolator.DISCRETE) }) });
    tl.play();
  }
  
  public void exploteAnimation() {
    double x = 0.0D;
    double y = 0.0D;
    if (this.type != 0) {
      x = this.initPoint.getX() + 200.0D * Math.cos(Math.toRadians(MINI_ANGLES[this.type - 1]));
      y = this.initPoint.getY() + 200.0D * Math.sin(Math.toRadians(MINI_ANGLES[this.type - 1]));
    } 
    Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(1.5D), new KeyValue[] { new KeyValue((WritableValue)layoutXProperty(), Double.valueOf(x), Interpolator.LINEAR), new KeyValue((WritableValue)layoutYProperty(), Double.valueOf(y), Interpolator.LINEAR), new KeyValue((WritableValue)rotateProperty(), Integer.valueOf(360), Interpolator.LINEAR), new KeyValue((WritableValue)opacityProperty(), Integer.valueOf(0), Interpolator.LINEAR) }) });
    tl.play();
  }
  
  public GameObject[] getHit(GameObject obj) {
    if (!this.mainShip.isScattered())
      this.mainShip.getHit(obj); 
    return null;
  }
}
