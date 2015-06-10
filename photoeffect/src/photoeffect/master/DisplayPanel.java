package photoeffect.master;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class DisplayPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private BufferedImage backgroundImage;

    public DisplayPanel()
    {
        setImage((String) null);
    }

    public BufferedImage getImage()
    {
        return backgroundImage;
    }

    public void setImage(BufferedImage image)
    {
        backgroundImage = image;
    }

    public void setImage(String path)
    {
        if (path != null)
        {
            try
            {
                this.backgroundImage = ImageIO.read(new File(path));
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(backgroundImage, 0);
                tracker.waitForID(0);
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            this.repaint();

        }
        else
        {
            this.backgroundImage = null;
        }
        setPreferredSize(new Dimension(512, 384));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (backgroundImage == null)
            g.drawString("CHOOSE AN IMAGE FROM THE LIST", 50, 200);
        else
            g.drawImage(backgroundImage, 0, 0, 512, 384, this);
        // g.drawImage(backgroundImage, 0, 0, this);
    }

}