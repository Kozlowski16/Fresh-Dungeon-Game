package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SpriteHandler {
    private HashMap<String, Image> images;
    private HashMap<String, Image> resizedImages;

    public SpriteHandler() {
        File dir = new File(System.getProperty("user.dir") + "\\ICONS");
        File[] directoryListing = dir.listFiles();
        images = new HashMap<>();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                try {
                    images.put(child.getName(), ImageIO.read(child));
                } catch (IOException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
        resizedImages = new HashMap<>();
        resize(100);
        for (String img : images.keySet()) {
            System.out.println(img);
        }
    }

    public void resize(int size) {
        for (String img : images.keySet()) {
            resizedImages.put(img, images.get(img).getScaledInstance(size, size, 0));

        }
    }

    public static void main(String[] args) {
        SpriteHandler icons = new SpriteHandler();
        for (String img : icons.images.keySet()) {
            System.out.println(img);

        }
    }

    public Image getSprite(String sprite) {
        return resizedImages.get(sprite);
    }
}

