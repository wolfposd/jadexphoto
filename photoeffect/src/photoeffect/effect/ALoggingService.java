package photoeffect.effect;

import jadex.commons.future.Future;

import java.awt.image.BufferedImage;

import photoeffect.filelog.FLog;

public abstract class ALoggingService implements IImageEffect
{

    @Override
    public Future<BufferedImage> modifyImage(BufferedImage image)
    {
        FLog.log(this.getClass().getSimpleName() + ";before modify");

        Future<BufferedImage> result = modifyImageInternal(image);

        FLog.log(this.getClass().getSimpleName() + ";after modify");

        return result;
    }

    protected abstract Future<BufferedImage> modifyImageInternal(BufferedImage image);

}
