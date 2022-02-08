package pentagon.levels;

import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import pentagon.elements.Asteroid;
import pentagon.elements.Bonus;
import pentagon.elements.Bullet;
import pentagon.elements.GameObject;
import pentagon.elements.Hittable;
import pentagon.elements.MainShip;
import pentagon.elements.MiniShip;
import pentagon.elements.MovingElement;
import pentagon.game.PentagonConstants;
import pentagon.game.Player;

public class Space extends Level implements PentagonConstants {
  public Label l;
  
  private final Group root;
  
  private final ArrayList<MovingElement> movingElements;
  
  private final ArrayList<Bullet> bullets;
  
  private final ArrayList<Hittable> hittableElements;
  
  private final ArrayList<Bonus> bonuses;
  
  private final ArrayList<Asteroid> asteroids;
  
  private final Player[] players = new Player[2];
  
  private final MainShip[] ships = new MainShip[2];
  
  public final Display display;
  
  private ArrayList<KeyCode> pressedKeys;
  
  private int bonusType = 0;
  
  public Space(Parent root) {
    super(root, 1300.0D, 700.0D);
    setFill((Paint)Color.BLACK);
    this.root = (Group)root;
    this.movingElements = new ArrayList<>();
    this.hittableElements = new ArrayList<>();
    this.bullets = new ArrayList<>();
    this.bonuses = new ArrayList<>();
    this.asteroids = new ArrayList<>();
    this.players[0] = new Player(0, Color.SLATEGREY, Color.FIREBRICK);
    this.players[1] = new Player(1, Color.MAROON, Color.MEDIUMAQUAMARINE);
    this.ships[0] = this.players[0].getShip();
    this.ships[1] = this.players[1].getShip();
    this.ships[0].setLevel(this);
    this.ships[1].setLevel(this);
    this.hittableElements.add(this.ships[0]);
    this.hittableElements.add(this.ships[1]);
    for (int i = 0; i < this.players.length; i++) {
      for (MiniShip miniShip : this.players[i].getShip().getMiniShips()) {
        this.movingElements.add(miniShip);
        this.hittableElements.add(miniShip);
      } 
    } 
    this.display = new Display();
    inputs();
    timeline();
    this.l = new Label("JUGADOR 1(der): flechas para mover, 0 para disparar, 1 para sprint, 2 y 3para gastar bonus\nJUGADOR 2(izq): wasd para mover, F para disparar, R para sprint, T e Y para gastar bonus");
    this.l.setTextFill((Paint)Color.WHITE);
    this.l.setTranslateX(650.0D);
    this.l.setTranslateY(200.0D);
    Timeline tl = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(7.0D), new KeyValue[] { new KeyValue((WritableValue)this.l.opacityProperty(), Integer.valueOf(0), Interpolator.DISCRETE) }) });
    tl.play();
    this.ships[0].setLayoutX(1050.0D);
    this.ships[0].setLayoutY(350.0D);
    this.ships[1].setLayoutX(250.0D);
    this.ships[1].setLayoutY(350.0D);
    this.root.getChildren().addAll((Node[])(Object[])new Node[] { (Node)this.ships[0], (Node)this.ships[1], (Node)this.l });
    this.root.getChildren().add(this.display);
  }
  
  private void inputs() {
    this.pressedKeys = new ArrayList<>();
    setOnKeyPressed(event -> {
          if (!this.pressedKeys.contains(event.getCode()))
            this.pressedKeys.add(event.getCode()); 
          if (event.getCode().equals(KeyCode.NUMPAD1) && !this.ships[0].isBoosting())
            this.ships[0].boost(); 
          if (event.getCode().equals(KeyCode.R) && !this.ships[1].isBoosting())
            this.ships[1].boost(); 
        });
    setOnKeyReleased(event -> this.pressedKeys.remove(event.getCode()));
    setOnMouseClicked(event -> {
          Bonus bonus = Bonus.buildBonus(this.bonusType, this);
          bonus.setLayoutX(650.0D);
          bonus.setLayoutY(350.0D);
          Bonus[] b = { bonus };
          addElements(1, (GameObject[])b);
          if (this.bonusType != 12) {
            this.bonusType++;
          } else {
            this.bonusType = 0;
          } 
        });
    setOnMouseMoved(event -> {
        
        });
  }
  
  private void timeline() {
    Timeline t = new Timeline(new KeyFrame[] { new KeyFrame(Duration.millis(40.0D), new EventHandler<ActionEvent>() {
              public void handle(ActionEvent event) {
                Space.this.moveShips();
                for (MovingElement me : Space.this.movingElements)
                  me.move(); 
                Space.this.orientateMiniShips();
                Space.this.generateAsteroids();
                Space.this.checkBonus();
                Space.this.controls();
                Space.this.checkHits();
                Space.this.resetShips();
                Space.this.removeObjects();
              }
            },  new KeyValue[0]) });
    t.setCycleCount(-1);
    t.play();
  }
  
  private void moveShips() {
    if (!this.ships[0].isScattered() && !this.ships[0].isExploded()) {
      if (this.pressedKeys.contains(KeyCode.LEFT))
        this.ships[0].rotate(-1); 
      if (this.pressedKeys.contains(KeyCode.RIGHT))
        this.ships[0].rotate(1); 
      if (this.pressedKeys.contains(KeyCode.UP))
        this.ships[0].move(1); 
      if (this.pressedKeys.contains(KeyCode.DOWN))
        this.ships[0].move(-1); 
    } 
    if (!this.ships[1].isScattered() && !this.ships[1].isExploded()) {
      if (this.pressedKeys.contains(KeyCode.A))
        this.ships[1].rotate(-1); 
      if (this.pressedKeys.contains(KeyCode.D))
        this.ships[1].rotate(1); 
      if (this.pressedKeys.contains(KeyCode.W))
        this.ships[1].move(1); 
      if (this.pressedKeys.contains(KeyCode.S))
        this.ships[1].move(-1); 
    } 
  }
  
  private void orientateMiniShips() {
    for (MiniShip miniShip : this.ships[0].getMiniShips()) {
      if (this.ships[0].isScattered())
        miniShip.orientate(this.ships[1].getLayoutX(), this.ships[1].getLayoutY()); 
    } 
    for (MiniShip miniShip : this.ships[1].getMiniShips()) {
      if (this.ships[1].isScattered())
        miniShip.orientate(this.ships[0].getLayoutX(), this.ships[0].getLayoutY()); 
    } 
  }
  
  private void removeObjects() {
    this.movingElements.removeIf(movEl -> {
          if (((GameObject)movEl).isRemovable()) {
            this.root.getChildren().remove(movEl);
            if (movEl instanceof Bullet)
              this.bullets.remove(movEl); 
            if (movEl instanceof Bonus && !((Bonus)movEl).isCaught())
              this.bonuses.remove(movEl); 
            if (movEl instanceof Hittable)
              this.hittableElements.remove(movEl); 
            if (movEl instanceof Asteroid)
              this.asteroids.remove(movEl); 
            return true;
          } 
          return false;
        });
  }
  
  private void controls() {
    if (this.pressedKeys.contains(KeyCode.NUMPAD0) && !this.ships[0].isExploded())
      addElements(0, (GameObject[])this.ships[0].shoot()); 
    if (this.pressedKeys.contains(KeyCode.NUMPAD2) && !this.ships[0].isExploded())
      this.ships[0].spendBonus(0, KeyCode.NUMPAD2); 
    if (this.pressedKeys.contains(KeyCode.NUMPAD3) && !this.ships[0].isExploded())
      this.ships[0].spendBonus(1, KeyCode.NUMPAD3); 
    if (this.ships[0].isScattered())
      for (MiniShip miniShip : this.ships[0].getMiniShips())
        addElements(0, (GameObject[])miniShip.shoot());  
    if (this.pressedKeys.contains(KeyCode.F) && !this.ships[1].isExploded())
      addElements(0, (GameObject[])this.ships[1].shoot()); 
    if (this.pressedKeys.contains(KeyCode.T) && !this.ships[0].isExploded())
      this.ships[1].spendBonus(0, KeyCode.T); 
    if (this.pressedKeys.contains(KeyCode.Y) && !this.ships[0].isExploded())
      this.ships[1].spendBonus(1, KeyCode.Y); 
    if (this.ships[1].isScattered())
      for (MiniShip miniShip : this.ships[1].getMiniShips())
        addElements(0, (GameObject[])miniShip.shoot());  
    if (this.pressedKeys.contains(KeyCode.SPACE));
  }
  
  private void checkHits() {
    GameObject[] newObjects = null;
    for (Bullet bullet : this.bullets) {
      for (Hittable hittable : this.hittableElements) {
        if (bullet.hasHit(hittable) && hittable instanceof Asteroid)
          newObjects = ((Asteroid)hittable).getHit((GameObject)bullet); 
      } 
    } 
    for (int i = 0; i < this.ships.length; i++) {
      MainShip ship1 = this.ships[i];
      MainShip ship2 = this.ships[(i == 0) ? 1 : 0];
      if (ship1.isBerserkr() && ship1.touches((GameObject)ship2) && 
        !ship2.isPhantom() && 
        !ship2.isExploded() && 
        !ship2.isScattered())
        ship2.getHit((GameObject)ship1); 
      for (Asteroid asteroid : this.asteroids) {
        if (ship1.touches((GameObject)asteroid) && 
          !ship1.isPhantom() && 
          !ship1.isExploded() && 
          !ship1.isScattered()) {
          if (ship1.isBerserkr()) {
            asteroid.getHit((GameObject)ship1);
            continue;
          } 
          ship1.getHit((GameObject)asteroid);
        } 
      } 
    } 
    if (newObjects != null)
      if (newObjects[0] instanceof Asteroid) {
        addElements(2, newObjects);
      } else if (newObjects[0] instanceof Bonus) {
        addElements(1, newObjects);
      }  
  }
  
  private void checkBonus() {
    ArrayList<Bonus> bonusToRemove = new ArrayList<>();
    for (Bonus bonus : this.bonuses) {
      for (MainShip ship : this.ships) {
        if (ship.touches((GameObject)bonus)) {
          bonus.setRemovable(true);
          if (bonus.isCatchable()) {
            ship.catchBonus(bonus);
          } else if (!bonus.isActive()) {
            bonus.applyEffect(ship);
          } 
        } 
      } 
      if (bonus.isActive()) {
        if (bonus.getType() != 11) {
          if (bonus.removeEffect())
            bonusToRemove.add(bonus); 
          continue;
        } 
        if (!this.pressedKeys.contains(bonus.getActivator())) {
          bonus.removeEffect();
          bonusToRemove.add(bonus);
        } 
      } 
    } 
    if (!this.bonuses.isEmpty() && !bonusToRemove.isEmpty())
      this.bonuses.removeAll(bonusToRemove); 
  }
  
  private void generateAsteroids() {
    if (Math.random() < 0.012D) {
      double size, prob = Math.random();
      if (prob < 0.05D) {
        size = 136.0D;
      } else if (prob < 0.25D) {
        size = 68.0D;
      } else if (prob < 0.85D) {
        size = 34.0D;
      } else {
        size = 17.0D;
      } 
      double x = Math.random() * 1300.0D;
      double y = Math.random() * 700.0D;
      Asteroid ast = new Asteroid(size, x, y, this);
      this.root.getChildren().add(ast);
      this.hittableElements.add(ast);
      this.movingElements.add(ast);
      this.asteroids.add(ast);
    } 
  }
  
  private void resetShips() {
    for (MainShip ship : this.ships) {
      if (ship.isExploded())
        ship.respawn(); 
      if (ship.isPhantom() && 
        System.nanoTime() - ship.getPhantomedAt() > 5000000000L)
        ship.setPhantom(false); 
      if (ship.isBoosting() && 
        System.nanoTime() - ship.getBoostAt() > 400000000L) {
        ship.setBoosting(false);
        ship.setSpeedMod(ship.getSpeedMod() / 3.0D);
      } 
    } 
  }
  
  public final void addElements(int objectClass, GameObject[] objects) {
    if (objects != null) {
      this.root.getChildren().addAll((Node[])(Object[])objects);
      for (GameObject object : objects)
        this.movingElements.add((MovingElement)object); 
      switch (objectClass) {
        case 0:
          for (GameObject object : objects)
            this.bullets.add((Bullet)object); 
          break;
        case 2:
          for (GameObject object : objects) {
            object.toBack();
            this.hittableElements.add((Hittable)object);
            this.asteroids.add((Asteroid)object);
          } 
          break;
        case 1:
          for (GameObject object : objects)
            this.bonuses.add((Bonus)object); 
          break;
      } 
    } 
    this.display.toFront();
  }
}
