package org.lumijiez.bugger.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.entities.enemies.*;
import org.lumijiez.bugger.entities.enemies.behaviors.Behaviors;
import org.lumijiez.bugger.entities.enemies.types.EnemyTypes;
import org.lumijiez.bugger.util.data.TripleInt;

import java.util.Random;

public class EnemyFactory {
    private static final Random random = new Random();

    public static EnemyEntity createRandomEnemy(World world, Vector2 position) {
        int enemyType = random.nextInt(EnemyTypes.values().length);

        return getEnemyEntity(EnemyTypes.values()[enemyType], world, position);
    }

    public static EnemyEntity createEnemy(EnemyTypes enemyType, World world, Vector2 position) {
        return getEnemyEntity(enemyType, world, position);
    }

    private static EnemyEntity getEnemyEntity(EnemyTypes enemy, World world, Vector2 position) {
        return switch (enemy) {
            case STALKER -> new EnemyEntity(world, EnemyTypes.STALKER, Behaviors.FOLLOW, position, new TripleInt(10, 10, 10));
            case WASP -> new EnemyEntity(world, EnemyTypes.WASP, Behaviors.FOLLOW, position, new TripleInt(10, 10, 10));
            case ULTRON -> new EnemyEntity(world, EnemyTypes.ULTRON, Behaviors.FOLLOW, position, new TripleInt(10, 10, 10));
            case GOLEM -> new EnemyEntity(world, EnemyTypes.GOLEM, Behaviors.FOLLOW, position, new TripleInt(10, 10, 10));
            case STELLAR -> new EnemyEntity(world, EnemyTypes.STELLAR, Behaviors.DEFENSIVE, position, new TripleInt(150, 10, 1));
        };
    }
}
