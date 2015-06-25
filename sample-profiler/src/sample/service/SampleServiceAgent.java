package sample.service;

import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@ProvidedServices(@ProvidedService(name = "sampleservice", type = ISampleService.class, implementation = @Implementation(SampleService.class)))
public class SampleServiceAgent
{

    @Agent
    MicroAgent agent;

    @AgentBody
    public void executeBody()
    {

    }

}
