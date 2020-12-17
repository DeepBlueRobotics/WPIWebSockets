package org.team199.wpiws.interfaces;

@FunctionalInterface
public interface BooleanCallback {

    public void callback(String device, boolean value);

}
