package photoeffect.effect;

import jadex.commons.future.Future;

import java.awt.image.BufferedImage;

public interface IImageEffect
{
    public Future<BufferedImage> modifyImage(BufferedImage image);
}
