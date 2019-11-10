package util;

import Entities.Entity;
import map.Map;

import java.awt.Point;
import java.util.*;

public class PathFinder {
    public char[][] map;
    Queue<PathNode> q;

    public PathFinder() {
        map = new char[20][20];
        for (int i = 0; i < map.length; i++) {
            map[i][0] = '#';
            map[0][i] = '#';
            map[map.length - 1][i] = '#';
            map[i][map[i].length - 1] = '#';
        }
        drawLine(5, 0, 15);
    }

    public static void generateAlternatePath(PathNode p, int x, int y, int[][] map) {
        generatePath(new Point(x, y), p.next.p, map);
    }

    public static PathNode generatePath(Point start, Point target, int[][] map) {
        Queue<PathNode> q;
        q = new LinkedList<>();
        q.add(new PathNode(start));
        while (!q.isEmpty()) {
            PathNode node = q.remove();
            if (map[node.p.y][node.p.x] == -1) {
                continue;
            }
            if (node.p.equals(target)) {
                return PathNode.reverse(node).next;
            }

            map[node.p.y][node.p.x] = -1;
            Point[] nighboors = new Point[4];

            nighboors[0] = new Point(node.p.x + 1, node.p.y);
            nighboors[1] = new Point(node.p.x - 1, node.p.y);
            nighboors[2] = new Point(node.p.x, node.p.y + 1);
            nighboors[3] = new Point(node.p.x, node.p.y - 1);

            //RandomizeArray(nighboors);
            Collections.shuffle(Arrays.asList(nighboors));
            for (Point p : nighboors) {

                if (map[p.y][p.x] > node.size) {
                    q.add(new PathNode(p, node));
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        // PathFinder m = new PathFinder();
        Map map = new Map();
        //Floor f = new Floor();
        //m.map = f.map;
        // m.print();
        Point target = new Point(map.start.getX(), map.start.getY());
        //LinkedList<> baddies =  map.enemies;
        /*
        for(int y=0;y<m.map.length;y++){
            for(int x=0;x<m.map[y].length;x++){
                if(m.map[y][x] == '1'){
                    m.map[y][x] = '\u0000';
                    baddies.add(new Point(x,y));
                }
            }
        }
        */


        int[][] test = map.getIntMap();
        System.out.println();
        for (int[] c : test) {
            System.out.println(Arrays.toString(c));
        }
        System.out.println();
        int[][] testMap = new int[30][30];
        for (int y = 0; y < testMap.length; y++) {
            for (int x = 0; x < testMap.length; x++) {
                testMap[y][x] = 99;
                //System.out.println("did");
            }
        }
        for (int i = 0; i < testMap.length; i++) {
            testMap[i][0] = -1;
            testMap[0][i] = -1;
            testMap[testMap.length - 1][i] = -1;
            testMap[i][testMap[i].length - 1] = -1;
        }
        for (int[] c : testMap) {
            System.out.println(Arrays.toString(c));
        }
        System.out.println("size: " + generatePath(new Point(2, 2), new Point(7, 7), testMap).size);
        Scanner sc = new Scanner(System.in);
        sc.next();
        long time = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            long time2 = System.nanoTime();
            for (Entity p : map.enemies) {

                //System.out.println("########################");
                //System.out.println(p.x + " " + p.y);
                //long time2 = System.nanoTime();
                generatePath(new Point(p.x, p.y), target, map.getIntMap());
                //System.out.println("size: " + generatePath(new Point(p.x,p.y), target,map.getIntMap()).size);

            }
            System.out.println("Time Taken: " + ((System.nanoTime() - time2) / 1000000));
            //System.gc();
        }
        System.out.println("Time Taken: " + ((System.nanoTime() - time) / 1000000));
        time = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            map.getIntMap();
        }
        System.out.println("Time Taken: " + ((System.nanoTime() - time) / 1000000));

        // System.out.println((char) 400);
        //System.out.println((int) m.map[0][0]);
        // System.out.println(PathNode.reverse(m.pathFind(new Point(10, 10), new Point(1, 1))));
        // m.print();
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

    public PathNode pathFind(Point start, Point target) {
        q = new LinkedList<PathNode>();
        q.add(new PathNode(start));

        while (!q.isEmpty()) {
            PathNode node = q.remove();
            if (node.p.equals(target)) {
                return node;
            }
            map[node.p.y][node.p.x] = (char) (node.size + '0');
            Point[] neiboors = new Point[4];
            neiboors[0] = new Point(node.p.x + 1, node.p.y);
            neiboors[1] = new Point(node.p.x - 1, node.p.y);
            neiboors[2] = new Point(node.p.x, node.p.y + 1);
            neiboors[3] = new Point(node.p.x, node.p.y - 1);
            for (Point p : neiboors) {
                if (map[p.y][p.x] == '\u0000' || (map[p.y][p.x] != '#' && map[p.y][p.x] > node.size + '0')) {
                    q.add(new PathNode(p, node));
                }
            }

        }
        return null;
    }
}
