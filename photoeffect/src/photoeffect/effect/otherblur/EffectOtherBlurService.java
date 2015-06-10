package photoeffect.effect.otherblur;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.Future;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

@Service
public class EffectOtherBlurService implements IEffectOtherBlur
{

    @ServiceComponent
    protected IInternalAccess agent;

    @Override
    public Future<BufferedImage> modifyImage(BufferedImage image)
    {
        System.out.println("  IN EffectOtherBlurService");
        for (long i = 0; i < 15000000000L; i++)
        {
            // approx 5secs
        }
        Future<BufferedImage> ret = new Future<>();

        BufferedImage blurredImage = image.getSubimage(0, 0, image.getWidth(), image.getHeight());

        if (image != null)
        {
            float[] matrix = { 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, 0.111f, };

            BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
            op.filter(image, blurredImage);
        }

        for (long i = 0; i < 15000000000L; i++)
        {
            // approx 5secs
        }

        ret.setResult(blurredImage);
        return ret;
    }

}
