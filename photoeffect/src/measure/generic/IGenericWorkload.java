package measure.generic;

import jadex.commons.future.Future;

public interface IGenericWorkload<T>
{

    public Future<T> modifyObject(T someObject);

}
