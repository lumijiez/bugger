package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Entity;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;

import java.util.List;

public class CleanupHandler {
    private final Array<Entity> entitiesToDestroy = new Array<>();

    private static CleanupHandler instance;

    private CleanupHandler() {}

    public static CleanupHandler getInstance() {
        if (instance == null) {
            instance = new CleanupHandler();
        }
        return instance;
    }

    public void tryClean() {
        Array<Entity> entities = entitiesToDestroy;
        //Array<Projectile> projectiles = ProjectileHandler.getInstance().getDeployedProjectiles();
        List<EnemyEntity> enemies = EnemyHandler.getInstance().getEnemies();
        World world = Bugger.getInstance().getWorld();

        for (Entity entity : entities) {
            world.destroyBody(entity.getBody());

//            if (entity instanceof Projectile) {
//                projectiles.removeValue((Projectile) entity, true);
//            }

            if (entity instanceof EnemyEntity) {
                ParticleHandler.getInstance().playBigBoom(entity.getBody().getPosition().x, entity.getBody().getPosition().y);
                enemies.remove(entity);
            }

        }
        entities.clear();
    }

    public Array<Entity> getEntitiesToDestroy() {
        return entitiesToDestroy;
    }

    public void disposeAll() {
        Bugger.spriteBatch.dispose();
        Bugger.uiBatch.dispose();
        Bugger.getInstance().getWorld().dispose();
        SpaceVFXHandler.getInstance().dispose();
        ParticleHandler.getInstance().dispose();
    }
}
