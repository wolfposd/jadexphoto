package photoeffect.effect.enlarge;

import jadex.bridge.service.annotation.Service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import measure.generic.AbstractGenericProfiler;

@Service
public class EffectEnlargeService extends AbstractGenericProfiler<BufferedImage> implements IEffectEnlarge
{

    @Override
    protected BufferedImage internalModifyObject(BufferedImage someobject)
    {
        Image img = someobject.getScaledInstance(someobject.getWidth() * 2, someobject.getHeight() * 2,
                Image.SCALE_FAST);

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

}
