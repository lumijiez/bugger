package org.lumijiez.bugger.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class Ultron extends EnemyEntity {
    private static final Random random = new Random();

    public Ultron(World world, Vector2 playerPosition) {
        super(world, "images/ultron.png", 10f);
        float spawnRadius = 100;
        float angle = random.nextFloat() * 2 * (float) Math.PI;
        float spawnX = playerPosition.x + (float) Math.cos(angle) * (spawnRadius + size);
        float spawnY = playerPosition.y + (float) Math.sin(angle) * (spawnRadius + size);
        this.body = createBody(spawnX, spawnY);
        this.body.setUserData(this);
    }
}
