package measure.generic;

import jadex.commons.future.Future;
import objectexplorer.MemoryMeasurer;
import photoeffect.filelog.FLog;

/**
 * 
 * Abstract class to enable generic timestamp creation for jadex-services
 * 
 * @author Posdorfer
 *
 * @param <T>
 *            object sent over link
 */
public abstract class AbstractGenericProfiler<T> implements IGenericWorkload<T>
{

    @Override
    public Future<T> modifyObject(T someobject)
    {

        FLog.log(this.getClass().getSimpleName() + ";before modify size: " + MemoryMeasurer.measureBytes(someobject));
        FLog.log(this.getClass().getSimpleName() + ";before modify");

        someobject = internalModifyObject(someobject);
        Future<T> returnval = new Future<T>();
        returnval.setResult(someobject);

        FLog.log(this.getClass().getSimpleName() + ";after modify");
        FLog.log(this.getClass().getSimpleName() + ";after modify size: " + MemoryMeasurer.measureBytes(someobject));

        return returnval;
    }

    protected abstract T internalModifyObject(T someobject);

}
