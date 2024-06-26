package org.carlmontrobotics.wpiws.interfaces;

/**
 * Represents a function which is equivalent to {@link java.util.function.Consumer} with the ability to throw an {@link Exception}
 */
@FunctionalInterface
public interface ExceptionConsumer<T> {

    public void accept(T t) throws Exception;

}
