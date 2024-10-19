package org.lumijiez.bugger.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.entities.enemies.*;

import java.util.Random;

public class EnemyFactory {
    private static final Random random = new Random();

    public static EnemyEntity createRandomEnemy(World world, Vector2 position) {
        int enemyType = random.nextInt(Enemies.values().length);

        return getEnemyEntity(Enemies.values()[enemyType], world, position);
    }

    public static EnemyEntity createEnemy(Enemies enemyType, World world, Vector2 position) {
        return getEnemyEntity(enemyType, world, position);
    }

    private static EnemyEntity getEnemyEntity(Enemies enemy, World world, Vector2 position) {
        return switch (enemy) {
            case STALKER -> new Stalker(world, position);
            case WASP -> new Wasp(world, position);
            case ULTRON -> new Ultron(world, position);
            case GOLEM -> new Golem(world, position);
            case STELLAR -> new Stellar(world, position);
        };
    }
}
