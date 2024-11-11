package org.lumijiez.bugger.entities.enemies.behaviors;

import com.badlogic.gdx.math.Vector2;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.player.Player;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.handlers.EnemyProjectileHandler;

public class FollowBehavior implements EnemyBehavior {
    private float shootTimer = 0.0f;
    private final float shootCooldown;
    private final float moveSpeed;
    private final float projectileSpeed;

    public FollowBehavior(float shootCooldown, float moveSpeed, float projectileSpeed) {
        this.shootCooldown = shootCooldown;
        this.moveSpeed = moveSpeed;
        this.projectileSpeed = projectileSpeed;
    }

    @Override
    public void init(EnemyEntity enemy) {
        shootTimer = 0.0f;
    }

    @Override
    public void update(EnemyEntity enemy) {
        Vector2 playerPos = Player.getInstance().getPosition();
        Vector2 direction = playerPos.cpy().sub(enemy.getPosition()).nor();

        enemy.getBody().setLinearVelocity(direction.scl(moveSpeed));

        float angle = direction.angleDeg() + 270f;
        enemy.getBody().setTransform(enemy.getPosition(), angle * (float) Math.PI / 180f);

        shootTimer += Bugger.deltaTime;
        if (shootTimer >= shootCooldown) {
            EnemyProjectileHandler.getInstance().shootEnemyProjectile(enemy.getPosition(), projectileSpeed);
            shootTimer = 0.0f;
        }
    }
}
