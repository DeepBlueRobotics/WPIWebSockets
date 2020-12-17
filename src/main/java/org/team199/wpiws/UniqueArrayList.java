package org.team199.wpiws;

import java.util.ArrayList;

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
