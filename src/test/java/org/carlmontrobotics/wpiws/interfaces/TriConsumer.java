package org.carlmontrobotics.wpiws.interfaces;

/**
 * Represents a function that accepts three arguments and does not return a value.
 *
 * @param <T> the type of the first argument
 * @param <U> the type of the second argument
 * @param <V> the type of the third argument
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

    /**
     * Calls the function
     *
     * @param t the first argument to pass to the function
     * @param u the second argument to pass to the function
     * @param v the third argument to pass to the function
     */
    public void accept(T t, U u, V v);

}
