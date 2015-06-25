package measure.generic;

import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import objectexplorer.MemoryMeasurer;

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
    public IFuture<T> modifyObject(T someobject)
    {

        // erst loggen
        FLog.log(this.getClass().getSimpleName(), "before modify");
        FLog.log(this.getClass().getSimpleName(), "before modify size: " + MemoryMeasurer.measureBytes(someobject));

        // jetzt verändern
        someobject = internalModifyObject(someobject);

        // wieder loggen
        FLog.log(this.getClass().getSimpleName(), "after modify");
        FLog.log(this.getClass().getSimpleName(), "after modify size: " + MemoryMeasurer.measureBytes(someobject));

        // zurückgeben
        Future<T> returnval = new Future<T>();
        returnval.setResult(someobject);
        return returnval;
    }

    /**
     * Die interne Methode, die vom Service implementiert werden muss. Diese
     * Methode wird ge-wrappt vom AbstractGenericProfiler. Dieser implementiert
     * die Methode des Service-Interfaces mit zusätzlichen Log-Funktionen und
     * reicht die "echte" Implementation mithilfe dieser Methode weiter. <br>
     * <br>
     * Es müssen keine Future Objekte an dieser Stelle erzeugt werden, da sie in
     * der Wrapper-Klasse automatisch erzeugt werden.
     * 
     * @param someobject
     * @return result
     */
    protected abstract T internalModifyObject(T someobject);

}
