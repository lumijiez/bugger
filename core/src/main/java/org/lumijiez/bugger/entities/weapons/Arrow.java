package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.entities.Entity;

public class Arrow extends Entity {
    private final float speed = 4000f;
    private final float lifetime = 3f;
    private float timeAlive = 0f;

    public Arrow(World world, Vector2 position, Vector2 direction) {
        super(world, "images/wasp.png", 10f);
        Vector2 offsetPosition = position.cpy().add(direction.nor().scl(size + 15f));

        this.body = createBody(offsetPosition.x, offsetPosition.y);
        this.body.setLinearVelocity(direction.nor().scl(speed));
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
        if (timeAlive >= lifetime || isMarkedToDestroy()) {
            destroy();
        }
    }

    public void render() {
        super.render();
    }
}
