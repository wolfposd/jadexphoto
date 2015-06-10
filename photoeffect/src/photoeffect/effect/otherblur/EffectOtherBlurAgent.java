package photoeffect.effect.otherblur;

import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@ProvidedServices(@ProvidedService(name = "otherblurservice", type = IEffectOtherBlur.class, implementation = @Implementation(EffectOtherBlurService.class)))
public class EffectOtherBlurAgent
{

    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {

    }

}
