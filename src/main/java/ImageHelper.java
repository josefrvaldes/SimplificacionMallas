import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by neiklotv on 1/6/17.
 */
public class ImageHelper {

    public static List<NodoInt> getNodosDesdeImagen(String nombreArchivo, int porcentajeAleatorio) throws IOException {

        List<NodoInt> nodos = new ArrayList<>();
        BufferedImage img;
        File f;

        f = new File(nombreArchivo);
        img = ImageIO.read(f);

        int width = img.getWidth();
        int height = img.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 100 + 1);
                if (randomNum < porcentajeAleatorio) {
                    Color color = new Color(img.getRGB(i, j));
                    //String aux = "alpha " + color.getAlpha() + ";r " + color.getRed() + ";g " + color.getGreen() + ";b " + color.getBlue();
                    //int a = color.getAlpha();
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    if (r < 30 && g < 30 && b < 30) {
                        NodoInt nodo = new NodoInt(i, j);
                        nodos.add(nodo);
                    }
                }
            }
        }
        return nodos;

    }

}
