package pentagon.game;

import com.sun.javafx.geom.Line2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import pentagon.elements.GameObject;

public class Utils {
  public static Point2D miniShipPosition(double angle, double distanceFromCenter) {
    double x = distanceFromCenter * Math.cos(Math.toRadians(angle));
    double y = distanceFromCenter * Math.sin(Math.toRadians(angle));
    return new Point2D(x, y);
  }
  
  public static final Point2D[] doubleToPointArray(double[] points) {
    Point2D[] array = new Point2D[points.length / 2];
    for (int i = 0; i < array.length; i++)
      array[i] = new Point2D(points[i * 2], points[i * 2 + 1]); 
    return array;
  }
  
  public static final boolean polygonTouchesPolygon(GameObject obj1, GameObject obj2) {
    boolean touches = false;
    for (Point2D point : obj2.getPoints()) {
      if (obj1.contains(obj1.sceneToLocal(obj2.localToScene(point))))
        return true; 
    } 
    for (Point2D point : obj1.getPoints()) {
      if (obj2.contains(obj2.sceneToLocal(obj1.localToScene(point))))
        return true; 
    } 
    return false;
  }
}
