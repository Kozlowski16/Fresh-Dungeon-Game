package map;

public class Point {
    private int x;
    private int y;
    private int ID1;
    private int ID2;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int id) {
        this.x = x;
        this.y = y;
        ID1 = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return ("(" + x + ", " + y + ")");
    }
}
