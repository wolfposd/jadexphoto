package photoeffect.items;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PhotoDAO
{

    private BufferedImage image;

    private String filename;

    public String getFilename()
    {
        return filename;
    }

    public PhotoDAO(BufferedImage image)
    {
        super();
        this.image = image;
    }

    public void store()
    {
        filename = "src/a" + new Double(Math.random()).toString() + ".jpg";
        try
        {
            ImageIO.write(image, "jpg", new File(filename));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("PhotoDAO stored the blurred image in " + filename);
    }
}
