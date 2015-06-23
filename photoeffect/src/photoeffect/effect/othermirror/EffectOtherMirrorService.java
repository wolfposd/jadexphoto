package photoeffect.effect.othermirror;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import measure.generic.AbstractGenericProfiler;

@Service
public class EffectOtherMirrorService extends AbstractGenericProfiler<BufferedImage> implements IEffectOtherMirror
{
    @ServiceComponent
    protected IInternalAccess agent;

    @Override
    protected BufferedImage internalModifyObject(BufferedImage image)
    {
        if (System.getProperty("loop") != null)
            for (long i = 0; i < 10000000000L; i++)
            {
                // approx 5secs
            }

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

        return modifiedImage;
    }
}
