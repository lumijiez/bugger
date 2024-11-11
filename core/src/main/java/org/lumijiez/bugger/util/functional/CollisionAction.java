package org.lumijiez.bugger.util.functional;

@FunctionalInterface
public interface CollisionAction {
    void handle(Object objectA, Object objectB);
}
