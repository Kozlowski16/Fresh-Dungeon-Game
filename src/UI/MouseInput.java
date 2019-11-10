package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInput extends MouseAdapter {
    private DungeonCanvas canvas;

    public MouseInput(DungeonCanvas d) {
        canvas = d;
    }

    private int lastX = 0;
    private int lastY = 0;
    private int lastX2 = 0;
    private int lastY2 = 0;

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
        lastX2 = lastX;
        lastY2 = lastY;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Math.abs(lastX2 - e.getX()) < 20 && Math.abs(lastY2 - e.getY()) < 20) {
            canvas.tileClicked(lastX, lastY, (e.getButton()));
            //System.out.println(e.getButton());
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        canvas.changeCamera(lastX - e.getX(), lastY - e.getY());
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        canvas.changeSpriteSize(notches);
        //System.out.println(notches);
    }

}
