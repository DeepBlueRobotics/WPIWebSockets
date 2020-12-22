package org.team199.wpiws;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An implementation of CopyOnWriteArrayList which prevents {@link java.util.List#add(Object)} from altering the list if the specified element already exists in the list
 */
public class UniqueCopyOnWriteArrayList<T> extends CopyOnWriteArrayList<T> {

    private static final long serialVersionUID = 7526197689969175318L;

    @Override
    public boolean add(T e) {
        return addIfAbsent(e);
    }
    
}
