package org.lumijiez.bugger.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.entities.Entity;
import org.lumijiez.bugger.entities.enemies.behaviors.Behaviors;
import org.lumijiez.bugger.entities.enemies.behaviors.EnemyBehavior;
import org.lumijiez.bugger.entities.enemies.types.EnemyType;
import org.lumijiez.bugger.util.data.TripleInt;

import java.util.Random;


public class EnemyEntity extends Entity {
    private static final Random random = new Random();
    protected EnemyBehavior behavior;
    protected EnemyType type;

    public EnemyEntity(World world, EnemyType type, Behaviors behaviorType, Vector2 playerPosition, TripleInt options) {
        super(world, type.getTexturePath(), type.getSize());
        this.type = type;
        this.behavior = behaviorType.createBehavior(options.one(), options.two(), options.thr());
        initializePosition(playerPosition);
        behavior.init(this);
    }

    private void initializePosition(Vector2 playerPosition) {
        float angle = random.nextFloat() * 2 * (float) Math.PI;
        float spawnX = playerPosition.x + (float) Math.cos(angle) * (type.getSpawnRadius() + type.getSize());
        float spawnY = playerPosition.y + (float) Math.sin(angle) * (type.getSpawnRadius() + type.getSize());
        this.body = createBody(spawnX, spawnY);
        this.body.setUserData(this);
    }

    public void update() {
        if (behavior != null) {
            behavior.update(this);
        }
    }

    public void cycle() {
        update();
        render(0, 0);
    }

    public void setBehavior(Behaviors behavior, float param1, float param2, float param3) {
        this.behavior = behavior.createBehavior(param1, param2, param3);
    }
}

