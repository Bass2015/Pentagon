package pentagon.game;

public interface PentagonConstants {
  public static final int SCREEN_WIDTH = 1300;
  
  public static final int SCREEN_HEIGHT = 700;
  
  public static final int LIMIT_LEFT = 27;
  
  public static final int LIMIT_RIGHT = 1270;
  
  public static final int LIMIT_TOP = 30;
  
  public static final int LIMIT_BOTTOM = 677;
  
  public static final String DIR = "pentagon/images/";
  
  public static final int L = -1;
  
  public static final int R = 1;
  
  public static final int FOR = 1;
  
  public static final int BACK = -1;
  
  public static final int FRAME_TIME = 40;
  
  public static final double SHIP_SPEED = 5.0D;
  
  public static final long SHOOTING_SPEED = 800000000L;
  
  public static final double GOLD_NUMBER = (1.0D + Math.sqrt(5.0D)) / 2.0D;
  
  public static final int BULLET = 0;
  
  public static final int BONUS = 1;
  
  public static final int ASTEROID = 2;
  
  public static final int FUEL = 0;
  
  public static final int SHIELD = 1;
  
  public static final int LIFE = 2;
  
  public static final int DEFAULT_SHOOT = 0;
  
  public static final int PENTA_SHOOT = 1;
  
  public static final int CHARGE_SHOOT = 2;
  
  public static final int EXPLOSIVE_SHOOT = 3;
  
  public static final double[] ANGLES = new double[] { 162.0D, 234.0D, 306.0D, 18.0D };
  
  public static final double[] MINI_ANGLES = new double[] { 90.0D, 54.0D, 18.0D, 342.0D, 306.0D, 270.0D, 234.0D, 198.0D, 162.0D, 126.0D };
  
  public static final double[] MINI_ORIENTATIONS = new double[] { 0.0D, 144.0D, -72.0D, 72.0D, -144.0D, 0.0D, 144.0D, -72.0D, 72.0D, -144.0D };
  
  public static final double SHIP_SIDE = 35.0D;
  
  public static final double TALL_TRI_BASE = 35.0D / Math.pow(GOLD_NUMBER, 2.0D);
  
  public static final double TALL_TRI_HEIGHT = 35.0D * Math.sin(Math.toRadians(36.0D));
  
  public static final double SHORT_TRI_HEIGHT = 17.5D * Math.tan(Math.toRadians(36.0D));
  
  public static final double RADIUM = -17.5D / Math.cos(Math.toRadians(54.0D));
  
  public static final double MINI_RADIUM = Math.sin(Math.toDegrees(144.0D)) * -RADIUM / 2.0D;
  
  public static final double[] POINTS = new double[] { 0.0D, RADIUM, RADIUM * 
      Math.cos(Math.toRadians(ANGLES[0])), RADIUM * 
      Math.sin(Math.toRadians(ANGLES[0])), RADIUM * 
      Math.cos(Math.toRadians(ANGLES[1])), RADIUM * 
      Math.sin(Math.toRadians(ANGLES[1])), RADIUM * 
      Math.cos(Math.toRadians(ANGLES[2])), RADIUM * 
      Math.sin(Math.toRadians(ANGLES[2])), RADIUM * 
      Math.cos(Math.toRadians(ANGLES[3])), RADIUM * 
      Math.sin(Math.toRadians(ANGLES[3])) };
  
  public static final double[] MINISHIP_POINTS = new double[] { 0.0D, MINI_RADIUM, MINI_RADIUM * 
      Math.cos(Math.toRadians(ANGLES[0])), MINI_RADIUM * 
      Math.sin(Math.toRadians(ANGLES[0])), MINI_RADIUM * 
      Math.cos(Math.toRadians(ANGLES[1])), MINI_RADIUM * 
      Math.sin(Math.toRadians(ANGLES[1])), MINI_RADIUM * 
      Math.cos(Math.toRadians(ANGLES[2])), MINI_RADIUM * 
      Math.sin(Math.toRadians(ANGLES[2])), MINI_RADIUM * 
      Math.cos(Math.toRadians(ANGLES[3])), MINI_RADIUM * 
      Math.sin(Math.toRadians(ANGLES[3])) };
  
  public static final double[] TALL_TRI_POINTS = new double[] { 0.0D, -TALL_TRI_HEIGHT / 2.0D, -TALL_TRI_BASE / 2.0D, TALL_TRI_HEIGHT / 2.0D, TALL_TRI_BASE / 2.0D, TALL_TRI_HEIGHT / 2.0D };
  
  public static final double[] SHORT_TRI_POINTS = new double[] { 0.0D, -SHORT_TRI_HEIGHT / 2.0D, -17.5D, SHORT_TRI_HEIGHT / 2.0D, 17.5D, SHORT_TRI_HEIGHT / 2.0D };
  
  public static final double BONUS_SIZE = 17.0D;
  
  public static final double[] BONUS_POINTS = new double[] { -17.0D, 0.0D, 0.0D, 17.0D, 17.0D, 0.0D, 0.0D, -17.0D };
  
  public static final long BONUS_TIME = 5000000000L;
  
  public static final int SCATTER_BONUS = 0;
  
  public static final int FUEL_BONUS = 1;
  
  public static final int SHIELD_BONUS = 2;
  
  public static final int LIFE_BONUS = 3;
  
  public static final int PHANTOM_BONUS = 4;
  
  public static final int SPEED_BONUS = 5;
  
  public static final int SHOOT_SPEED_BONUS = 6;
  
  public static final int SIZE_BONUS = 7;
  
  public static final int BERSERKR_BONUS = 8;
  
  public static final int TELEPORT_BONUS = 9;
  
  public static final int PENTA_SHOOT_BONUS = 10;
  
  public static final int CHARGE_SHOOT_BONUS = 11;
  
  public static final int EXPLOSIVE_SHOOT_BONUS = 12;
  
  public static final double SMALL = 17.0D;
  
  public static final double MEDIUM = 34.0D;
  
  public static final double LARGE = 68.0D;
  
  public static final double XLARGE = 136.0D;
  
  public static final double[] AST_ANGLES = new double[] { 40.0D, 80.0D, 120.0D, 160.0D, 200.0D, 240.0D, 280.0D, 320.0D };
  
  public static final double BULLET_SIZE = 2.0D;
  
  public static final double[] BULLET_ANGLES = new double[] { 
      18.0D, 36.0D, 54.0D, 72.0D, 90.0D, 108.0D, 126.0D, 144.0D, 162.0D, 180.0D, 
      198.0D, 216.0D, 234.0D, 252.0D, 270.0D, 288.0D, 306.0D, 324.0D, 342.0D };
  
  public static final double[] BULLET_POINTS = new double[] { 
      2.0D, 0.0D, 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[0])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[0])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[1])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[1])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[2])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[2])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[3])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[3])), 
      2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[4])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[4])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[5])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[5])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[6])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[6])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[7])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[7])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[8])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[8])), 
      2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[9])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[9])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[10])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[10])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[11])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[11])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[12])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[12])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[13])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[13])), 
      2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[14])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[14])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[15])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[15])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[16])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[16])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[17])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[17])), 2.0D * 
      Math.cos(Math.toRadians(BULLET_ANGLES[18])), 2.0D * 
      Math.sin(Math.toRadians(BULLET_ANGLES[18])) };
  
  public static final double DEFAULT_MODE = RADIUM + TALL_TRI_HEIGHT / 1.76D;
  
  public static final double BERSERKR_MODE = -RADIUM - TALL_TRI_HEIGHT / 2.0D;
  
  public static final double DOUBLE_SIZE = RADIUM - TALL_TRI_HEIGHT / 2.0D;
  
  public static final double HALF_SIZE = RADIUM + TALL_TRI_HEIGHT;
}
