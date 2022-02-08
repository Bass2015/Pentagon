package pentagon.elements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import pentagon.game.PentagonConstants;
import pentagon.game.Utils;
import pentagon.levels.Level;
import pentagon.levels.Space;

public abstract class Bonus extends GameObject implements MovingElement, PentagonConstants {
  private static final String[] URLS = new String[] { 
      "scatter-bonus.png", "fuel.png", "shield.png", "life.png", "phantom.png", "speed.png", "shootSpeed.png", "changeSize.png", "berserkr.png", "teleport.png", 
      "pentaShoot.png", "chargeShoot.png", "explosiveShoot.png" };
  
  protected int type;
  
  private final double direction;
  
  protected long startedAt;
  
  protected boolean catchable;
  
  protected boolean caught;
  
  protected boolean positive;
  
  protected MainShip ship;
  
  protected Image icon;
  
  protected ImageView view;
  
  private KeyCode activator;
  
  private Bonus() {
    this.size = 17.0D;
    this.points = Utils.doubleToPointArray(BONUS_POINTS);
    this.body = (Shape)new Polygon(BONUS_POINTS);
    getChildren().add(this.body);
    this.direction = RND.nextDouble() * Math.PI * 2.0D;
    setLayoutX(Math.random() * 1300.0D);
    setLayoutY(Math.random() * 700.0D);
  }
  
  public void setActivator(KeyCode activator) {
    this.activator = activator;
  }
  
  public KeyCode getActivator() {
    return this.activator;
  }
  
  public int getType() {
    return this.type;
  }
  
  public boolean isCatchable() {
    return this.catchable;
  }
  
  public boolean isActive() {
    return (this.startedAt != 0L);
  }
  
  public boolean isCaught() {
    return this.caught;
  }
  
  public void setCaught(boolean caught) {
    this.caught = caught;
  }
  
  public static Bonus buildBonus(int type, Level level) {
    Bonus bonus = null;
    switch (type) {
      case 0:
        bonus = new ScatterBonus();
        break;
      case 1:
        bonus = new FuelBonus();
        break;
      case 2:
        bonus = new ShieldBonus();
        break;
      case 3:
        bonus = new LifeBonus();
        break;
      case 4:
        bonus = new PhantomBonus();
        break;
      case 5:
        bonus = new SpeedBonus();
        break;
      case 6:
        bonus = new ShootSpeedBonus();
        break;
      case 7:
        bonus = new ChangeSizeBonus();
        break;
      case 8:
        bonus = new BerserkrBonus();
        break;
      case 9:
        bonus = new TeleportBonus();
        break;
      case 10:
        bonus = new ShootModeBonus(type, 1);
        break;
      case 11:
        bonus = new ChargeShootBonus();
        break;
      case 12:
        bonus = new ShootModeBonus(type, 3);
        break;
    } 
    initVariables(bonus, level);
    return bonus;
  }
  
  private static void initVariables(Bonus bonus, Level level) {
    bonus.level = level;
    Color color = null;
    if (bonus.catchable) {
      color = Color.CORNFLOWERBLUE;
    } else if (bonus.positive) {
      color = Color.DARKOLIVEGREEN;
    } else {
      color = Color.ORANGERED;
    } 
    bonus.body.setFill((Paint)color);
    bonus.body.setStroke((Paint)Color.GREY);
    bonus.getChildren().add(bonus.view);
  }
  
  public void applyEffect(MainShip ship) {
    this.startedAt = System.nanoTime();
    this.ship = ship;
  }
  
  public boolean removeEffect() {
    this.ship.removeBonus(this);
    this.startedAt = 0L;
    this.caught = false;
    ((Space)this.level).display.removeBonus(this);
    return true;
  }
  
  public void move() {
    double newX = getLayoutX() + 2.5D * Math.sin(this.direction);
    double newY = getLayoutY() - 2.5D * Math.cos(this.direction);
    if (newX < 0.0D || newX > 1300.0D || newY < 0.0D || newY > 700.0D)
      this.removable = true; 
    move(newX, newY);
  }
  
  private static class ScatterBonus extends Bonus {
    public ScatterBonus() {
      this.type = 0;
      this.catchable = true;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[0]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-17.0D);
      this.view.setTranslateY(-17.0D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.ship.scatter();
    }
    
    public boolean removeEffect() {
      if (System.nanoTime() - this.startedAt > 5000000000L) {
        this.ship.regroup();
        return super.removeEffect();
      } 
      return false;
    }
  }
  
  private static class PhantomBonus extends Bonus {
    public PhantomBonus() {
      this.type = 4;
      this.catchable = true;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[4]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-7.5D);
      this.view.setTranslateY(-9.5D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.ship.setPhantom(true);
    }
    
    public boolean removeEffect() {
      if (System.nanoTime() - this.startedAt > 5000000000L) {
        this.ship.setPhantom(false);
        return super.removeEffect();
      } 
      return false;
    }
  }
  
  private static class BerserkrBonus extends Bonus {
    public BerserkrBonus() {
      this.type = 8;
      this.catchable = true;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[8]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-17.0D);
      this.view.setTranslateY(-17.0D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.ship.setBerserkr(true);
    }
    
    public boolean removeEffect() {
      if (System.nanoTime() - this.startedAt > 5000000000L) {
        this.ship.setBerserkr(false);
        return super.removeEffect();
      } 
      return false;
    }
  }
  
  private static class TeleportBonus extends Bonus {
    public TeleportBonus() {
      this.type = 9;
      this.catchable = true;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[9]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-17.0D);
      this.view.setTranslateY(-17.0D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.ship.teleport();
    }
    
    public boolean removeEffect() {
      return super.removeEffect();
    }
  }
  
  private static class ShootModeBonus extends Bonus {
    private int shootMode;
    
    public ShootModeBonus(int type, int shootMode) {
      this.type = type;
      this.shootMode = shootMode;
      this.catchable = true;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[type]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-17.0D);
      this.view.setTranslateY(-17.0D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.ship.setShootMode(this.shootMode);
    }
    
    public boolean removeEffect() {
      if (System.nanoTime() - this.startedAt > 5000000000L) {
        this.ship.setShootMode(0);
        return super.removeEffect();
      } 
      return false;
    }
  }
  
  private static class ChargeShootBonus extends Bonus {
    public ChargeShootBonus() {
      this.type = 11;
      this.catchable = true;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[this.type]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-17.0D);
      this.view.setTranslateY(-17.0D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.ship.setShootMode(2);
      ((Space)this.level).addElements(0, (GameObject[])this.ship.shoot());
    }
    
    public boolean removeEffect() {
      if (this.ship != null) {
        this.ship.releaseBullet();
        this.ship.setShootMode(0);
        return super.removeEffect();
      } 
      return false;
    }
  }
  
  private static class FuelBonus extends Bonus {
    public FuelBonus() {
      this.type = 1;
      this.positive = RND.nextBoolean();
      this.catchable = false;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[1]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-5.5D);
      this.view.setTranslateY(-8.5D);
    }
    
    public void applyEffect(MainShip ship) {
      if (this.positive) {
        ((Space)this.level).display.addCounter(0, ship.getPilot().getTag());
        ship.addCounter(0);
      } else {
        ((Space)this.level).display.removeCounter(0, ship.getPilot().getTag());
        ship.removeCounter(0);
      } 
    }
  }
  
  private static class ShieldBonus extends Bonus {
    public ShieldBonus() {
      this.type = 2;
      this.positive = RND.nextBoolean();
      this.catchable = false;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[2]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-6.5D);
      this.view.setTranslateY(-6.5D);
    }
    
    public void applyEffect(MainShip ship) {
      if (this.positive) {
        ((Space)this.level).display.addCounter(1, ship.getPilot().getTag());
        ship.addCounter(1);
      } else {
        ((Space)this.level).display.removeCounter(1, ship.getPilot().getTag());
        ship.removeCounter(1);
      } 
    }
  }
  
  private static class LifeBonus extends Bonus {
    public LifeBonus() {
      this.type = 3;
      this.positive = RND.nextBoolean();
      this.catchable = false;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[3]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-8.5D);
      this.view.setTranslateY(-8.5D);
    }
    
    public void applyEffect(MainShip ship) {
      if (this.positive) {
        ((Space)this.level).display.addCounter(2, ship.getPilot().getTag());
        ship.addCounter(2);
      } else {
        ((Space)this.level).display.removeCounter(2, ship.getPilot().getTag());
        ship.removeCounter(2);
      } 
    }
  }
  
  private static class SpeedBonus extends Bonus {
    public SpeedBonus() {
      this.type = 5;
      this.positive = RND.nextBoolean();
      this.catchable = false;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[5]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-10.5D);
      this.view.setTranslateY(-10.5D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.caught = true;
      this.ship.setSpeedMod(this.positive ? 1.5D : 0.5D);
    }
    
    public boolean removeEffect() {
      if (System.nanoTime() - this.startedAt > 5000000000L) {
        this.ship.setSpeedMod(1.0D);
        return super.removeEffect();
      } 
      return false;
    }
  }
  
  private static class ShootSpeedBonus extends Bonus {
    public ShootSpeedBonus() {
      this.type = 6;
      this.positive = RND.nextBoolean();
      this.catchable = false;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[6]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-6.5D);
      this.view.setTranslateY(-12.5D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.caught = true;
      this.ship.setShootingSpeedMod(this.positive ? 2.0D : 0.5D);
    }
    
    public boolean removeEffect() {
      if (System.nanoTime() - this.startedAt > 5000000000L) {
        this.ship.setShootingSpeedMod(1.0D);
        return super.removeEffect();
      } 
      return false;
    }
  }
  
  private static class ChangeSizeBonus extends Bonus {
    public ChangeSizeBonus() {
      this.type = 6;
      this.positive = RND.nextBoolean();
      this.catchable = false;
      this.icon = new Image("pentagon/images/" + Bonus.URLS[7]);
      this.view = new ImageView(this.icon);
      this.view.setTranslateX(-17.0D);
      this.view.setTranslateY(-17.0D);
    }
    
    public void applyEffect(MainShip ship) {
      super.applyEffect(ship);
      this.caught = true;
      this.ship.changeSize(this.positive ? DOUBLE_SIZE : HALF_SIZE);
    }
    
    public boolean removeEffect() {
      if (System.nanoTime() - this.startedAt > 5000000000L) {
        this.ship.regroup();
        return super.removeEffect();
      } 
      return false;
    }
  }
}
