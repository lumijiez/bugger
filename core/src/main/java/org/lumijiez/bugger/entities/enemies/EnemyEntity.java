package org.lumijiez.bugger.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Entity;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.handlers.EnemyProjectileHandler;

public class EnemyEntity extends Entity {
    private float shootTimer = 0.0f;

    public EnemyEntity(World world, String texturePath, float size) {
        super(world, texturePath, size);
    }

//    public void update(Vector2 target) {
//        follow(target);
//    }

    public void update() {
        Vector2 playerPos = Player.getInstance().getPosition();
        follow(playerPos);
    }

    private void follow(Vector2 playerPos) {
        Vector2 direction = playerPos.cpy().sub(body.getPosition()).nor();
        float speed = 10f;
        body.setLinearVelocity(direction.scl(speed));

        float angle = direction.angleDeg() + 270f;
        body.setTransform(body.getPosition(), angle * (float) Math.PI / 180f);

        shootTimer += Bugger.deltaTime;

        float shootCooldown = 2.0f;
        if (shootTimer >= shootCooldown) {
            EnemyProjectileHandler.getInstance().shootEnemyProjectile(this.body.getPosition(), 50f);
            shootTimer = 0.0f;
        }
    }

//    public void cycle(Vector2 target) {
//        update(target);
//        render();
//    }

    public void cycle() {
        update();
        render();
    }

    public void render() {
        super.render();
    }
}
