package org.team199.wpiws.interfaces;

@FunctionalInterface
public interface TriConsumer<T, U, R> {

    public void accept(T t, U u, R r);

}
