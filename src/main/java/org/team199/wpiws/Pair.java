package org.team199.wpiws;

import java.util.Objects;

/**
 * Binds two objects as one
 * @param T the type of the first object
 * @param U the type of the second object
 */
public class Pair<T, U> {

    /**
     * The first value
     */
    public final T val1;
    /**
     * The second value
     */
    public final U val2;

    /**
     * Creates a new Pair
     * @param val1 the first value of this Pair
     * @param val2 the second value of this Pair
     */
    public Pair(T val1, U val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pair) {
            Pair<?, ?> other = (Pair<?, ?>)obj;
            return Objects.equals(val1, other.val1) && Objects.equals(val2, other.val2);
        }
        return false;
    }

}
