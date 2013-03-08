package zoo.htmunit.tryouts;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

public class CleanImage {

    public static void main(String... args) throws Exception {
	URL url = CleanImage.class.getResource("/captcha/captcha_x.jpg");
	File file = new File(url.toURI());
	BufferedImage image = ImageIO.read(new FileInputStream(file));

	System.out.println(image.getHeight());
	System.out.println(image.getWidth());

	// Scalr.apply(image, ops)

	ImageFilter filter = new GrayFilter(true, 50);

	ImageProducer imageProducer = new FilteredImageSource(image.getSource(), filter);
	// imageProducer.

	Image dest = Toolkit.getDefaultToolkit().createImage(imageProducer);

	ImageIO.write(imageToBufferedImage(dest, image.getWidth(), image.getHeight()), "png", new File("./target/dest.png"));

    }

    private static BufferedImage imageToBufferedImage(Image image, int width, int height) {
	BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2 = dest.createGraphics();
	g2.drawImage(image, 0, 0, null);
	g2.dispose();
	return dest;
    }

}
