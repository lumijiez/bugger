package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.weapons.EnemyRay;

public class EnemyProjectileHandler {
    private final Array<EnemyRay> deployedEnemyProjectiles = new Array<>();
    private final Array<EnemyRay> freeEnemyProjectiles = new Array<>();
    private static EnemyProjectileHandler instance;
    private static final int INITIAL_PROJECTILES = 50;

    private EnemyProjectileHandler() {
        for (int i = 0; i < INITIAL_PROJECTILES; i++) {
            freeEnemyProjectiles.add(new EnemyRay(Bugger.getInstance().getWorld(), true));
        }
    }

    public static EnemyProjectileHandler getInstance() {
        if (instance == null) {
            instance = new EnemyProjectileHandler();
        }
        return instance;
    }

    public void cycle(float delta) {
        for (int i = 0; i < deployedEnemyProjectiles.size; i++) {
            EnemyRay ray = deployedEnemyProjectiles.get(i);
            if (!ray.isMarkedToDestroy()) {
                ray.update(delta);
                ray.render();
            } else {
                ray.reset();
                freeEnemyProjectiles.add(ray);
                deployedEnemyProjectiles.removeIndex(i);
                i--;
            }
        }
    }

    public void shootEnemyProjectile(Vector2 position, Vector2 direction, float speed) {
        EnemyRay projectile;

        Vector2 playerPos = Player.getInstance().getPosition();

        Vector2 shootDirection = playerPos.cpy().sub(position).nor();

        if (freeEnemyProjectiles.size > 0) {
            projectile = freeEnemyProjectiles.pop();
        } else if (deployedEnemyProjectiles.size > 0) {
            projectile = deployedEnemyProjectiles.first();
            deployedEnemyProjectiles.removeIndex(0);
        } else {
            return;
        }

        projectile.init(position, shootDirection.scl(speed), true);
        deployedEnemyProjectiles.add(projectile);
    }

}
