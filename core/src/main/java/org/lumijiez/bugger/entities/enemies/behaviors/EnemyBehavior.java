package org.lumijiez.bugger.entities.enemies.behaviors;

import org.lumijiez.bugger.entities.enemies.EnemyEntity;

public interface EnemyBehavior {
    void update(EnemyEntity enemy);
    void init(EnemyEntity enemy);
}
