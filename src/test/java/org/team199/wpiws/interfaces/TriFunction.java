package org.team199.wpiws.interfaces;

@FunctionalInterface
public interface TriFunction<T, U, R, S> {

    public S apply(T t, U u, R r);

}
