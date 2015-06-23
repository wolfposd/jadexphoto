package photoeffect.effect.enlarge;

import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@ProvidedServices(@ProvidedService(name = "enlargeservice", type = IEffectEnlarge.class, implementation = @Implementation(EffectEnlargeService.class)))
public class EffectEnlargeAgent
{
    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {

    }
}
