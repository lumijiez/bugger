package org.lumijiez.bugger.entities.enemies.types;

public enum EnemyTypes implements EnemyType {
    STALKER("images/stalker.png", 10f, 100f),
    GOLEM("images/golem.png", 30f, 120f),
    STELLAR("images/stellar.png", 15f, 100f),
    WASP("images/wasp.png", 8f, 80f),
    ULTRON("images/ultron.png", 20f, 150f);

    private final String texturePath;
    private final float size;
    private final float spawnRadius;

    EnemyTypes(String texturePath, float size, float spawnRadius) {
        this.texturePath = texturePath;
        this.size = size;
        this.spawnRadius = spawnRadius;
    }

    @Override
    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public float getSize() {
        return size;
    }

    @Override
    public float getSpawnRadius() {
        return spawnRadius;
    }
}
