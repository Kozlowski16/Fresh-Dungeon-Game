package util;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Vision {
    public char[][] map;
    Queue<PathNode> s;

    public Vision() {
        map = new char[20][20];
        for (int i = 0; i < map.length; i++) {
            map[i][0] = '#';
            map[0][i] = '#';
            map[map.length - 1][i] = '#';
            map[i][map[i].length - 1] = '#';
        }
        drawLine(5, 0, 15);
        drawLine(13, 5, 12);
    }

    public static void main(String[] args) {
        Vision m = new Vision();
        m.print();
        //m.doWork(6,6);
        for (double r = 0; r < 2 * Math.PI; r += Math.PI / 50) {
            m.drawLine(10.5, 10.5, 6, r);
            m.print();
            System.out.println();
        }
        m.map[10][10] = ' ';
        //m.drawLine(10,10,6,-Math.PI/3);
        System.out.println();
        m.print();
    }

    public void drawLine(int y, int x1, int x2) {
        for (int i = x1; i < x2; i++) {
            map[y][i] = '#';
        }
    }

    public void print() {
        for (char[] c : map) {
            System.out.println(Arrays.toString(c));
        }
    }

    public void drawLine(double x, double y, int size, double r) {
        while (size != 0) {
            x += Math.cos(r);
            y += Math.sin(r);
            if (map[(int) y][(int) x] == '#') {
                break;
            }
            map[(int) y][(int) x] = '1';

            size--;
        }
        print();
    }

    public void doWork(int x, int y) {
        s = new LinkedList<PathNode>();
        s.add(new PathNode(new Point(x, y)));
        map[x][y] = '#';
        while (!s.isEmpty()) {
            PathNode node = s.remove();
            if (node.size == 5) {
                continue;
            }
            Point[] neiboors = new Point[4];
            map[node.p.y][node.p.x] = '1';
            map[x][y] = '#';
            print();
            neiboors[0] = new Point(node.p.x + 1, node.p.y);
            neiboors[1] = new Point(node.p.x - 1, node.p.y);
            neiboors[2] = new Point(node.p.x, node.p.y + 1);
            neiboors[3] = new Point(node.p.x, node.p.y - 1);
            for (Point p : neiboors) {
                if (map[p.y][p.x] != '#' && (map[p.y][p.x] != '1')) {
                    s.add(new PathNode(p, node));
                }
            }
        }
    }
}
