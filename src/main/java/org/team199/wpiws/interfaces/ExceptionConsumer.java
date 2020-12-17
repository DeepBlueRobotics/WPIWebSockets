package org.team199.wpiws.interfaces;

@FunctionalInterface
public interface ExceptionConsumer<T> {

    public void accept(T t) throws Exception;
    
}
