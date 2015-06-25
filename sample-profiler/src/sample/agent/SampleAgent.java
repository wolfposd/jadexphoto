package sample.agent;

import jadex.bridge.service.RequiredServiceInfo;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.IFuture;
import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import measure.generic.FLog;
import measure.generic.IGenericWorkload;
import measure.generic.Lambda;
import sample.service.ISampleService;

@Agent
@RequiredServices({ @RequiredService(name = "sampleservice", type = ISampleService.class, binding = @Binding(scope = RequiredServiceInfo.SCOPE_GLOBAL)) })
public class SampleAgent
{

    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {
        executeBodyInSchoen();
    }

    /**
     * ExecuteBody des Agenten mit ResultListenern
     */
    public void executeBodyInSchoen()
    {
        // Schritt 1: Zugriff auf den Service erhalten:
        IFuture<IGenericWorkload<String>> futureSampleService = agent.getRequiredService("sampleservice");
        futureSampleService.addResultListener(new DefaultResultListener<IGenericWorkload<String>>()
        {
            @Override
            public void resultAvailable(IGenericWorkload<String> sampleService)
            {
                // an dieser Stelle haben wir direkten zugriff auf den
                // sampleservice

                // Schritt 2: einen String über den sampleservice verändern:

                FLog.log(SampleAgent.class.getSimpleName(), "sende String an Service");
                IFuture<String> futureString = sampleService.modifyObject("diesistmeinteststring");

                // manchmal können viele geschachtelte ResultListener unnötige
                // Zeilen Code produzieren.
                // Hier schafft Lambda.result(...) abhilfe, aber nur ab Java 1.8

                futureString.addResultListener(Lambda.result(modifiedstring -> {
                    FLog.log(SampleAgent.class.getSimpleName(), "bekomme String von Service");
                    System.out.println("Dieser String wurde vom sampleservice zurückgeliefert: >" + modifiedstring
                            + "<");
                }));
            }
        });
    }

    /**
     * ExecuteBody des Agenten mit Blocking-Calls
     */
    public void executeBodyMalAnders()
    {
        // Wenn wir nicht mit ResultListenern arbeiten
        // wollen/koennen/moechten/duerfen, können wir auch
        // blocking-calls verwenden, das sieht so aus:

        @SuppressWarnings("unchecked")
        IGenericWorkload<String> service = (IGenericWorkload<String>) agent.getRequiredService("sampleservice").get();

        String result = service.modifyObject("meinstring").get();

        System.out.println("Dieser String wurde vom sampleservice zurückgeliefert: >" + result + "<");

    }

    public void step()
    {

    }

}
