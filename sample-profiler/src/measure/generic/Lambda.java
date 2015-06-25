package measure.generic;

import jadex.commons.future.DefaultResultListener;

/**
 * Wrapper class for DefaultResultListener to make using Lambdas in Java 8 work<br>
 * <br>
 * 
 * turns this:
 * 
 * <pre>
 * {@code 
 * stringResult.addResultListener(new DefaultResultListener<String>()
 *         {
 *             public void resultAvailable(String result)
 *             {
 *                 // ...
 *             }
 *         });
 * </pre>
 * 
 * into this:
 * 
 * <pre>
 * {@code
 * stringResult.addResultListener(Lambda.result(result -> {
 *     // ...
 * ));
 * 
 * </pre>
 * 
 * @author w.posdorfer
 *
 */
public final class Lambda
{

    public static <E> DefaultResultListener<E> result(ILambda<E> lambda)
    {
        return new DefaultResultListener<E>()
        {
            @Override
            public void resultAvailable(E result)
            {
                lambda.run(result);
            }
        };
    }

    /**
     * functional interface required for lambdas
     *
     * @param <T>
     *            some type
     */
    public static interface ILambda<T>
    {
        public void run(T e);
    }

}
