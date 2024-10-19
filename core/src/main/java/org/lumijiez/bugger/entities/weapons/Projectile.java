package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.entities.Entity;

public abstract class Projectile extends Entity {

    private float timeAlive = 0f;

    public Projectile(World world, String texturePath, float size) {
        super(world, texturePath, size);
        this.body = createBody(0, 0);
    }

    public Projectile(World world, Vector2 position, Vector2 direction, String texturePath, float size) {
        super(world, texturePath, size);
        Vector2 offsetPosition = position.cpy().add(direction.nor().scl(size + 1f));
        this.body = createBody(offsetPosition.x, offsetPosition.y);
        this.body.setTransform(offsetPosition, (float) (direction.angleRad() + Math.toRadians(270f)));
        this.body.setLinearVelocity(direction.nor().scl(5000f));
    }

    public Projectile(World world, Vector2 position, Vector2 direction, String texturePath, float size, float speed) {
        super(world, texturePath, size);
        Vector2 offsetPosition = position.cpy().add(direction.nor().scl(size + 1f));
        this.body = createBody(offsetPosition.x, offsetPosition.y);
        this.body.setTransform(offsetPosition, (float) (direction.angleRad() + Math.toRadians(270f)));
        this.body.setLinearVelocity(direction.nor().scl(speed));
    }


    @Override
    protected Body createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size / 2, size + 5);

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
        if (timeAlive >= lifetime) {
            markedToDestroy = true;
        }
    }

    public void render() {
        super.render();
    }
}
