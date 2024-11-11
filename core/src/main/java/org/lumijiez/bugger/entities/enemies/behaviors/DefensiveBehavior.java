package org.lumijiez.bugger.entities.enemies.behaviors;

import com.badlogic.gdx.math.Vector2;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.player.Player;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.handlers.EnemyProjectileHandler;

public class DefensiveBehavior implements EnemyBehavior {
    private final float preferredDistance;
    private final float moveSpeed;
    private final float shootCooldown;
    private float shootTimer = 0.0f;

    public DefensiveBehavior(float preferredDistance, float moveSpeed, float shootCooldown) {
        this.preferredDistance = preferredDistance;
        this.moveSpeed = moveSpeed;
        this.shootCooldown = shootCooldown;
    }

    @Override
    public void init(EnemyEntity enemy) {
        shootTimer = 0.0f;
    }

    @Override
    public void update(EnemyEntity enemy) {
        Vector2 playerPos = Player.getInstance().getPosition();
        Vector2 enemyPos = enemy.getPosition();
        Vector2 direction = playerPos.cpy().sub(enemyPos);
        float currentDistance = direction.len();

        float distanceDiff = currentDistance - preferredDistance;
        direction.nor();

        if (Math.abs(distanceDiff) > 1.0f) {
            Vector2 movement = direction.scl(Math.min(moveSpeed, Math.abs(distanceDiff) * 0.5f));
            enemy.getBody().setLinearVelocity(movement.cpy().scl(Math.signum(distanceDiff)));
        } else {
            enemy.getBody().setLinearVelocity(Vector2.Zero);
        }

        float angle = direction.angleDeg() + 270f;
        enemy.getBody().setTransform(enemyPos, angle * (float) Math.PI / 180f);

        shootTimer += Bugger.deltaTime;
        if (shootTimer >= shootCooldown && Math.abs(distanceDiff) < 10f) {
            EnemyProjectileHandler.getInstance().shootEnemyProjectile(enemyPos, 40f);
            shootTimer = 0.0f;
        }
    }
}
