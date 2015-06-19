package photoeffect.effect.othermirror;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.Future;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import photoeffect.effect.ALoggingService;

@Service
public class EffectOtherMirrorService extends ALoggingService implements IEffectOtherMirror
{
    @ServiceComponent
    protected IInternalAccess agent;

    @Override
    public Future<BufferedImage> modifyImageInternal(BufferedImage image)
    {
        if (System.getProperty("loop") != null)
            for (long i = 0; i < 10000000000L; i++)
            {
                // approx 5secs
            }
        Future<BufferedImage> ret = new Future<>();

        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));

        AffineTransformOp ato = new AffineTransformOp(at, null);

        BufferedImage modifiedImage = image.getSubimage(0, 0, image.getWidth(), image.getHeight());
        ato.filter(image, modifiedImage);

        if (System.getProperty("loop") != null)
            for (long i = 0; i < 10000000000L; i++)
            {
                // approx 5secs
            }

        ret.setResult(modifiedImage);
        return ret;
    }

}
