package sample;

import jadex.base.Starter;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.SServiceProvider;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.IFuture;
import jadex.commons.future.ThreadSuspendable;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import measure.generic.FLog;
import sample.agent.SampleAgent;
import sample.service.SampleServiceAgent;

public class Main
{

    public static void main(String[] args)
    {

        if (!isJavaAgentSet())
        {
            throw new RuntimeException("Es scheint als sei der JavaAgent als VM-Argument nicht gesetzt zu sein.\n"
                    + "Dazu folgendes unter Run-Configuration->Main->Arguments->VM-Arguments eintragen"
                    + ": \"-javaagent:libs/object-explorer.jar\"" + "\n(natürlich ohne Anführungszeichen)");
        }

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

        // Vorher einmal das File-Log initialisieren
        // name entscheidet den Namen der text-datei innerhalb des Log-Ordners
        FLog.initLog("sample-profiler");

        IComponentIdentifier cid;
        // Ab hier werden die Agenten gestartet, dazu einfach alle benötigten in
        // die Array-Initialisierung eintragen
        for (Class<?> agentclass : new Class<?>[] { SampleServiceAgent.class, SampleAgent.class })
        {
            cid = cms.createComponent(null, agentclass.getName() + ".class", null, null).get(sus);
            System.out.println("Started " + agentclass.getSimpleName() + " component: " + cid);
        }

    }

    public static boolean isJavaAgentSet()
    {
        boolean isSet = false;

        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();

        for (String string : arguments)
        {
            if (string.contains("-javaagent"))
            {
                isSet = true;
                break;
            }
        }
        return isSet;

    }
}
