package datasource;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ResourceHelper {
    private ResourceHelper() {
        throw new IllegalStateException("Do not instantiate a utility class");
    }

    public static InputStream getAsStream(String resourceName) {
        return ResourceHelper.class.getResourceAsStream(resourceName);
    }

    public static ImageIcon getAsImageIcon(String resourceName) {
        BufferedImage image;
        System.out.println("Getting as imageIcon " + resourceName);
        try {
            InputStream stream = ResourceHelper.getAsStream(resourceName);
            if (stream == null) {
                throw new IOException("Could not find resource " + resourceName);
            }
            image = ImageIO.read(stream);
        } catch (IOException e) {
            throw new RuntimeException("Card image file could not be found: " + resourceName, e);
        }
        System.out.println("SUCCESS Getting as imageIcon " + resourceName);
        return new ImageIcon(image);
    }
}
