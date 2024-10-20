package org.lumijiez.bugger.pools;

import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.weapons.EnemyRay;

public class EnemyProjectilePool {
    private final Array<EnemyRay> deployedEnemyProjectiles = new Array<>();
    private final Array<EnemyRay> freeEnemyProjectiles = new Array<>();
    private static final int INITIAL_PROJECTILES = 100;

    public EnemyProjectilePool(boolean isEnemy) {
        for (int i = 0; i < INITIAL_PROJECTILES; i++) {
            freeEnemyProjectiles.add(new EnemyRay(Bugger.getInstance().getWorld(), isEnemy));
        }
    }

    public EnemyRay obtain() {
        EnemyRay projectile;
        if (freeEnemyProjectiles.size > 0) {
            projectile = freeEnemyProjectiles.pop();
        } else if (deployedEnemyProjectiles.size > 0) {
            projectile = deployedEnemyProjectiles.first();
            deployedEnemyProjectiles.removeIndex(0);
        } else {
            return null;
        }
        deployedEnemyProjectiles.add(projectile);
        return projectile;
    }

    public void free(EnemyRay ray) {
        ray.reset();
        deployedEnemyProjectiles.removeValue(ray, true);
        freeEnemyProjectiles.add(ray);
    }

    public void updateAndRender(float delta) {
        for (int i = 0; i < deployedEnemyProjectiles.size; i++) {
            EnemyRay ray = deployedEnemyProjectiles.get(i);
            if (!ray.isMarkedToDestroy()) {
                ray.update(delta);
                ray.render();
            } else {
                free(ray);
                i--;
            }
        }
    }

    public Array<EnemyRay> getDeployedProjectiles() {
        return deployedEnemyProjectiles;
    }
}
