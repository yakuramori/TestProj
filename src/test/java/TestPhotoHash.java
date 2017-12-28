import com.pragone.jphash.image.radial.RadialHash;
import com.pragone.jphash.jpHash;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestPhotoHash {
    @Test
    public void test() throws IOException {

        ImageUtils.getImageListFromFolder("c:\\temp", "img");
        RadialHash hash4 = jpHash.getImageRadialHash("c:\\temp\\kot.jpg");
        System.out.println("Hash4: " + hash4);
        BufferedImage image = ImageIO.read(new File("c:\\temp\\iFrameBigImage.png"));
        int t = image.getTransparency();
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = copy.createGraphics();
        g2d.setColor(Color.WHITE); // Or what ever fill color you want...
        g2d.fillRect(0, 0, copy.getWidth(), copy.getHeight());
        g2d.drawImage(image, 0, 0, copy.getWidth(), copy.getHeight(), null);
        g2d.dispose();
        int tc = copy.getTransparency();
        ImageIO.write(copy, "PNG", new File("c:\\temp\\iFrameBigImageCopy.png"));
        RadialHash hash1 = jpHash.getImageRadialHash(copy);
        System.out.println("Hash1: " + hash1);


        System.out.println("Similarity 1 and 4: " + jpHash.getSimilarity(hash1, hash4));
    }
}
