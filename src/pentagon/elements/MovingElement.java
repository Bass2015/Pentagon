package pentagon.elements;

import java.util.Random;

public interface MovingElement {
  public static final Random RND = new Random();
  
  public static final long CHANGE_ANGLE_TIME = 500000000L;
  
  void move();
}
