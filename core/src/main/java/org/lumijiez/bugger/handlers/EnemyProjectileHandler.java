package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.weapons.EnemyRay;
import org.lumijiez.bugger.pools.EnemyProjectilePool;

public class EnemyProjectileHandler {
    private static EnemyProjectileHandler instance;
    private final EnemyProjectilePool projectilePool;

    private EnemyProjectileHandler() {
        projectilePool = new EnemyProjectilePool(true);
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
        EnemyRay projectile = projectilePool.obtain();

        if (projectile != null) {
            projectile.init(position, shootDirection.scl(speed), true);
        }
    }

    public Array<EnemyRay> getDeployedEnemyProjectiles() {
        return projectilePool.getDeployedProjectiles();
    }
}
