package photoeffect.effect.mirror;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.Future;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

@Service
public class EffectMirrorService implements IEffectMirror
{
    @ServiceComponent
    protected IInternalAccess agent;

    @Override
    public Future<BufferedImage> modifyImage(BufferedImage image)
    {
        System.out.println("  IN EffectMirrorService");
        Future<BufferedImage> ret = new Future<>();

        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));

        AffineTransformOp ato = new AffineTransformOp(at, null);

        BufferedImage modifiedImage = image.getSubimage(0, 0, image.getWidth(), image.getHeight());
        ato.filter(image, modifiedImage);

        for (long i = 0; i < 30000000000L; i++)
        {
            // approx 10secs
        }

        ret.setResult(modifiedImage);
        return ret;
    }

}
