package Model;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Colors {
    public static List<Color> colorList;

    public static List<Color> colors = new ArrayList<>();
    static{
        colors.add(new Color(255, 0, 0)); // Czerwony
        colors.add(new Color(0, 255, 0)); // Zielony
        colors.add(new Color(0, 0, 255)); // Niebieski
        colors.add(new Color(255, 255, 0)); // Żółty
        colors.add(new Color(255, 0, 255)); // Magenta
        colors.add(new Color(0, 255, 255)); // Cyjan
        colors.add(new Color(255, 128, 0)); // Pomarańczowy
        colors.add(new Color(128, 0, 255)); // Fioletowy
        colors.add(new Color(255, 204, 204)); // Różowy
        colors.add(new Color(153, 255, 51)); // Zielony neon
        colors.add(new Color(255, 153, 51)); // Pomarańczowy neon
        colors.add(new Color(51, 255, 255)); // Błękitny neon
    }

    public static Color getRandomColor(){
        Random random = new Random();
        int randomNumber = random.nextInt(colors.size());
        return colors.get(randomNumber);
    }
}
