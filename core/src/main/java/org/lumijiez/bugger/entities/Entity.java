package org.lumijiez.bugger.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.lumijiez.bugger.Bugger;

public abstract class Entity {
    protected Body body;
    protected Sprite sprite;
    protected final float size;
    protected World world;
    protected boolean markedToDestroy = false;

    public Entity(World world, String texturePath, float size) {
        this.world = world;
        this.size = size;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));
    }

    protected Body createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size / 2, size / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }

    public void render() {
        sprite.setOrigin(size / 2, size / 2);
        sprite.setSize(size, size);
        sprite.setPosition(body.getPosition().x - size / 2, body.getPosition().y - size / 2);
        sprite.setRotation(body.getAngle() * (180f / (float) Math.PI));
        Bugger.spriteBatch.begin();
        sprite.draw(Bugger.spriteBatch);
        Bugger.spriteBatch.end();
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void destroy() {
        if (!markedToDestroy) {
            markedToDestroy = true;
        }
    }

    public boolean isMarkedToDestroy() {
        return markedToDestroy;
    }

    public Body getBody() {
        return body;
    }
}
