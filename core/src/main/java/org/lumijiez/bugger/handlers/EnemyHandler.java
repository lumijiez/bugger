package org.lumijiez.bugger.handlers;

import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;

import java.util.ArrayList;
import java.util.List;

public class EnemyHandler {
    private final List<EnemyEntity> enemies = new ArrayList<>();
    private static EnemyHandler instance;

    private EnemyHandler() {}

    public static EnemyHandler getInstance() {
        if (instance == null) {
            instance = new EnemyHandler();
        }
        return instance;
    }

    public void render(float delta) {
        EnemySpawner.getInstance().cycle(delta);
        for (EnemyEntity enemy : enemies) enemy.cycle();
    }

    public void cycle() {
        for (EnemyEntity enemy : enemies) {
            if (enemy.isMarkedToDestroy()) {
                Bugger.getInstance().getEntitiesToDestroy().add(enemy);
            }
        }
    }

    public List<EnemyEntity> getEnemies() {
        return enemies;
    }
}
