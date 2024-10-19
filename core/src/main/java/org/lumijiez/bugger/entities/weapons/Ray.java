package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.Bugger;

public class Ray extends Projectile {
    float speed = 5000f;

    public Ray(World world) {
        super(world,"images/blaze.png", 5f);
        this.body.setUserData(this);
    }

    public Ray(World world, Vector2 position, Vector2 direction) {
        super(world, position, direction, "images/blaze.png", 5f);
        this.body.setUserData(this);
    }

    public Ray(World world, Vector2 position, Vector2 direction, float speed) {
        super(world, position, direction, "images/blaze.png", 5f, speed);
        this.speed = speed;
        this.body.setUserData(this);
    }

    public void init(Vector2 position, Vector2 direction) {
        this.body.setTransform(position, (float) (direction.angleRad() + Math.toRadians(270f)));
        this.body.setLinearVelocity(direction.nor().scl(speed));
    }

    public void reset() {
        timeAlive = 0f;
        markedToDestroy = false;
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
