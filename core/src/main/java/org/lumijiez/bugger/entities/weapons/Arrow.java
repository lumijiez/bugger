package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.Bugger;

public class Arrow extends Projectile {

    public Arrow(World world, Vector2 position, Vector2 direction) {
        super(world, "images/arrow.png", 5f);

        Vector2 offsetPosition = position.cpy().add(direction.nor().scl(size + 1f));

        this.body = createBody(offsetPosition.x, offsetPosition.y);

        this.body.setTransform(offsetPosition, (float) (direction.angleRad() + Math.toRadians(270f)));

        float speed = 5000f;

        this.body.setLinearVelocity(direction.nor().scl(speed));
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
