package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.entities.Entity;

public class Arrow extends Entity {
    private float timeAlive = 0f;

    public Arrow(World world, Vector2 position, Vector2 direction) {
        super(world, "images/wasp.png", 1f);
        Vector2 offsetPosition = position.cpy().add(direction.nor().scl(size + 1f));

        this.body = createBody(offsetPosition.x, offsetPosition.y);
        float speed = 5000f;
        this.body.setLinearVelocity(direction.nor().scl(speed));
        this.body.setAngularVelocity(speed);
    }

    protected Body createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size / 2, size / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    public void update(float delta) {
        timeAlive += delta;
        float lifetime = 3f;
        if (timeAlive >= lifetime || isMarkedToDestroy()) {
            destroy();
        }
    }

    public void render() {
        super.render();
    }
}
