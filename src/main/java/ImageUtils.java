import com.pragone.jphash.image.radial.RadialHash;
import com.pragone.jphash.jpHash;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageUtils {

    public static List<BufferedImage> getImageListFromFolder(String pathToFolder, String fileNamePattern) {
        List<BufferedImage> bufferedImageList = new ArrayList<>();
        String filterBy;
        if (!pathToFolder.endsWith("\\")) {
            filterBy = pathToFolder + "\\" + fileNamePattern;
        } else {
            filterBy = pathToFolder + fileNamePattern;
        }

        try (Stream<Path> paths = Files.walk(Paths.get(pathToFolder))) {
            List<File> fileList = paths.filter(Files::isRegularFile).filter(path -> path.toString().startsWith(filterBy)).map(Path::toFile).collect(Collectors.toList());
            System.out.println("Number of file(s) loaded: " + fileList.size());
            fileList.stream().forEach(file -> {
                try {
                    bufferedImageList.add(ImageIO.read(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of Image(s) loaded: " + bufferedImageList.size());
        return bufferedImageList;
    }

    public static double compareImagesByHash(BufferedImage image1, BufferedImage image2) {
        double result = jpHash.getSimilarity(getImageHash(image1), getImageHash(image2));
        System.out.println("Similarity: " + result);
        return result;
    }

    public static double compareSimilarity(String hash1, String hash2) {
        double result = jpHash.getSimilarity(RadialHash.fromString(hash1), RadialHash.fromString(hash2));
        System.out.println("Similarity: " + result);
        return result;
    }

    public static double compareSimilarity(RadialHash hash1, RadialHash hash2) {
        double result = jpHash.getSimilarity(hash1, hash2);
        System.out.println("Similarity: " + result);
        return result;
    }

    public static RadialHash getImageHash(BufferedImage image) {
        RadialHash hash = null;
        image = convertIfNot3Byte(image);
        try {
            hash = jpHash.getImageRadialHash(image);
            System.out.println("Image Hash: " + hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static RadialHash getImageHash(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        return getImageHash(image);
    }

    private static BufferedImage convertIfNot3Byte(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            return image;
        }
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = copy.createGraphics();
        g2d.setColor(Color.WHITE); // Or what ever fill color you want...
        g2d.fillRect(0, 0, copy.getWidth(), copy.getHeight());
        g2d.drawImage(image, 0, 0, copy.getWidth(), copy.getHeight(), null);
        g2d.dispose();
        return copy;
    }
}
