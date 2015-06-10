package photoeffect.send;

import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.IFuture;
import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

import java.util.Date;

@Description("This agent declares a required clock service and Provide a Send Service.")
@Agent
@RequiredServices(@RequiredService(name = "clockservice", type = IClockService.class, binding = @Binding(scope = RequiredServiceInfo.SCOPE_PLATFORM)))
@ProvidedServices(@ProvidedService(type = ISendService.class, implementation = @Implementation(SendService.class)))
public class SendAgent
{

    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {
        IFuture<IClockService> clockservice = agent.getServiceContainer().getRequiredService("clockservice");
        clockservice.addResultListener(new DefaultResultListener<IClockService>()
        {
            public void resultAvailable(IClockService cs)
            {
                System.out.println("Send service starts at: " + new Date(cs.getTime()));
            }
        });
    }
}
