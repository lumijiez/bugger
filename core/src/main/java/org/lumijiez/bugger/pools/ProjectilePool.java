package org.lumijiez.bugger.pools;

import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.weapons.Ray;

public class ProjectilePool {
    private final Array<Ray> deployedProjectiles = new Array<>();
    private final Array<Ray> freeProjectiles = new Array<>();
    private static final int INITIAL_PROJECTILES = 100;

    public ProjectilePool(boolean isEnemy) {
        for (int i = 0; i < INITIAL_PROJECTILES; i++) {
            freeProjectiles.add(new Ray(Bugger.getInstance().getWorld(), isEnemy));
        }
    }

    public Ray obtain() {
        Ray projectile;
        if (freeProjectiles.size > 0) {
            projectile = freeProjectiles.pop();
        } else if (deployedProjectiles.size > 0) {
            projectile = deployedProjectiles.first();
            deployedProjectiles.removeIndex(0);
        } else {
            return null;
        }
        deployedProjectiles.add(projectile);
        return projectile;
    }

    public void free(Ray ray) {
        ray.reset();
        deployedProjectiles.removeValue(ray, true);
        freeProjectiles.add(ray);
    }

    public void updateAndRender(float delta) {
        for (int i = 0; i < deployedProjectiles.size; i++) {
            Ray ray = deployedProjectiles.get(i);
            if (!ray.isMarkedToDestroy()) {
                ray.update(delta);
                ray.render();
            } else {
                free(ray);
                i--;
            }
        }
    }

    public Array<Ray> getDeployedProjectiles() {
        return deployedProjectiles;
    }
}
