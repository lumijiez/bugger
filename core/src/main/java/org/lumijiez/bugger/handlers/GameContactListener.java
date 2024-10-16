package org.lumijiez.bugger.handlers;

import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.entities.enemies.EnemyEntity;
import org.lumijiez.bugger.entities.weapons.Arrow;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (isArrow(fixtureA) && isArrow(fixtureB)) {
            return;
        }

        if (isArrow(fixtureA) && isEntity(fixtureB)) {
            Arrow arrow = (Arrow) fixtureA.getBody().getUserData();
            EnemyEntity enemy = (EnemyEntity) fixtureB.getBody().getUserData();
            if (arrow != null) {
                arrow.destroy();
                enemy.destroy();
            }
        }

        if (isArrow(fixtureB) && isEntity(fixtureA)) {
            Arrow arrow = (Arrow) fixtureB.getBody().getUserData();
            EnemyEntity enemy = (EnemyEntity) fixtureA.getBody().getUserData();
            if (arrow != null) {
                arrow.destroy();
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
        return fixture.getBody().getUserData() instanceof Arrow;
    }

    private boolean isEntity(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof EnemyEntity;
    }
}
