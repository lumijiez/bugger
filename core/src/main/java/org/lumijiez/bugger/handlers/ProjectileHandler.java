package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.weapons.Ray;

public class ProjectileHandler {
    private final Array<Ray> deployedProjectiles = new Array<>();
    private final Array<Ray> freeProjectiles = new Array<>();
    private final Array<Ray> deployedEnemyProjectiles = new Array<>();
    private final Array<Ray> freeEnemyProjectiles = new Array<>();
    private static ProjectileHandler instance;
    private static final int INITIAL_PROJECTILES = 50;

    private ProjectileHandler() {
        for (int i = 0; i < INITIAL_PROJECTILES; i++) {
            freeProjectiles.add(new Ray(Bugger.getInstance().getWorld(), false));
        }
    }

    public static ProjectileHandler getInstance() {
        if (instance == null) {
            instance = new ProjectileHandler();
        }
        return instance;
    }

    public void cycle(float delta) {
        for (int i = 0; i < deployedProjectiles.size; i++) {
            Ray ray = deployedProjectiles.get(i);
            if (!ray.isMarkedToDestroy()) {
                ray.update(delta);
                ray.render();
            } else {
                ray.reset();
                freeProjectiles.add(ray);
                deployedProjectiles.removeIndex(i);
                i--;
            }
        }
    }

    public void shootRay() {
        Vector2 direction = new Vector2();

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        Vector3 mousePosition = CameraHandler.getInstance().getCamera().unproject(new Vector3(mouseX, mouseY, 0));
        Vector2 playerPos = Player.getInstance().getPosition();
        direction.set(mousePosition.x, mousePosition.y).sub(playerPos).nor();

        shootRay(playerPos, direction, 20f);
    }

    public void shootRay(Vector2 position, Vector2 direction, float speed) {
        Ray projectile;

        if (freeProjectiles.size > 0) {
            projectile = freeProjectiles.pop();
        } else if (deployedProjectiles.size > 0) {
            projectile = deployedProjectiles.first();
            deployedProjectiles.removeIndex(0);
        } else {
            return;
        }

        projectile.init(position, direction.nor().scl(speed), false);
        deployedProjectiles.add(projectile);
    }

    public Array<Ray> getDeployedProjectiles() {
        return deployedProjectiles;
    }

    public Array<Ray> getDeployedEnemyProjectiles() {
        return deployedEnemyProjectiles;
    }
}
