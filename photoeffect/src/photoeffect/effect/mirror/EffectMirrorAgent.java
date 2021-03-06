package photoeffect.effect.mirror;

import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@ProvidedServices(@ProvidedService(name = "mirrorservice", type = IEffectMirror.class, implementation = @Implementation(EffectMirrorService.class)))
public class EffectMirrorAgent
{

    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {

    }
}
