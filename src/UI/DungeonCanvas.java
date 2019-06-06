package UI;

import main.Game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class DungeonCanvas extends Canvas {
    private int spriteSize = 50;
    private int cameraX = 0;
    private int cameraY = 0;
    private static final int WIDTH = 640 * 3, HEIGHT = WIDTH / 12 * 9;

    public DungeonCanvas() {
        //this.addMouseListener(new MouseInput(this));
        MouseInput mouse = new MouseInput(this);
        this.addMouseMotionListener(mouse);
        this.addMouseListener(mouse);
        this.addMouseWheelListener(mouse);
        //add Action listeners here
    }

    public void changeSpriteSize(int difference) {
        spriteSize += spriteSize * difference / 10.f;
        spriteSize += difference;
        Game.sprites.resize(spriteSize);
    }

    public void tileClicked(int x, int y) {
        int y1 = (int) Math.floor((cameraY - HEIGHT / 2.f) / spriteSize);
        int x1 = (int) Math.floor((cameraX - WIDTH / 2.f) / spriteSize);

        // y1*spriteSize-cameraY+HEIGHT/2;
        // x1*spriteSize-cameraX+WIDTH/2;
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

        switch (Game.gameState) {
            case MAIN_MENU: {
                g.setColor(Color.WHITE);
                g.drawString("Click to Start",WIDTH/2,HEIGHT/2);
                //g.fillRect(WIDTH/2,HEIGHT/2,400,100);
                break;
            }
            case GAME:

                int y1 = (int) Math.floor((cameraY - HEIGHT / 2.f) / spriteSize);
                int x1 = (int) Math.floor((cameraX - WIDTH / 2.f) / spriteSize);
                for (int y = y1 * spriteSize - cameraY + HEIGHT / 2; y < HEIGHT; y += spriteSize, y1++) {
                    for (int x = x1 * spriteSize - cameraX + WIDTH / 2, x2 = x1; x < WIDTH; x += spriteSize, x2++) {
                        if (x2 < 0 || x2 >= Game.map[0].length || y1 < 0 || y1 >= Game.map.length) {
                            g.drawImage(Game.sprites.getSprite("red.png"), x, y, null);
                        } else {
                            Game.map[y1][x2].render(g, x, y);
                        }
                    }
                }
                break;

            case SETTINGS:

                break;
            case GAME_OVER:

                break;
            default:
        }

        g.dispose();
        bs.show();

    }

}
