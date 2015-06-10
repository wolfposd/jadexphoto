package photoeffect.effect.othermirror;

import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@ProvidedServices(@ProvidedService(name = "othermirrorservice", type = IEffectOtherMirror.class, implementation = @Implementation(EffectOtherMirrorService.class)))
public class EffectOtherMirrorAgent
{

    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {

    }
}
