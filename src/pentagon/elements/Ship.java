package pentagon.elements;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import pentagon.game.Player;

public abstract class Ship extends GameObject implements Hittable {
  public static final int CENTER = 0;
  
  protected static final int TALL1 = 1;
  
  protected static final int SHORT1 = 2;
  
  protected static final int TALL2 = 3;
  
  protected static final int SHORT2 = 4;
  
  protected static final int TALL3 = 5;
  
  protected static final int SHORT3 = 6;
  
  protected static final int TALL4 = 7;
  
  protected static final int SHORT4 = 8;
  
  protected static final int TALL5 = 9;
  
  protected static final int SHORT5 = 10;
  
  protected double speedMod;
  
  protected double shootingSpeedMod;
  
  protected int shootMode;
  
  protected long shotAt;
  
  protected Bullet attachedBullet;
  
  protected Player pilot;
  
  public Ship(Player pilot) {
    this.pilot = pilot;
  }
  
  public Player getPilot() {
    return this.pilot;
  }
  
  public double getSpeedMod() {
    return this.speedMod;
  }
  
  public double getShootingSpeedMod() {
    return this.shootingSpeedMod;
  }
  
  public void setSpeedMod(double speedMod) {
    this.speedMod = speedMod;
  }
  
  public void setShootMode(int mode) {
    this.shootMode = mode;
  }
  
  public void setShootingSpeedMod(double shootSpeedMod) {
    this.shootingSpeedMod = shootSpeedMod;
  }
  
  public void rotate(int direction) {
    double angle = this.orientation + Math.copySign(5.0D * this.speedMod, direction);
    if (this.attachedBullet != null)
      this.attachedBullet.move(localToScene(this.points[0])); 
    rotate(angle);
  }
  
  public Bullet[] shoot() {
    if (this instanceof MiniShip && ((MiniShip)this).getDestroyed())
      return null; 
    if (this.shotAt == 0L || (System.nanoTime() - this.shotAt) > 8.0E8D / this.shootingSpeedMod) {
      Bullet[] b;
      Point2D starting;
      int i;
      switch (this.shootMode) {
        case 0:
          starting = localToScene(this.points[0]);
          b = new Bullet[1];
          b[0] = new Bullet(this, starting.getX(), starting.getY(), this.orientation);
          this.shotAt = System.nanoTime();
          return b;
        case 1:
          b = new Bullet[5];
          for (i = 0; i < this.points.length; i++) {
            starting = localToScene(this.points[i]);
            b[i] = new Bullet(this, starting.getX(), starting.getY(), this.orientation + (i * 72));
          } 
          this.shotAt = System.nanoTime();
          return b;
        case 2:
          b = new Bullet[1];
          if (this.attachedBullet == null) {
            starting = localToScene(this.points[0]);
            b = new Bullet[1];
            b[0] = new Bullet(this, starting.getX(), starting.getY(), this.orientation);
            b[0].setAttached(true);
            this.attachedBullet = b[0];
            Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(6.0D), new KeyValue[] { new KeyValue((WritableValue)this.attachedBullet.scaleXProperty(), Integer.valueOf(30), Interpolator.LINEAR), new KeyValue((WritableValue)this.attachedBullet.scaleYProperty(), Integer.valueOf(30), Interpolator.LINEAR) }) });
            tl.play();
            this.attachedBullet.setOpacity(0.75D);
            this.attachedBullet.body.setFill((Paint)Color.AQUAMARINE);
            this.attachedBullet.setEffect((Effect)new DropShadow(10.0D, Color.AQUAMARINE));
          } 
          this.shotAt = System.nanoTime();
          return b;
        case 3:
          starting = localToScene(this.points[0]);
          b = new Bullet[1];
          b[0] = new Bullet(this, starting.getX(), starting.getY(), this.orientation);
          b[0].setExplosive(true);
          this.shotAt = System.nanoTime();
          return b;
      } 
      return null;
    } 
    return null;
  }
  
  public void releaseBullet() {
    if (this.attachedBullet != null) {
      this.attachedBullet.setOrientation(this.orientation);
      this.attachedBullet.setAttached(false);
      this.attachedBullet = null;
    } 
  }
}
