package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.entities.player.Player;
import org.lumijiez.bugger.entities.weapons.Projectile;
import org.lumijiez.bugger.pools.ProjectilePool;

public class EnemyProjectileHandler {
    private static EnemyProjectileHandler instance;
    private final ProjectilePool projectilePool;

    private EnemyProjectileHandler() {
        projectilePool = new ProjectilePool(true);
    }

    public static EnemyProjectileHandler getInstance() {
        if (instance == null) {
            instance = new EnemyProjectileHandler();
        }
        return instance;
    }

    public void cycle(float delta) {
        projectilePool.updateAndRender(delta);
    }

    public void shootEnemyProjectile(Vector2 position, float speed) {
        Vector2 playerPos = Player.getInstance().getPosition();
        Vector2 shootDirection = playerPos.cpy().sub(position).nor();
        Projectile projectile = projectilePool.obtain();

        if (projectile != null) {
            projectile.init(position, shootDirection.scl(speed), true);
        }
    }

    public Array<Projectile> getDeployedEnemyProjectiles() {
        return projectilePool.getDeployedProjectiles();
    }
}
