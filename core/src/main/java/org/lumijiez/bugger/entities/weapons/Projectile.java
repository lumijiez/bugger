package org.lumijiez.bugger.entities.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.Bugger;
import org.lumijiez.bugger.entities.Entity;
import org.lumijiez.bugger.pools.ProjectileFlyweight;

import static org.lumijiez.bugger.pools.ProjectileFlyweight.ENEMY_TEXTURE;
import static org.lumijiez.bugger.pools.ProjectileFlyweight.PLAYER_TEXTURE;

public class Projectile extends Entity {
    protected float timeAlive = 0f;
    protected boolean isEnemy;
    protected float speed;
    private final ProjectileFlyweight flyweight;

    public Projectile(World world, boolean isEnemy) {
        super(world, isEnemy ? ENEMY_TEXTURE : PLAYER_TEXTURE, 5f);
        this.flyweight = ProjectileFlyweight.get(isEnemy);
        this.isEnemy = isEnemy;
        this.speed = flyweight.getDefaultSpeed();
        this.body = createBody(0, 0);
    }

    public Projectile(World world, Vector2 position, Vector2 direction, boolean isEnemy) {
        super(world, isEnemy ? ENEMY_TEXTURE : PLAYER_TEXTURE, 5f);
        this.flyweight = ProjectileFlyweight.get(isEnemy);
        Vector2 offsetPosition = position.cpy().add(direction.nor().scl(5f + 1f));
        this.isEnemy = isEnemy;
        this.speed = flyweight.getDefaultSpeed();
        this.body = createBody(offsetPosition.x, offsetPosition.y);
        this.body.setTransform(offsetPosition, (float) (direction.angleRad() + Math.toRadians(270f)));
        this.body.setLinearVelocity(direction.nor().scl(speed));
        this.body.setUserData(this);
    }

    public Projectile(World world, Vector2 position, Vector2 direction, boolean isEnemy, float speed) {
        super(world, isEnemy ? ENEMY_TEXTURE : PLAYER_TEXTURE, 5f);
        this.flyweight = ProjectileFlyweight.get(isEnemy);
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
        shape.setAsBox(flyweight.getSize() / 2, flyweight.getSize() + 5);

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
        Sprite spriteToRender = flyweight.getSprite();
        spriteToRender.setOrigin(flyweight.getSize() / 2, flyweight.getSize() / 2);
        spriteToRender.setSize(flyweight.getSize(), flyweight.getSize() + 5);
        spriteToRender.setPosition(body.getPosition().x - flyweight.getSize() / 2,
            body.getPosition().y - flyweight.getSize() / 2);
        spriteToRender.setRotation(body.getAngle() * (180f / (float) Math.PI));
        Bugger.spriteBatch.begin();
        spriteToRender.draw(Bugger.spriteBatch);
        Bugger.spriteBatch.end();
    }

    @Override
    public void destroy() {
        markedToDestroy = true;
    }
}
