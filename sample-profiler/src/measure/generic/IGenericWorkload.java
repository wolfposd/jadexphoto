package measure.generic;

import jadex.commons.future.IFuture;

/**
 * Generisches Interface um automatisches logging zu ermöglichen
 * 
 * @author Posdorfer
 *
 * @param <T>
 *            der Typ des Objektes das Übertragen werden soll
 */
public interface IGenericWorkload<T>
{

    /**
     * Standardisierte Service-Methode für generisches Verhalten.<br>
     * Ziel dieser Methode ist ein einheitliches Interface für alle Services
     * darzustellen, bei der ein Objekt-Typ übergeben wird, dann verändert und
     * anschließend an den Aufrufer zurückgegeben wird.
     * 
     * @param someObject
     * @return IFuture of modifiedSomeObject
     */
    public IFuture<T> modifyObject(T someObject);

}
