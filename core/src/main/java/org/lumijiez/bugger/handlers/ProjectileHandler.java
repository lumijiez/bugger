package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.weapons.Projectile;
import org.lumijiez.bugger.pools.ProjectilePool;

public class ProjectileHandler {
    private static ProjectileHandler instance;
    private final ProjectilePool projectilePool;

    private ProjectileHandler() {
        projectilePool = new ProjectilePool(false);
    }

    public static ProjectileHandler getInstance() {
        if (instance == null) {
            instance = new ProjectileHandler();
        }
        return instance;
    }

    public void cycle(float delta) {
        projectilePool.updateAndRender(delta);
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
        Projectile projectile = projectilePool.obtain();
        if (projectile != null) {
            projectile.init(position, direction.nor().scl(speed), false);
        }
    }

    public Array<Projectile> getDeployedProjectiles() {
        return projectilePool.getDeployedProjectiles();
    }
}
