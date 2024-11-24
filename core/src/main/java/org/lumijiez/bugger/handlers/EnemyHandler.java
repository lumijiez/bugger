package org.lumijiez.bugger.handlers;

import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.entities.enemies.behaviors.Behaviors;

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
        SpawnerHandler.getInstance().cycle(delta);
        for (EnemyEntity enemy : enemies) enemy.cycle();
    }

    public void cycle() {
        for (EnemyEntity enemy : enemies) {
            if (enemy.isMarkedToDestroy()) {
                CleanupHandler.getInstance().getEntitiesToDestroy().add(enemy);
            }
        }
    }

    public List<EnemyEntity> getEnemies() {
        return enemies;
    }

    public void overrideBehaviorForExisting(Behaviors behavior) {
        switch(behavior) {
            case FOLLOW -> enemies.forEach(enemy -> enemy.setBehavior(Behaviors.FOLLOW, 10, 10, 10));
            case DEFENSIVE -> enemies.forEach(enemy -> enemy.setBehavior(Behaviors.DEFENSIVE, 150, 10, 1));
        }
    }
}
