package org.lumijiez.bugger.util;

import org.lumijiez.bugger.entities.Entity;
import org.lumijiez.bugger.entities.EntityType;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.entities.weapons.Projectile;

public class Util {
    public static EntityType determineEntityType(Entity entity) {
        if (entity instanceof Projectile) {
            return EntityType.PROJECTILE;
        } else if (entity instanceof EnemyEntity) {
            return EntityType.ENEMY;
        }
        throw new IllegalArgumentException("Unknown entity type");
    }
}
