package photoeffect.effect.blur;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import measure.generic.AbstractGenericProfiler;

@Service
public class EffectBlurService extends AbstractGenericProfiler<BufferedImage> implements IEffectBlur
{

    @ServiceComponent
    protected IInternalAccess agent;

    @Override
    protected BufferedImage internalModifyObject(BufferedImage image)
    {
        BufferedImage blurredImage = image.getSubimage(0, 0, image.getWidth(), image.getHeight());

        if (image != null)
        {
            float[] matrix = { 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, };

            BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
            op.filter(image, blurredImage);
        }

        if (System.getProperty("loop") != null)
            for (long i = 0; i < 30000000000L; i++)
            {
                // approx 10secs
            }

        return blurredImage;
    }

}
