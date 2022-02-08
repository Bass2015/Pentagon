package pentagon.elements;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import pentagon.game.Player;
import pentagon.game.Utils;
import pentagon.levels.Level;
import pentagon.levels.Space;

public class MainShip extends Ship {
  private final MiniShip[] miniShips;
  
  private int shields;
  
  private int lifes;
  
  private int fuel;
  
  private Bonus[] bonuses = new Bonus[2];
  
  private boolean berserkr;
  
  private boolean phantom;
  
  private boolean scattered;
  
  private boolean exploded;
  
  private boolean boosting;
  
  private long explodedAt;
  
  private long phantomedAt;
  
  private long boostAt;
  
  private Point2D scatteredAt;
  
  public MainShip(Player pilot) {
    super(pilot);
    this.points = Utils.doubleToPointArray(POINTS);
    this.body = (Shape)new Polygon(POINTS);
    this.body.setFill((Paint)pilot.getColors()[0]);
    this.miniShips = MiniShip.generateMiniShips(this);
    for (int i = 0; i < this.miniShips.length; i++) {
      if (i == 0 || i == 1 || i == 5 || i == 7) {
        (this.miniShips[i]).body.setFill((Paint)pilot.getColors()[1]);
      } else {
        (this.miniShips[i]).body.setFill((Paint)pilot.getColors()[0]);
      } 
    } 
    getChildren().add(this.body);
    getChildren().addAll(Arrays.asList(this.miniShips));
    this.miniShips[0].toFront();
    this.miniShips[1].toFront();
    this.miniShips[5].toFront();
    this.miniShips[7].toFront();
    this.speedMod = 1.0D;
    this.shootingSpeedMod = 1.0D;
    this.shields = 3;
    this.lifes = 3;
    this.fuel = 3;
  }
  
  public boolean isScattered() {
    return this.scattered;
  }
  
  public boolean isExploded() {
    return this.exploded;
  }
  
  public boolean isPhantom() {
    return this.phantom;
  }
  
  public boolean isBoosting() {
    return this.boosting;
  }
  
  public boolean isBerserkr() {
    return this.berserkr;
  }
  
  public Point2D getScatteredAt() {
    return this.scatteredAt;
  }
  
  public long getPhantomedAt() {
    return this.phantomedAt;
  }
  
  public long getBoostAt() {
    return this.boostAt;
  }
  
  public MiniShip[] getMiniShips() {
    return this.miniShips;
  }
  
  public double getOrientation() {
    return this.orientation;
  }
  
  public void setPhantom(boolean ph) {
    if (ph) {
      this.phantomedAt = System.nanoTime();
      setEffect((Effect)new DropShadow(10.0D, Color.WHITE));
      this.phantom = true;
      setOpacity(0.3D);
    } else {
      setEffect(null);
      this.phantom = false;
      setOpacity(1.0D);
    } 
  }
  
  public void setBoosting(boolean boost) {
    this.boosting = boost;
  }
  
  public void setOrientation(double ori) {
    this.orientation = ori;
  }
  
  public void addCounter(int type) {
    switch (type) {
      case 0:
        this.fuel++;
        break;
      case 1:
        this.shields++;
        break;
      case 2:
        this.lifes++;
        break;
    } 
  }
  
  public void removeCounter(int type) {
    switch (type) {
      case 0:
        this.fuel--;
        break;
      case 1:
        this.shields--;
        break;
      case 2:
        this.lifes--;
        if (this.lifes == 0)
          explote(); 
        break;
    } 
  }
  
  public void setLevel(Level level) {
    super.setLevel(level);
    for (MiniShip miniShip : this.miniShips)
      miniShip.setLevel(level); 
  }
  
  public void setMiniShipPositions(double mode) {
    Point2D position = null;
    this.miniShips[0].relocate((Point2D)null);
    for (int i = 1; i < this.miniShips.length; i++) {
      if (mode != DEFAULT_MODE)
        position = Utils.miniShipPosition(MINI_ANGLES[i - 1], mode); 
      this.miniShips[i].relocate(position);
    } 
  }
  
  public void move(int direction) {
    double correctSpeed = Math.copySign(5.0D * this.speedMod, direction);
    double newX = getLayoutX() + correctSpeed * Math.sin(Math.toRadians(this.orientation));
    double newY = getLayoutY() - correctSpeed * Math.cos(Math.toRadians(this.orientation));
    if (this.attachedBullet != null)
      this.attachedBullet.move(localToScene(this.points[0])); 
    if (newX < 27.0D)
      newX = 27.0D; 
    if (newX > 1270.0D)
      newX = 1270.0D; 
    if (newY < 30.0D)
      newY = 30.0D; 
    if (newY > 677.0D)
      newY = 677.0D; 
    move(newX, newY);
  }
  
  public GameObject[] getHit(GameObject obj) {
    if (obj instanceof Bullet && !((Bullet)obj).getPilot().equals(this.pilot))
      if (this.shields == 0) {
        explote();
      } else {
        this.shields--;
        ((Space)this.level).display.removeCounter(1, this.pilot.getTag());
        final Polygon p = new Polygon(POINTS);
        p.setStroke((Paint)Color.WHITESMOKE);
        p.setFill(null);
        getChildren().add(p);
        Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(0.1D), new EventHandler<ActionEvent>() {
                  public void handle(ActionEvent event) {
                    MainShip.this.getChildren().remove(p);
                  }
                },  new KeyValue[] { new KeyValue((WritableValue)p.scaleXProperty(), Integer.valueOf(3), Interpolator.LINEAR), new KeyValue((WritableValue)p.scaleYProperty(), Integer.valueOf(3), Interpolator.LINEAR), new KeyValue((WritableValue)p.opacityProperty(), Integer.valueOf(0), Interpolator.DISCRETE) }) });
        tl.play();
      }  
    if (obj instanceof Asteroid || obj instanceof MainShip)
      explote(); 
    return null;
  }
  
  private void explote() {
    this.exploded = true;
    if (this.lifes != 0) {
      this.lifes--;
      ((Space)this.level).display.removeCounter(2, this.pilot.getTag());
    } 
    for (int i = 0; i < this.shields; i++)
      ((Space)this.level).display.removeCounter(1, this.pilot.getTag()); 
    this.shields = 0;
    this.phantom = true;
    this.explodedAt = System.nanoTime();
    ArrayList<KeyValue> values = new ArrayList<>();
    for (int j = 0; j < this.miniShips.length; j++)
      this.miniShips[j].exploteAnimation(); 
    Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.millis(1.0D), new KeyValue[] { new KeyValue((WritableValue)this.body.opacityProperty(), Integer.valueOf(0), Interpolator.DISCRETE) }) });
    tl.play();
  }
  
  public void respawn() {
    if (this.lifes > 0 && System.nanoTime() - this.explodedAt > 4000000000L) {
      setPhantom(true);
      setMiniShipPositions(DEFAULT_MODE);
      KeyValue[] values = new KeyValue[11];
      for (int i = 0; i < values.length; i++) {
        values[i] = new KeyValue((WritableValue)this.miniShips[i].opacityProperty(), Integer.valueOf(1), Interpolator.LINEAR);
        this.miniShips[i].rotate(this.miniShips[i].getInitOrientation());
      } 
      Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(3.0D), new KeyValue[] { 
                values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], 
                values[10], new KeyValue((WritableValue)this.body.opacityProperty(), Integer.valueOf(1), Interpolator.LINEAR) }) });
      tl.play();
      this.exploded = false;
      for (int j = 0; j < 3; j++) {
        this.shields++;
        ((Space)this.level).display.addCounter(1, this.pilot.getTag());
      } 
    } 
  }
  
  public boolean catchBonus(Bonus bonus) {
    for (int i = 0; i < this.bonuses.length; i++) {
      if (this.bonuses[i] == null) {
        this.bonuses[i] = bonus;
        this.bonuses[i].setCaught(true);
        ((Space)this.level).display.addBonus(bonus, i, this.pilot.getTag());
        return true;
      } 
    } 
    return false;
  }
  
  public void spendBonus(int index, KeyCode activator) {
    if ((index == 0 || index == 1) && this.bonuses[index] != null) {
      this.bonuses[index].setActivator(activator);
      if (!this.bonuses[index].isActive()) {
        this.bonuses[index].applyEffect(this);
        this.bonuses[index].setOpacity(0.5D);
      } 
    } 
  }
  
  public void removeBonus(Bonus bonus) {
    for (int i = 0; i < this.bonuses.length; i++) {
      if (this.bonuses[i] != null && this.bonuses[i].equals(bonus))
        this.bonuses[i] = null; 
    } 
  }
  
  public void scatter() {
    this.body.setOpacity(0.0D);
    this.scattered = true;
    this.scatteredAt = new Point2D(getLayoutX(), getLayoutY());
    Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(1.0D), new KeyValue[] { new KeyValue((WritableValue)rotateProperty(), Integer.valueOf(0), Interpolator.LINEAR) }) });
    tl.play();
    this.orientation = 0.0D;
  }
  
  public void regroup() {
    if (this.scattered) {
      setPhantom(true);
      this.scattered = false;
    } 
    if (!getChildren().contains(this.body)) {
      this.body.setOpacity(0.0D);
      getChildren().add(this.body);
      this.body.toBack();
    } 
    for (MiniShip miniShip : this.miniShips)
      miniShip.setEffect(null); 
    setMiniShipPositions(DEFAULT_MODE);
    this.scatteredAt = null;
  }
  
  public void boost() {
    if (this.fuel > 0) {
      this.boosting = true;
      this.boostAt = System.nanoTime();
      this.speedMod *= 3.0D;
      this.fuel--;
      ((Space)this.level).display.removeCounter(0, this.pilot.getTag());
    } 
  }
  
  public void changeSize(double distanceFromCenter) {
    getChildren().remove(this.body);
    for (MiniShip miniShip : this.miniShips)
      miniShip.setEffect((Effect)new DropShadow(10.0D, Color.GREY)); 
    setMiniShipPositions(distanceFromCenter);
  }
  
  public void setBerserkr(boolean berserkr) {
    this.berserkr = berserkr;
    if (this.berserkr) {
      changeSize(BERSERKR_MODE);
    } else {
      regroup();
    } 
  }
  
  public void teleport() {
    double x = Math.random() * 1300.0D;
    double y = Math.random() * 700.0D;
    setPhantom(true);
    move(x, y);
  }
}
