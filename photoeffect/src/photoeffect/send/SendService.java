package photoeffect.send;

import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.bridge.service.annotation.ServiceStart;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.future.DelegationResultListener;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;

import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import photoeffect.items.PhotoDAO;

@Service
public class SendService implements ISendService
{

    @ServiceComponent
    protected IInternalAccess agent;

    protected IClockService clock;

    protected DateFormat format;

    /*
     * il servizio offerto � la creazione di un DAO che viene anche ritornato
     * come risultato al chiamante
     * 
     * the service offered creating a DAO. That is also returned as the result
     * to the caller
     */
    public String send(BufferedImage image)
    {

        PhotoDAO myDAO = new PhotoDAO(image);
        System.out.print(agent.getComponentIdentifier().getLocalName());
        System.out.print(" created the new PhotoDAO " + myDAO.toString());
        System.out.print("for " + image);
        System.out.println("at " + format.format(clock.getTime()));
        myDAO.store();
        return myDAO.getFilename();

    }

    @ServiceStart
    public IFuture<IClockService> startService()
    {
        final Future<IClockService> ret = new Future<IClockService>();

        format = new SimpleDateFormat("hh:mm:ss");

        IFuture<IClockService> fut = agent.getServiceContainer().getRequiredService("clockservice");
        fut.addResultListener(new DelegationResultListener<IClockService>(ret)
        {
            public void customResultAvailable(IClockService result)
            {
                clock = result;
                super.customResultAvailable(null);
            }
        });
        return ret;
    }

}
