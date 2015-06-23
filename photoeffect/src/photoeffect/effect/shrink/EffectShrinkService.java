package photoeffect.effect.shrink;

import jadex.bridge.service.annotation.Service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import measure.generic.AbstractGenericProfiler;

@Service
public class EffectShrinkService extends AbstractGenericProfiler<BufferedImage> implements IEffectShrink
{
    @Override
    protected BufferedImage internalModifyObject(BufferedImage someobject)
    {

        Image img = someobject.getScaledInstance((int) (someobject.getWidth() * 0.5),
                (int) (someobject.getHeight() * 0.5), Image.SCALE_FAST);

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

}
