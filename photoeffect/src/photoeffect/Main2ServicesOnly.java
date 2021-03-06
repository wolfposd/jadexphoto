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
import photoeffect.effect.enlarge.EffectEnlargeAgent;
import photoeffect.effect.mirror.EffectMirrorAgent;
import photoeffect.effect.otherblur.EffectOtherBlurAgent;
import photoeffect.effect.othermirror.EffectOtherMirrorAgent;
import photoeffect.effect.shrink.EffectShrinkAgent;
import photoeffect.filelog.FLog;

public class Main2ServicesOnly
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
                IComponentManagementService.class, RequiredServiceInfo.SCOPE_GLOBAL).get(sus);

        FLog.initLog("Main2ServicesOnly");

        IComponentIdentifier cid;
        for (Class<?> agentclass : new Class<?>[] { EffectBlurAgent.class, EffectOtherBlurAgent.class,
                EffectMirrorAgent.class, EffectOtherMirrorAgent.class, EffectShrinkAgent.class,
                EffectEnlargeAgent.class })
        {

            cid = cms.createComponent(null, agentclass.getName() + ".class", null, null).get(sus);
            System.out.println("Started " + agentclass.getSimpleName() + " component: " + cid);

        }

        // comment if for-loops in services should be disabled
        System.setProperty("loop", "yes");

    }
}
