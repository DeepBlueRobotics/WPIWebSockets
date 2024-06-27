package org.carlmontrobotics.wpiws.connection;

/**
 * Represents a Runnable which is being run in another {@link Thread}
 * @param T the object type held in this RunningObject
 */
public class RunningObject<T extends Runnable> {

    /**
     * The {@link Runnable} which is running
     */
    public final T object;
    /**
     * The Thread running the Object
     */
    public final Thread thread;

    /**
     * Creates a new RunningObject from the given {@link Runnable} and runs it in a new {@link Thread}
     * @param object the {@link Runnable} to run
     */
    public RunningObject(T object) {
        this(object, start(object));
    }

    /**
     * Creates a new RunningObject from the given {@link Runnable} and {@link Thread}
     * @param object the {@link Runnable} which is running
     * @param thread the {@link Thread} it which the {@link Runnable} is running
     */
    public RunningObject(T object, Thread thread) {
        this.object = object;
        this.thread = thread;
    }

    /**
     * Begins running the Runnable in a new Thread
     * @param object the Runnable to run
     * @return the new Thread which is running the Runnable
     */
    public static final Thread start(Runnable object) {
        Thread thread = new Thread(object);
        thread.start();
        return thread;
    }

}
