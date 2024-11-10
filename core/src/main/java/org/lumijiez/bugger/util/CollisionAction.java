package org.lumijiez.bugger.util;

@FunctionalInterface
public interface CollisionAction {
    void handle(Object objectA, Object objectB);
}
