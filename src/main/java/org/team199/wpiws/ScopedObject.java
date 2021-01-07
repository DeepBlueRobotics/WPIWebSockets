package org.team199.wpiws;

import java.util.function.Consumer;

import org.team199.wpiws.interfaces.ExceptionConsumer;

/**
 * Represents an object which has a destructor
 * @param T the object type held by this ScopedObject
 */
public class ScopedObject<T> implements AutoCloseable {
    
    private final T object;
    private final Consumer<T> closer;
    private boolean isClosed;
    
    /**
     * Creates a ScopedObject from an AutoClosable and assigns its close method to {@link AutoCloseable#close()}
     * @param <T> the type of the object
     * @param object the object
     * @return a ScopedObject with its closer assigned to {@link AutoCloseable#close()}
     */
    public static <T extends AutoCloseable> ScopedObject<T> fromAutoClosable(T object) {
        return new ScopedObject<T>(object, createCloser(T::close));
    }

    /**
     * Creates a new ScopedObject
     * @param object the Object to which to add a destructor
     * @param closer a Consumer which will be fed with the object when it (the Object) is destroyed
     */
    public ScopedObject(T object, Consumer<T> closer) {
        this.object = object;
        this.closer = closer;
        this.isClosed = false;
    }
    
    /**
     * Retrieves the object associeated with this ScopedObject
     * @return the object associeated with this ScopedObject
     */
    public T getObject() {
        return object;
    }

    /**
     * Closes the underlying object if it has not already done so
     */
    @Override
    public void close() {
        if(isClosed) {
            return;
        }
        try {
            isClosed = true;
            closer.accept(object);
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    /**
     * Creates a Consumer which will not throw an Exception
     * @param <T> the type of the object consumed by the consumer
     * @param closer the Consumer to wrap
     * @return a Consumer which will call the given Consumer and will not throw an exception
     */
    public static <T> Consumer<T> createCloser(ExceptionConsumer<T> closer) {
        return object -> {
            try {
                closer.accept(object);
            } catch(Exception e) {
                e.printStackTrace(System.err);
            }
        };
    }
    
}
