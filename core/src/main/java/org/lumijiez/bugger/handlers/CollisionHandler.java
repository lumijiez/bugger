package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.entities.weapons.EnemyProjectile;
import org.lumijiez.bugger.entities.weapons.EnemyRay;
import org.lumijiez.bugger.entities.weapons.Ray;

public class CollisionHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        if (isEnemy(fixtureA) && isPlayer(fixtureB)) {
            EnemyRay ray = (EnemyRay) userDataA;
            if (ray.isEnemy()) {
                ray.destroy();
                Player.getInstance().damage(50);
                ParticleHandler.getInstance().playSmallBoom(Player.getInstance().getPosition().x, Player.getInstance().getPosition().y);
                ParticleHandler.getInstance().playHit(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 60);
            }
        }

        if (isEnemy(fixtureB) && isPlayer(fixtureA)) {
            EnemyRay ray = (EnemyRay) userDataB;
            if (ray.isEnemy()) {
                ray.destroy();
                Player.getInstance().damage(50);
                ParticleHandler.getInstance().playSmallBoom(Player.getInstance().getPosition().x, Player.getInstance().getPosition().y);
                ParticleHandler.getInstance().playHit(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 60);
            }
        }



        if (isArrow(fixtureA) && isEntity(fixtureB)) {
            Ray ray = (Ray) fixtureA.getBody().getUserData();
            EnemyEntity enemy = (EnemyEntity) fixtureB.getBody().getUserData();
            if (ray != null && !ray.isEnemy()) {
                Bugger.kills++;
                ray.destroy();
                enemy.destroy();
            }
        }

        if (isArrow(fixtureB) && isEntity(fixtureA)) {
            Ray ray = (Ray) fixtureB.getBody().getUserData();
            EnemyEntity enemy = (EnemyEntity) fixtureA.getBody().getUserData();
            if (ray != null && !ray.isEnemy()) {
                Bugger.kills++;
                ray.destroy();
                enemy.destroy();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private boolean isArrow(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Ray;
    }

    private boolean isEnemy(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof EnemyProjectile;
    }

    private boolean isPlayer(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Player;
    }

    private boolean isEntity(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof EnemyEntity;
    }
}
