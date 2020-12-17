package org.team199.wpiws;

import java.util.concurrent.CopyOnWriteArrayList;

public class UniqueCopyOnWriteArrayList<T> extends CopyOnWriteArrayList<T> {

    private static final long serialVersionUID = 7526197689969175318L;

    @Override
    public boolean add(T e) {
        if(!contains(e)) {
            return super.add(e);
        }
        return false;
    }
    
}
