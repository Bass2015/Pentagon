package pentagon.elements;

import com.sun.javafx.geom.Line2D;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.shape.Shape;
import pentagon.game.PentagonConstants;
import pentagon.game.Utils;
import pentagon.levels.Level;

public abstract class GameObject extends Parent implements PentagonConstants {
  protected Level level;
  
  protected double size;
  
  protected double orientation;
  
  protected boolean removable;
  
  protected Shape body;
  
  protected Point2D[] points;
  
//  protected Line2D[] sides;
  
  public void setLevel(Level level) {
    this.level = level;
  }
//  
//  @Deprecated
//  protected final void setSides() {
//    this.sides = new Line2D[this.points.length];
//    for (int i = 0; i < this.sides.length; i++) {
//      float x1 = (float)this.points[i].getX();
//      float y1 = (float)this.points[i].getY();
//      float x2 = (float)this.points[(i == this.points.length - 1) ? 0 : (i + 1)].getX();
//      float y2 = (float)this.points[(i == this.points.length - 1) ? 0 : (i + 1)].getY();
//      this.sides[i] = new Line2D(x1, y1, x2, y2);
//    } 
//  }
  
  public final void setRemovable(boolean removable) {
    this.removable = removable;
  }
//  
//  @Deprecated
//  public final Line2D[] getSides() {
//    return this.sides;
//  }
//  
  public final Point2D[] getPoints() {
    return this.points;
  }
  
  public final Level getLevel() {
    return this.level;
  }
  
  public final Shape getBody() {
    return this.body;
  }
  
  public final double getSize() {
    return this.size;
  }
  
  public final boolean isRemovable() {
    return this.removable;
  }
  
  protected final void move(double x, double y) {
    setLayoutX(x);
    setLayoutY(y);
  }
  
  protected final void rotate(double value) {
    setRotate(value);
    this.orientation = getRotate();
  }
  
  public final boolean touches(GameObject otherObj) {
    return Utils.polygonTouchesPolygon(this, otherObj);
  }
}
