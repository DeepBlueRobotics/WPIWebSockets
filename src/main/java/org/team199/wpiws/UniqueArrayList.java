package org.team199.wpiws;

import java.util.ArrayList;

/**
 * An implementation of ArrayList which prevents {@link java.util.List#add(Object)} from altering the list if the specified element already exists in the list
 */
public class UniqueArrayList<T> extends ArrayList<T> {

    private static final long serialVersionUID = 7526197689969175318L;

    @Override
    public boolean add(T e) {
        if(!contains(e)) {
            return super.add(e);
        }
        return false;
    }
    
}
