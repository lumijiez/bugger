package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.enemies.Enemies;
import org.lumijiez.bugger.factories.EnemyFactory;

public class EnemySpawner {
    private static EnemySpawner instance;
    private float enemySpawnTimer = 0f;
    private static final float ENEMY_SPAWN_INTERVAL = 0.5f;

    private EnemySpawner() {}

    public static EnemySpawner getInstance() {
        if (instance == null) {
            instance = new EnemySpawner();
        }
        return instance;
    }

    public void cycle(float delta) {
        enemySpawnTimer += delta;
        if (enemySpawnTimer >= ENEMY_SPAWN_INTERVAL) {
            World world = Bugger.getInstance().getWorld();
            Vector2 playerPos = Bugger.getInstance().getPlayer().getPosition();
            trySpawnRandom(world, playerPos);
            enemySpawnTimer = 0;
        }
    }

    public void spawn(Enemies enemy) {
        World world = Bugger.getInstance().getWorld();
        Vector2 playerPos = Bugger.getInstance().getPlayer().getPosition();
        Bugger.getInstance().getEnemies().add(EnemyFactory.createEnemy(enemy, world, playerPos));
    }

    public void spawn(Enemies enemy, World world, Vector2 position) {
        Bugger.getInstance().getEnemies().add(EnemyFactory.createEnemy(enemy, world, position));
    }

    private void trySpawnRandom(World world, Vector2 position) {
        Bugger.getInstance().getEnemies().add(EnemyFactory.createRandomEnemy(world, position));
    }
}
