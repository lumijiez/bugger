package org.lumijiez.bugger.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.entities.Entity;

public class EnemyEntity extends Entity {
    public EnemyEntity(World world, String texturePath, float size) {
        super(world, texturePath, size);
    }

    public void moveTowards(Vector2 target) {
        Vector2 direction = target.cpy().sub(body.getPosition()).nor();
        float speed = 10f;
        body.setLinearVelocity(direction.scl(speed));

        float angle = direction.angleDeg() + 270f;
        body.setTransform(body.getPosition(), angle * (float) Math.PI / 180f);
    }

    public void render() {
        super.render();
    }
}
