package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.Bugger;

public class EnemyRay extends EnemyProjectile {
    float speed = 5000f;

    public EnemyRay(World world, boolean isEnemy) {
        super(world,"images/enemyblaze.png", 5f, isEnemy);
    }

    public EnemyRay(World world, Vector2 position, Vector2 direction) {
        super(world, position, direction, "images/enemyblaze.png", 5f);
        this.body.setUserData(this);
    }

    public EnemyRay(World world, Vector2 position, Vector2 direction, float speed) {
        super(world, position, direction, "images/enemyblaze.png", 5f, speed);
        this.speed = speed;
        this.body.setUserData(this);
    }

    public void init(Vector2 position, Vector2 direction, boolean isEnemy) {
        this.isEnemy = isEnemy;
        this.body.setTransform(position, (float) (direction.angleRad() + Math.toRadians(270f)));
        this.body.setLinearVelocity(direction.nor().scl(speed));
        this.body.setUserData(this);
    }

    public void reset() {
        timeAlive = 0f;
        markedToDestroy = false;
        this.isEnemy = false;
        this.body.setLinearVelocity(Vector2.Zero);
        this.body.setTransform(Vector2.Zero, 0f);
    }

    @Override
    public void render() {
        sprite.setOrigin(size / 2, size / 2);
        sprite.setSize(size, size + 5);
        sprite.setPosition(body.getPosition().x - size / 2, body.getPosition().y - size / 2);
        sprite.setRotation(body.getAngle() * (180f / (float) Math.PI));
        Bugger.spriteBatch.begin();
        sprite.draw(Bugger.spriteBatch);
        Bugger.spriteBatch.end();
    }
}
