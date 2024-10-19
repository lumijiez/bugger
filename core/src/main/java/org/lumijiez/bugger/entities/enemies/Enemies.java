package org.lumijiez.bugger.entities.enemies;

public enum Enemies {
    STALKER("Stalker"),
    WASP("Wasp"),
    ULTRON("Ultron"),
    GOLEM("Golem"),
    STELLAR("Stellar");

    private final String className;

    Enemies(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}

