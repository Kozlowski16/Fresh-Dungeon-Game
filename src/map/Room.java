package map;

import util.PathNode;

import java.util.Arrays;
import java.util.Random;

public class Room {
    private char[][] room;

    Room(int a) {
        Random r = new Random(1);
        int x = r.nextInt(6) + 6;
        int y = r.nextInt(6) + 6;

        room = new char[y][x];
        for (char[] f : room)
            Arrays.fill(f, ' ');
        createWalls();
        room[y - (r.nextInt(3) + 3)][x - (r.nextInt(3) + 3)] = 's';
    }

    Room() {
        Random r = new Random(1);
        int x = r.nextInt(6) + 6;
        int y = r.nextInt(6) + 6;
        room = new char[y][x];
        for (char[] f : room)
            Arrays.fill(f, ' ');
        createWalls();
        createEnimies(r.nextInt(20));
        createPickUp(r.nextInt(1));
        createPickUp(1);
    }

    private void createPickUp(int count) {
        if (count == 0) {
            return;
        }
        Random r = new Random(1);
        int[] array = new int[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt((room[0].length - 2) * (room.length - 2));
        }
        Arrays.sort(array);

        for (int y = 1, i = 0, j = 0; y < room.length - 1; y++) {
            for (int x = 1; x < room[0].length - 1; x++, i++) {
                if (i == array[j]) {
                    room[y][x] = '2';
                    j++;
                    if (j == array.length) {
                        y = 999;
                        break;
                    }
                }
            }
        }
    }

    Room(int width, int height) {
        room = new char[height][width];
        for (char[] f : room)
            Arrays.fill(f, ' ');
        createWalls();
    }

    private void createWalls() {
        for (int x = 0; x < room[0].length; x++) {
            room[0][x] = '#';
            room[room.length - 1][x] = '#';
        }
        for (int y = 0; y < room.length; y++) {
            room[y][0] = '#';
            room[y][room[0].length - 1] = '#';
        }
    }

    private void createEnimies(int count) {
        if (count == 0) {
            return;
        }
        Random r = new Random(1);
        int[] array = new int[count];
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt((room[0].length - 2) * (room.length - 2));
        }
        Arrays.sort(array);

        for (int y = 1, i = 0, j = 0; y < room.length - 1; y++) {
            for (int x = 1; x < room[0].length - 1; x++, i++) {
                if (i == array[j]) {
                    room[y][x] = '1';
                    j++;
                    if (j == array.length) {
                        y = 999;
                        break;
                    }
                }
            }
        }
    }

    public int getWidth() {
        return room[0].length;
    }

    public int getHeight() {
        return room.length;
    }

    public char[][] getRoom() {
        return room;
    }

    public Point[] getPoints() {
        Point[] p = new Point[(getWidth() + getHeight()) * 2 - 8];


        return p;
    }
}
