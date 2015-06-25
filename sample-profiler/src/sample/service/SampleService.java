package sample.service;

import jadex.bridge.service.annotation.Service;
import measure.generic.AbstractGenericProfiler;

@Service
public class SampleService extends AbstractGenericProfiler<String> implements ISampleService
{

    @Override
    protected String internalModifyObject(String someobject)
    {
        return someobject + someobject + someobject + someobject + someobject.length();
    }

}
