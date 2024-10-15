package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Random;

import static org.lumijiez.bugger.GameScreen.spriteBatch;

public class Enemy {
    private Body body;
    private final float size = 20f;
    private final float speed = 50f;
    private final Vector2 position;
    private static final Random random = new Random();
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("images/wasp.png")));

    public Enemy(World world, Vector2 playerPosition) {
        float spawnRadius = 100;
        float angle = random.nextFloat() * 2 * (float) Math.PI;
        float spawnX = playerPosition.x + (float) Math.cos(angle) * (spawnRadius + size);
        float spawnY = playerPosition.y + (float) Math.sin(angle) * (spawnRadius + size);
        this.position = new Vector2(spawnX, spawnY);
        this.body = createBody(world, position.x, position.y);
    }

    private Body createBody(World world, float x, float y) {
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

    public void moveTowards(Vector2 target) {
        Vector2 direction = target.cpy().sub(body.getPosition()).nor();
        body.setLinearVelocity(direction.scl(speed / 100f));

        float angle = direction.angleDeg() + 270f;
        body.setTransform(body.getPosition(), angle * (float) Math.PI / 180f);
    }

    public void render() {
        sprite.setOrigin(size / 2, size / 2);
        sprite.setSize(size, size);
        sprite.setPosition(body.getPosition().x - size / 2, body.getPosition().y - size / 2);
        sprite.setRotation(body.getAngle() * (180f / (float) Math.PI));
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }


    public Vector2 getPosition() {
        return body.getPosition();
    }
}
