//package second.labs;
//
//import java.util.Scanner;
//
//enum TYPE {
//    POINT,
//    CIRCLE
//}
//
//enum DIRECTION {
//    UP,
//    DOWN,
//    LEFT,
//    RIGHT
//}
//
//interface Movable {
//    void moveUp();
//
//    void moveDown();
//
//    void moveRight();
//
//    void moveLeft();
//
//    int getCurrentXPosition();
//
//    int getCurrentYPosition();
//}
//
//class MovingPoint implements Movable {
//
//    private int x, y, xSpeed, ySpeed;
//
//    public MovingPoint(int x, int y, int xSpeed, int ySpeed) {
//        this.x = x;
//        this.y = y;
//        this.xSpeed = xSpeed;
//        this.ySpeed = ySpeed;
//    }
//
//    @Override
//    public void moveUp() {
//
//    }
//
//    @Override
//    public void moveDown() {
//
//    }
//
//    @Override
//    public void moveRight() {
//
//    }
//
//    @Override
//    public void moveLeft() {
//
//    }
//
//    @Override
//    public int getCurrentXPosition() {
//        return x;
//    }
//
//    @Override
//    public int getCurrentYPosition() {
//        return y;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Movable point with coordinates (%d,%d)", x, y);
//    }
//}
//
//class MovingCircle implements Movable {
//
//    private int radius;
//    private MovingPoint center;
//
//    public MovingCircle(int radius, MovingPoint center) {
//        this.radius = radius;
//        this.center = center;
//    }
//
//
//    @Override
//    public void moveUp() {
//
//    }
//
//    @Override
//    public void moveDown() {
//
//    }
//
//    @Override
//    public void moveRight() {
//
//    }
//
//    @Override
//    public void moveLeft() {
//
//    }
//
//    @Override
//    public int getCurrentXPosition() {
//        return 0;
//    }
//
//    @Override
//    public int getCurrentYPosition() {
//        return 0;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Movable circle with center coordinates (%d,%d) and radius %d", getCurrentXPosition(), getCurrentYPosition(), radius);
//    }
//}
//
//class MovablesCollection {
//
//    private Movable[] movable;
//    private static int x_MAX = 0;
//    private static int y_MAX = 0;
//
//    public MovablesCollection(Movable[] movable) {
//        this.movable = movable;
//    }
//
//    public MovablesCollection(int x_MAX, int y_MAX) {
//        this.x_MAX = x_MAX;
//        this.y_MAX = y_MAX;
//    }
//
//    public void addMovableObject(Movable m){
//
//    }
//}
//
//
//
//
//public class CirclesTest {
//
//    public static void main(String[] args) {
//
//        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
//        MovablesCollection collection = new MovablesCollection(100, 100);
//        Scanner sc = new Scanner(System.in);
//        int samples = Integer.parseInt(sc.nextLine());
//        for (int i = 0; i < samples; i++) {
//            String inputLine = sc.nextLine();
//            String[] parts = inputLine.split(" ");
//
//            int x = Integer.parseInt(parts[1]);
//            int y = Integer.parseInt(parts[2]);
//            int xSpeed = Integer.parseInt(parts[3]);
//            int ySpeed = Integer.parseInt(parts[4]);
//
//            if (Integer.parseInt(parts[0]) == 0) { //point
//                collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
//            } else { //circle
//                int radius = Integer.parseInt(parts[5]);
//                collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
//            }
//
//        }
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE POINTS TO THE LEFT");
//        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE CIRCLES DOWN");
//        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
//        System.out.println(collection.toString());
//
//        System.out.println("CHANGE X_MAX AND Y_MAX");
//        MovablesCollection.setxMax(90);
//        MovablesCollection.setyMax(90);
//
//        System.out.println("MOVE POINTS TO THE RIGHT");
//        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE CIRCLES UP");
//        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
//        System.out.println(collection.toString());
//    }
//}
