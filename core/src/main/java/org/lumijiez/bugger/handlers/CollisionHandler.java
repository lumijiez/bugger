package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.player.Player;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.entities.weapons.Projectile;
import org.lumijiez.bugger.util.functional.CollisionAction;
import org.lumijiez.bugger.util.data.CollisionPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollisionHandler implements ContactListener {

    private final List<Map.Entry<CollisionPair, CollisionAction>> collisionHandlers = new ArrayList<>();

    public CollisionHandler() {
        initializeCollisionHandlers();
    }

    private void initializeCollisionHandlers() {
        registerCollisionHandler(Projectile.class, Player.class, (rayObj, playerObj) -> {
            Projectile ray = (Projectile) rayObj;
            if (ray.isEnemy()) handlePlayerHit(ray);
        });

        registerCollisionHandler(Projectile.class, EnemyEntity.class, (rayObj, enemyObj) -> {
            Projectile ray = (Projectile) rayObj;
            EnemyEntity enemy = (EnemyEntity) enemyObj;
            if (!ray.isEnemy()) handleEnemyHit(ray, enemy);
        });
    }

    private void registerCollisionHandler(Class<?> typeA, Class<?> typeB, CollisionAction action) {
        CollisionPair pair = new CollisionPair(typeA, typeB);
        collisionHandlers.add(Map.entry(pair, action));

        CollisionPair reversePair = new CollisionPair(typeB, typeA);
        collisionHandlers.add(Map.entry(reversePair, (objA, objB) -> action.handle(objB, objA)));
    }

    private void handlePlayerHit(Projectile ray) {
        ray.destroy();
        Player.getInstance().damage(50);
        ParticleHandler instance = ParticleHandler.getInstance();
        Player player = Player.getInstance();

        float viewWidth = CameraHandler.getInstance().getUICamera().viewportWidth;
        float viewHeight = CameraHandler.getInstance().getUICamera().viewportHeight;

        instance.playSmallBoom(player.getPosition().x, player.getPosition().y);
        instance.playHit( viewWidth - 100, viewHeight - 60);
    }

    private void handleEnemyHit(Projectile ray, EnemyEntity enemy) {
        Bugger.kills++;
        ray.destroy();
        enemy.destroy();
    }

    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        if (userDataA == null || userDataB == null) return;

        for (var entry : collisionHandlers) {
            CollisionPair pair = entry.getKey();
            if (pair.matches(userDataA, userDataB)) {
                entry.getValue().handle(userDataA, userDataB);
                return;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
