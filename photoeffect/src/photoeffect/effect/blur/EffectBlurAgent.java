package photoeffect.effect.blur;

import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@ProvidedServices(@ProvidedService(name = "blurservice", type = IEffectBlur.class, implementation = @Implementation(EffectBlurService.class)))
public class EffectBlurAgent
{

    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {

    }

}
