package datasource;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ResourceHelper {
    private ResourceHelper() {
        throw new IllegalStateException("Do not instantiate a utility class");
    }

    public static InputStream getAsStream(String resourceName) {
        return ResourceHelper.class.getResourceAsStream(resourceName);
    }

    public static File getAsFile(String resourceName) {
        URL url = ResourceHelper.class.getResource(resourceName);
        if (url == null) {
            throw new RuntimeException("URL of Resource was null: " + resourceName);
        }
        try {
            return Paths.get(url.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to load resource: " + resourceName, e);
        }
    }

    public static ImageIcon getAsImageIcon(String resourceName) {
        BufferedImage image;
        try {
            image = ImageIO.read(ResourceHelper.getAsFile(resourceName));
        } catch (IOException e) {
            throw new RuntimeException("Card image file could not be found: " + resourceName, e);
        }
        return new ImageIcon(image);
    }
}
