package org.lumijiez.bugger.util.data;

public record CollisionPair(Class<?> typeA, Class<?> typeB) {
    public boolean matches(Object objA, Object objB) {
        return (typeA.isInstance(objA) && typeB.isInstance(objB));
    }
}
