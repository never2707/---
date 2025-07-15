import java.awt.Image;
import java.io.File;
import java.nio.file.Path;

import javax.swing.ImageIcon;

public class Images {
    String parent = Path.of(new File("images.java").getAbsolutePath()).getParent().getParent().toString() + "/images/"; 

    ImageIcon d0 = new ImageIcon(parent + "d0.png");
    ImageIcon d1 = new ImageIcon(parent + "d1.png");
    ImageIcon d2 = new ImageIcon(parent + "d2.png");
    ImageIcon d3 = new ImageIcon(parent + "d3.png");
    ImageIcon d4 = new ImageIcon(parent + "d4.png");
    ImageIcon d5 = new ImageIcon(parent + "d5.png");
    ImageIcon d6 = new ImageIcon(parent + "d6.png");
    ImageIcon d7 = new ImageIcon(parent + "d7.png");
    ImageIcon d8 = new ImageIcon(parent + "d8.png");
    ImageIcon d9 = new ImageIcon(parent + "d9.png");
    ImageIcon n1 = new ImageIcon(parent + "num1.png");
    ImageIcon n2 = new ImageIcon(parent + "num2.png");
    ImageIcon n3 = new ImageIcon(parent + "num3.png");
    ImageIcon n4 = new ImageIcon(parent + "num4.png");
    ImageIcon n5 = new ImageIcon(parent + "num5.png");
    ImageIcon n6 = new ImageIcon(parent + "num6.png");
    ImageIcon n7 = new ImageIcon(parent + "num7.png");
    ImageIcon n8 = new ImageIcon(parent + "num8.png");
    ImageIcon bomb = new ImageIcon(parent + "bomb.png");
    ImageIcon bomb_red = new ImageIcon(parent + "bomb_red.png");
    ImageIcon face = new ImageIcon(parent + "face.png");
    ImageIcon face_press = new ImageIcon(parent + "face_press.png");
    ImageIcon face_lose = new ImageIcon(parent + "face_lose.png");
    ImageIcon face_win = new ImageIcon(parent + "face_win.png");
    ImageIcon background = new ImageIcon(parent + "numsbackground.png");
    ImageIcon background_easy = new ImageIcon(parent + "background_easy.png");
    ImageIcon background_normal = new ImageIcon(parent + "background_normal.png");
    ImageIcon background_hard = new ImageIcon(parent + "background_hard.png");
    ImageIcon space = new ImageIcon(parent + "button.png");
    ImageIcon space_press = new ImageIcon(parent + "pressed.png");
    ImageIcon flag = new ImageIcon(parent+ "flag.png");

    public static ImageIcon getIcon(ImageIcon icon, int width, int height){
        Image image;
        image = icon.getImage().getScaledInstance(width, height, 1);
        icon = new ImageIcon(image);
        return icon;
    }
}
