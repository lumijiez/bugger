package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.entities.Entity;

public class Projectile extends Entity {
    private static final String ENEMY_TEXTURE = "images/enemyblaze.png";
    private static final String PLAYER_TEXTURE = "images/blaze.png";

    protected float timeAlive = 0f;
    protected boolean isEnemy;
    protected float speed = 5000f;

    public Projectile(World world, boolean isEnemy) {
        super(world, isEnemy ? ENEMY_TEXTURE : PLAYER_TEXTURE, 5f);
        this.isEnemy = isEnemy;
        this.body = createBody(0, 0);
    }

    public Projectile(World world, Vector2 position, Vector2 direction, boolean isEnemy) {
        super(world, isEnemy ? ENEMY_TEXTURE : PLAYER_TEXTURE, 5f);
        Vector2 offsetPosition = position.cpy().add(direction.nor().scl(5f + 1f));
        this.isEnemy = isEnemy;
        this.body = createBody(offsetPosition.x, offsetPosition.y);
        this.body.setTransform(offsetPosition, (float) (direction.angleRad() + Math.toRadians(270f)));
        this.body.setLinearVelocity(direction.nor().scl(speed));
        this.body.setUserData(this);
    }

    public Projectile(World world, Vector2 position, Vector2 direction, boolean isEnemy, float speed) {
        super(world, isEnemy ? ENEMY_TEXTURE : PLAYER_TEXTURE, 5f);
        Vector2 offsetPosition = position.cpy().add(direction.nor().scl(5f + 1f));
        this.isEnemy = isEnemy;
        this.speed = speed;
        this.body = createBody(offsetPosition.x, offsetPosition.y);
        this.body.setTransform(offsetPosition, (float) (direction.angleRad() + Math.toRadians(270f)));
        this.body.setLinearVelocity(direction.nor().scl(speed));
        this.body.setUserData(this);
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

    public void update(float delta) {
        timeAlive += delta;
        float lifetime = 3f;
        if (timeAlive >= lifetime) {
            markedToDestroy = true;
        }
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void render() {
        super.render(0, 5);
    }

    @Override
    public void destroy() {
        markedToDestroy = true;
    }
}
