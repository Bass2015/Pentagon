package pentagon.game;

import javafx.scene.paint.Color;
import pentagon.elements.MainShip;

public class Player {
  private MainShip ship;
  
  private Color[] colors;
  
  private String name;
  
  private int tag;
  
  public Player(int tag, Color color1, Color color2) {
    this.colors = new Color[2];
    this.colors[0] = color1;
    this.colors[1] = color2;
    this.ship = new MainShip(this);
    this.tag = tag;
  }
  
  public Color[] getColors() {
    return this.colors;
  }
  
  public MainShip getShip() {
    return this.ship;
  }
  
  public int getTag() {
    return this.tag;
  }
}
