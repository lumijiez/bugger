package org.lumijiez.bugger.util;

public record CollisionPair(Class<?> typeA, Class<?> typeB) {
    public boolean matches(Object objA, Object objB) {
        return (typeA.isInstance(objA) && typeB.isInstance(objB));
    }
}
