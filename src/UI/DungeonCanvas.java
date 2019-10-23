package UI;

import main.Game;
import map.Tile;
import util.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class DungeonCanvas extends Canvas {
    private int WIDTH;
    private int HEIGHT;
    private int spriteSize = 100;
    private int cameraX = WIDTH / 2 + 25;
    private int cameraY = HEIGHT / 2 + 25;
    private SpriteHandler sprites;

    public DungeonCanvas(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        //this.addMouseListener(new MouseInput(this));
        MouseInput mouse = new MouseInput(this);
        this.addMouseMotionListener(mouse);
        this.addMouseListener(mouse);
        this.addMouseWheelListener(mouse);
        sprites = Game.getInstance().getSpriteHandler();
        //add Action listeners here
    }
    public DungeonCanvas() {
        this(1000, 1000);
    }

    public void changeSpriteSize(int difference) {
        spriteSize += spriteSize * difference / 10.f;
        spriteSize += difference;
        sprites.resize(spriteSize);
    }

    public void tileClicked(int x, int y) {
        int renderY = (int) Math.floor((cameraY - HEIGHT / 2.f) / spriteSize);
        int renderX = (int) Math.floor((cameraX - WIDTH / 2.f) / spriteSize);
        System.out.println("#######################");
        System.out.println("Rendered X: " + renderX);
        System.out.println("Rendered Y: " + renderY);
        int offsetX = renderX * spriteSize - cameraX + WIDTH / 2;
        int offsetY = renderY * spriteSize - cameraY + HEIGHT / 2;
        System.out.println(offsetX);
        System.out.println(offsetY);
        int clickedX = (x - offsetX) / spriteSize + renderX;
        int clickedY = (y - offsetY) / spriteSize + renderY;

        System.out.println("clicked X: " + clickedX);
        System.out.println("clicked Y: " + clickedY);

        //System.out.println(x);

    }

    public void changeCamera(int x, int y) {
        cameraX += x;
        cameraY += y;
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH * 2, HEIGHT * 2);

        switch (Game.getInstance().gameState) {
            case MAIN_MENU: {
                g.setColor(Color.WHITE);
                g.drawString("Click to Start", WIDTH / 2, HEIGHT / 2);
                //g.fillRect(WIDTH/2,HEIGHT/2,400,100);
                break;
            }
            case GAME:
                Tile[][] map = Game.getInstance().map.getTiles();
                int renderY = (int) Math.floor((cameraY - HEIGHT / 2.f) / spriteSize);
                int renderX = (int) Math.floor((cameraX - WIDTH / 2.f) / spriteSize);
                for (int y = renderY * spriteSize - cameraY + HEIGHT / 2; y < HEIGHT; y += spriteSize, renderY++) {
                    for (int x = renderX * spriteSize - cameraX + WIDTH / 2, x2 = renderX; x < WIDTH; x += spriteSize, x2++) {
                        if (x2 < 0 || x2 >= map[0].length || renderY < 0 || renderY >= map.length) {
                            g.drawImage(sprites.getSprite("red.png"), x, y, null);
                        } else {
                            map[renderY][x2].render(g, x, y);
                        }
                    }
                }
                break;

            case SETTINGS:
                //TODO
                break;
            case GAME_OVER:
                //TODO
                break;
            default:
        }
        int renderY = (int) Math.floor((cameraY - HEIGHT / 2.f) / spriteSize);
        int renderX = (int) Math.floor((cameraX - WIDTH / 2.f) / spriteSize);
        g.setColor(Color.CYAN);
        for (int x = renderX * spriteSize - cameraX + WIDTH / 2; x < WIDTH; x += spriteSize) {
            g.drawLine(x, HEIGHT, x, 0);
        }
        for (int y = renderY * spriteSize - cameraY + HEIGHT / 2; y < HEIGHT; y += spriteSize) {
            g.drawLine(0, y, WIDTH, y);
        }
        g.dispose();
        bs.show();

    }

}
