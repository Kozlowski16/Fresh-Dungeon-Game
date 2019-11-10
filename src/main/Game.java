package main;

import UI.DungeonCanvas;
import Entities.Entity;
import Entities.Player;
import map.Map;
import util.GameState;
import util.Logger;
import util.PathFinder;
import util.SpriteHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Game implements Runnable {
    //private static final int WIDTH = 640 *2, HEIGHT = WIDTH / 12 * 9;
    private static final int WIDTH = 1000, HEIGHT = 1000;
    private static Game instance;
    public GameState gameState;
    public Map map;
    public Player player;
    private SpriteHandler sprites;
    private Thread thread;
    private Thread turnThread;
    private boolean running;
    private DungeonCanvas canvas;

    private Game() {
        sprites = new SpriteHandler();
        gameState = GameState.GAME;
        map = new Map();
        //turnThread = new Thread(this::progressTurn);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
            instance.setupUI();
        }
        return instance;
    }

    public static void main(String[] args) {
        //System.setProperty("sun.java2d.opengl", "true");
        Game game = Game.getInstance();
        game.start();
        System.out.println(Calendar.getInstance().getTime().toString());
        Logger.initialize();
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
        player = new Player(map.start.getX(), map.start.getY());
        System.out.println(map.start);
        System.out.println(map.getTiles()[map.start.getY()][map.start.getX()] == null);
        map.initPlayer(player);
        thread = new Thread(this);
        thread.start();
        running = true;
        handleVision();
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
                //System.out.println("Ticks: " + ticks);
                ticks = 0;
                frames = 0;
            }
        }
        stop();
    }

    public void handleClick(int x, int y, int button) {
        try {
            switch (button) {
                case (0):
                    System.out.println("How did you click no buttons?");
                    break;
                case (1):
                    if (map.getTile(x, y).visible) {

                    } else if (map.getTile(x, y).discovered) {
                        if (!map.getTile(x, y).isSolid()) {
                            movePlayer(x, y);
                            if (map.getTile(x, y).gameObject != null) {
                                //player.interact(map.getTile(x, y).gameObject);
                            }
                        }
                    }
                    if (map.getTile(x, y).getEntity() != null) {
                        player.attack(map.getTile(x, y).getEntity());
                        progressTurn();
                    }
                    if (!map.getTile(x, y).isSolid() /*&& map.getTile(x,y).isVisable()*/) {
                        movePlayer(x, y);
                    }
                    break;
                case (2):
                    break;
                case (3):
                    break;
                default:
                    System.out.println("How did you end up in the default switch of handle click?: " + button);
            }
            for (Entity b : map.enemies) {
                //System.out.println(b);
            }
        } catch (Exception e) {
            Logger.log(e);
        }

    }

    public void movePlayer(int x, int y) {
        player.path = PathFinder.generatePath(new Point(player.x, player.y), new Point(x, y), map.getIntMap());
        try {
            /*
            Thread turn =  new Thread(){
                boolean nextTurn = true;
                @Override
                public void run() {
                    try {
                        while(progressTurn()){
                        Thread.sleep(109);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //super.run();
                }
            };
            turn.start();

             */
            //turnThread.start();
            //Thread turn2 =
            //turn2.start();


            //while(progressTurn()){
            // TimeUnit.MILLISECONDS.sleep(100);
            //}
            progressTurn();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }


    }

    private void progressTurn(Entity attackedEntity, int x, int y) {
        boolean nextTurn = true;
        while (nextTurn) {
            if (player.path == null) {
                if (player.x != x || player.y != y) {
                    player.path = PathFinder.generatePath(new Point(player.x, player.y), new Point(x, y), map.getIntMap());
                    if (player.path == null) {
                        System.out.println("Unable to find player path");
                        return;
                    }
                }

            }
            if (player.path != null) {
                if (map.getTile(player.path.p).getEntity() != null) {
                    player.attack(map.getTile(player.path.p).getEntity());
                } else {
                    int active = map.getActiveBad();
                    //System.out.println("prev:" + active);
                    map.moveEntity(player, player.path.p.x, player.path.p.y);
                    for (Entity baddie : map.enemies) {
                        baddie.path = null;
                    }
                    handleVision();
                    //System.out.println("prev:" + active + " now: " +  map.getActiveBad() );
                    if (active != map.getActiveBad()) {
                        nextTurn = false;
                    }
                }
                player.path = player.path.next;
            }
            if (player.path == null) {
                nextTurn = false;
            }
            moveBadPeople();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                //Thread.sleep(109);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void moveBadPeople() {
        ArrayList<Entity> rejects = new ArrayList<Entity>();
        ArrayList<Entity> bad = map.enemies;
        int last = -1;
        while (rejects.size() != last) {
            last = rejects.size();
            for (int i = 0; i < bad.size(); i++) {
                Entity baddie = bad.get(i);
                if (!baddie.active) {
                    continue;
                }
                if (baddie.path == null) {
                    baddie.path = PathFinder.generatePath(new Point(baddie.x, baddie.y), new Point(player.x, player.y), map.getIntMap());
                }
                if (baddie.path == null) {
                    continue;
                }
                //System.out.println(baddie.path);
                if (map.getTile(baddie.path.p.x, baddie.path.p.y).isSolid()) {
                    System.out.println("Rejecting Path");
                    if (bad != rejects) {
                        rejects.add(baddie);
                    }

                } else {

                    if (bad == rejects) {
                        rejects.remove(i);
                        i--;
                    }
                    System.out.println(baddie.path);
                    if (baddie.path.p.x == player.x && baddie.path.p.y == player.y) {
                        baddie.attack(player);
                        System.out.println(baddie + " attacking " + player);
                    } else {
                        System.out.println(baddie + " Moving to " + baddie.path.p);
                        map.moveEntity(baddie, baddie.path.p.x, baddie.path.p.y);
                    }
                    baddie.path = baddie.path.next;
                }
            }
            bad = rejects;
        }
/*
        while(rejects.size() != last){
            last = rejects.size();
            for (int i = 0; i < rejects.size() ; i++) {
                BasicEnemy baddie = rejects.get(i);
                if(baddie.path == null){
                    baddie.path =  PathFinder.generatePath(new Point(baddie.x,baddie.y),new Point(player.x,player.y), map.getIntMap());
                    baddie.path = baddie.path.next;
                }
                if(!map.getTile(baddie.path.p.x,baddie.path.p.y).isSolid()){
                    rejects.remove(i);
                    i--;
                    map.moveEntity(baddie,baddie.path.p.x,baddie.path.p.y);
                    baddie.path = baddie.path.next;
                }
            }
        }
        */
    }

    public void handleVision() {
        for (double r = 0; r < 2 * Math.PI; r += Math.PI / 40) {
            drawLine(player.x + 0.5, player.y + 0.5, 6, r);
        }
    }

    public void drawLine(double x, double y, int size, double r) {
        while (size != 0) {
            x += Math.cos(r);
            y += Math.sin(r);
            if (map.getTile((int) x, (int) y).isSolid() && map.getTile((int) x, (int) y).getEntity() == null) {
                break;
            }
            map.getTile((int) x, (int) y).discover();
            size--;
        }
    }

}
