package photoeffect.effect.shrink;

import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@ProvidedServices(@ProvidedService(name = "shrinkservice", type = IEffectShrink.class, implementation = @Implementation(EffectShrinkService.class)))
public class EffectShrinkAgent
{
    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {

    }
}
