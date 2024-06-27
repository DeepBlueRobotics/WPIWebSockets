package org.carlmontrobotics.wpiws.interfaces;

/**
 * Represents a function that accepts three arguments and returns a value.
 *
 * @param <T> the type of the first argument
 * @param <U> the type of the second argument
 * @param <V> the type of the third argument
 * @param <R> the type of the return value
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {

    /**
     * Calls the function
     *
     * @param t the first argument to pass to the function
     * @param u the second argument to pass to the function
     * @param v the third argument to pass to the function
     *
     * @return the result of the function
     */
    public R apply(T t, U u, V v);

}
