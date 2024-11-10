package org.lumijiez.bugger.pools;

import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.weapons.Projectile;

public class ProjectilePool {
    private final Array<Projectile> deployedProjectiles = new Array<>();
    private final Array<Projectile> freeProjectiles = new Array<>();
    private static final int INITIAL_PROJECTILES = 100;
    private final boolean isEnemy;

    public ProjectilePool(boolean isEnemy) {
        this.isEnemy = isEnemy;
        initializePool();
    }

    private void initializePool() {
        for (int i = 0; i < INITIAL_PROJECTILES; i++) {
            freeProjectiles.add(new Projectile(Bugger.getInstance().getWorld(), isEnemy));
        }
    }

    public Projectile obtain() {
        Projectile projectile;
        if (freeProjectiles.size > 0) {
            projectile = freeProjectiles.pop();
        } else if (deployedProjectiles.size > 0) {
            projectile = deployedProjectiles.first();
            deployedProjectiles.removeIndex(0);
        } else {
            projectile = new Projectile(Bugger.getInstance().getWorld(), isEnemy);
        }
        deployedProjectiles.add(projectile);
        return projectile;
    }

    public void free(Projectile ray) {
        if (ray != null && deployedProjectiles.contains(ray, true)) {
            ray.reset();
            deployedProjectiles.removeValue(ray, true);
            freeProjectiles.add(ray);
        }
    }

    public void updateAndRender(float delta) {
        for (int i = deployedProjectiles.size - 1; i >= 0; i--) {
            Projectile ray = deployedProjectiles.get(i);
            if (!ray.isMarkedToDestroy()) {
                ray.update(delta);
                ray.render();
            } else {
                free(ray);
            }
        }
    }

    public void freeAll() {
        for (Projectile ray : deployedProjectiles) {
            free(ray);
        }
    }

    public Array<Projectile> getDeployedProjectiles() {
        return deployedProjectiles;
    }

    public int getActiveProjectileCount() {
        return deployedProjectiles.size;
    }

    public int getFreeProjectileCount() {
        return freeProjectiles.size;
    }
}
