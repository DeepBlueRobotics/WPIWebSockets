package org.team199.wpiws;

public class Pair<T, U> {

    public final T val1;
    public final U val2;

    public Pair(T val1, U val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pair) {
            Pair<?, ?> other = (Pair<?, ?>)obj;
            return val1.equals(other.val1) && val2.equals(other.val2);
        }
        return false;
    }

}
