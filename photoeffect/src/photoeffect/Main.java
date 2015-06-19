package photoeffect;

import jadex.base.Starter;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.SServiceProvider;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.IFuture;
import jadex.commons.future.ThreadSuspendable;
import photoeffect.effect.blur.EffectBlurAgent;
import photoeffect.effect.mirror.EffectMirrorAgent;
import photoeffect.effect.otherblur.EffectOtherBlurAgent;
import photoeffect.effect.othermirror.EffectOtherMirrorAgent;
import photoeffect.filelog.FLog;
import photoeffect.master.MasterAgent;

public class Main
{

    public static void main(String[] args)
    {

        String[] defargs = new String[] { "-gui", "true", "-welcome", "false", "-cli", "false", "-printpass", "false" };
        String[] newargs = new String[defargs.length + args.length];
        System.arraycopy(defargs, 0, newargs, 0, defargs.length);
        System.arraycopy(args, 0, newargs, defargs.length, args.length);
        IFuture<IExternalAccess> platfut = Starter.createPlatform(newargs);
        ThreadSuspendable sus = new ThreadSuspendable();
        IExternalAccess platform = platfut.get(sus);
        System.out.println("Started platform: " + platform.getComponentIdentifier());
        IComponentManagementService cms = SServiceProvider.getService(platform.getServiceProvider(),
                IComponentManagementService.class, RequiredServiceInfo.SCOPE_PLATFORM).get(sus);

        FLog.initLog("AgentAndServices");

        // Starting Agents:

        IComponentIdentifier cid;
        for (Class<?> agentclass : new Class<?>[] { EffectBlurAgent.class, EffectOtherBlurAgent.class,
                EffectMirrorAgent.class, EffectOtherMirrorAgent.class, MasterAgent.class })
        {

            cid = cms.createComponent(null, agentclass.getName() + ".class", null, null).get(sus);
            System.out.println("Started " + agentclass.getSimpleName() + " component: " + cid);

        }

        // set to null if for-loops in services should be disabled
        System.setProperty("loop", "yes");

    }

}
