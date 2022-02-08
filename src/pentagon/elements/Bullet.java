package pentagon.elements;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import pentagon.game.Player;
import pentagon.game.Utils;

public class Bullet extends GameObject implements MovingElement {
  private static final double SPEED = 15.0D;
  
  private static final double SIZE = 2.0D;
  
  private Player pilot;
  
  private Ship ship;
  
  private boolean attached;
  
  private boolean explosive;
  
  private boolean exploding;
  
  public boolean isAttached() {
    return this.attached;
  }
  
  public void setAttached(boolean attached) {
    this.attached = attached;
  }
  
  public void setOrientation(double orientation) {
    this.orientation = orientation;
  }
  
  public void setExplosive(boolean explosive) {
    this.explosive = explosive;
  }
  
  public Bullet(Ship ship, double startingX, double startingY, double orientation) {
    this.ship = ship;
    this.pilot = ship.getPilot();
    this.level = ship.getLevel();
    this.orientation = orientation;
    this.body = (Shape)new Polygon(BULLET_POINTS);
    this.body.setFill((Paint)Color.WHITE);
    this.points = Utils.doubleToPointArray(BULLET_POINTS);
    this.attached = false;
    getChildren().add(this.body);
    relocate(startingX, startingY);
  }
  
  public Player getPilot() {
    return this.pilot;
  }
  
  public void move() {
    if (!this.attached && !this.exploding) {
      double newX = getLayoutX() + 15.0D * Math.sin(Math.toRadians(this.orientation));
      double newY = getLayoutY() - 15.0D * Math.cos(Math.toRadians(this.orientation));
      move(newX, newY);
      if (newX < 0.0D || newX > 1300.0D || newY < 0.0D || newY > 700.0D)
        this.removable = true; 
    } 
  }
  
  public void move(Point2D point) {
    move(point.getX(), point.getY());
  }
  
  public boolean hasHit(Hittable object) {
    if (this.attached)
      return false; 
    if (touches((GameObject)object)) {
      if (touchedEnemyShip((GameObject)object)) {
        object.getHit(this);
        if (this.explosive) {
          explode();
        } else if (scaleXProperty().doubleValue() == 1.0D) {
          this.removable = true;
        } 
      } 
      if (object instanceof Asteroid) {
        double doubleValue = scaleXProperty().doubleValue();
        if (this.explosive) {
          explode();
        } else if (scaleXProperty().doubleValue() == 1.0D) {
          this.removable = true;
        } 
      } 
      return true;
    } 
    return false;
  }
  
  private boolean touchedEnemyShip(GameObject object) {
    return (object instanceof MainShip && !((Ship)object).getPilot().equals(this.pilot) && 
      !((MainShip)object).isExploded() && 
      !((MainShip)object).isPhantom() && 
      !((MainShip)object).isScattered() && 
      !((MainShip)object).isBerserkr());
  }
  
  private void explode() {
    this.body.setFill(null);
    this.body.setStroke((Paint)Color.ORANGE);
    this.body.setEffect((Effect)new DropShadow(5.0D, Color.ORANGERED));
    Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(2.0D), new EventHandler<ActionEvent>() {
              public void handle(ActionEvent event) {
                Bullet.this.removable = true;
              }
            },  new KeyValue[] { new KeyValue((WritableValue)scaleXProperty(), Integer.valueOf(200), Interpolator.LINEAR), new KeyValue((WritableValue)scaleYProperty(), Integer.valueOf(200), Interpolator.LINEAR), new KeyValue((WritableValue)opacityProperty(), Double.valueOf(0.1D), Interpolator.LINEAR) }) });
    tl.play();
    this.exploding = true;
  }
}
