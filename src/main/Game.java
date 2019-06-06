package main;

import UI.DungeonCanvas;
import map.Floor;
import map.tile;
import util.GameState;
import util.SpriteHandler;

import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {

    public static GameState gameState;
    public static tile[][] map;
    public static SpriteHandler sprites = new SpriteHandler();

    public static final int WIDTH = 640 * 3, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running;
    private DungeonCanvas canvas;

    public static void main(String[] args) {
        new Game();
    }


    private Game() {
        gameState = GameState.GAME;
        JFrame frame = new JFrame("Fresh Start");
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        canvas = new DungeonCanvas();
        frame.add(canvas);
        frame.setVisible(true);
        this.start();
        Floor floor = new Floor();
        floor.printFloor();
        map = floor.getMap();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        canvas.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                //tick();
                delta--;
            }
            if (running)
                canvas.render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
}
