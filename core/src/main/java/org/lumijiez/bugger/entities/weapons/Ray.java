package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.Bugger;

public class Ray extends Projectile {

    public Ray(World world, Vector2 position, Vector2 direction) {
        super(world, position, direction, "images/blaze.png", 5f);

        this.body.setUserData(this);
    }

    public Ray(World world, Vector2 position, Vector2 direction, float speed) {
        super(world, position, direction, "images/blaze.png", 5f, speed);

        this.body.setUserData(this);
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
