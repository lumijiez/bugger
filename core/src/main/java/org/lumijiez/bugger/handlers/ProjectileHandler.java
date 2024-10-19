package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.weapons.Projectile;
import org.lumijiez.bugger.entities.weapons.Ray;

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

    public void shootRay() {
        Vector2 direction = new Vector2();

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        Vector3 mousePosition = CameraHandler.getInstance().getCamera().unproject(new Vector3(mouseX, mouseY, 0));

        Vector2 playerPos = Player.getInstance().getPosition();
        direction.set(mousePosition.x, mousePosition.y).sub(playerPos).nor();

        projectiles.add(new Ray(Bugger.getInstance().getWorld(), playerPos, direction));
    }

    public void shootRay(Vector2 position, Vector2 direction, float speed) {
        projectiles.add(new Ray(Bugger.getInstance().getWorld(), position, direction, speed));
    }


    public Array<Projectile> getProjectiles() {
        return projectiles;
    }
}
