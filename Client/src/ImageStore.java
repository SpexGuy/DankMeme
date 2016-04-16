import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by martin on 4/16/16.
 */
public class ImageStore {
    /** The single instance of this class */
    private static final ImageStore single = new ImageStore();

    /**
     * Get the single instance of this class
     *
     * @return The single instance of this class
     */
    public static ImageStore get() {
        return single;
    }

    /** The cached sprite map, from reference to sprite instance */
    private HashMap<String, Image> images = new HashMap<>();

    /**
     * Retrieve a sprite from the store
     *
     * @param ref The reference to the image to use for the sprite
     * @return A sprite instance containing an accelerate image of the request reference
     */
    public Image getImage(String ref) {
        // if we've already got the sprite in the cache
        // then just return the existing version
        if (images.get(ref) != null) {
            return images.get(ref);
        }

        // otherwise, go away and grab the sprite from the resource
        // loader
        BufferedImage sourceImage = null;

        try {
            // The ClassLoader.getResource() ensures we get the sprite
            // from the appropriate place, this helps with deploying the game
            // with things like webstart. You could equally do a file look
            // up here.
            URL url = this.getClass().getClassLoader().getResource("assets/"+ref);

            if (url == null) {
                fail("Can't find ref: "+ref);
            } else {
                // use ImageIO to read the image in
                sourceImage = ImageIO.read(url);
            }
        } catch (IOException e) {
            fail("Failed to load: "+ref);
        }

        assert(sourceImage != null);
        // create an accelerated image of the right size to store our sprite in
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);

        // draw our source image into the accelerated image
        image.getGraphics().drawImage(sourceImage,0,0,null);

        // create a sprite, add it the cache then return it
        images.put(ref,image);

        return image;
    }

    /**
     * Utility method to handle resource loading failure
     *
     * @param message The message to display on failure
     */
    private void fail(String message) {
        // we're pretty dramatic here, if a resource isn't available
        // we dump the message and exit the game
        System.err.println(message);
        System.exit(0);
    }
}