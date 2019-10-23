package main;

import UI.DungeonCanvas;
import map.Map;
import util.GameState;
import util.SpriteHandler;

import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {
    //private static final int WIDTH = 640 *2, HEIGHT = WIDTH / 12 * 9;
    private static final int WIDTH = 1000, HEIGHT = 1000;
    private static Game instance;
    public GameState gameState;
    public Map map;
    private SpriteHandler sprites;
    private Thread thread;
    private boolean running;
    private DungeonCanvas canvas;

    private Game() {
        sprites = new SpriteHandler();
        gameState = GameState.GAME;
        map = new Map();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
            instance.setupUI();
        }
        return instance;
    }

    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.start();
    }

    public SpriteHandler getSpriteHandler() {
        return sprites;
    }

    private void setupUI() {
        JFrame frame = new JFrame("Fresh Start");
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        canvas = new DungeonCanvas(WIDTH, HEIGHT);
        frame.add(canvas);
        frame.setVisible(true);
    }

    private synchronized void start() {

        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private synchronized void stop() {
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
        int ticks = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                //System.out.println(delta);
                //tick();
                ticks++;
                delta--;
            }
            if (running) {
                //System.out.println("######");
                canvas.render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                System.out.println("Ticks: " + ticks);
                ticks = 0;
                frames = 0;
            }
        }
        stop();
    }
}
