package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.entities.weapons.Projectile;

public class ProjectileHandler {
    private final Array<Projectile> projectiles = new Array<>();
    private static ProjectileHandler instance;

    private ProjectileHandler() {}

    public static ProjectileHandler getInstance() {
        if (instance == null) {
            instance = new ProjectileHandler();
        }
        return instance;
    }

    public void cycle(float delta) {
        for (Projectile arrow : projectiles) {
            if (!arrow.isMarkedToDestroy()) {
                arrow.update(delta);
                arrow.render();
            } else {
                CleanupHandler.getInstance().getEntitiesToDestroy().add(arrow);
            }
        }
    }

    public Array<Projectile> getProjectiles() {
        return projectiles;
    }
}
