package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;

import static org.lumijiez.bugger.GameScreen.spriteBatch;

public class Player {
    private static Player instance;
    private Body body;
    private final float size = 50f;
    private final float speed = 5f;
    public World world;
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("images/wasp.png")));

    private Player() { }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void setPlayer(World world, float x, float y) {
        this.world = world;
        this.body = createBody(x, y);
    }

    public void move(float deltaX, float deltaY) {
        body.setLinearVelocity(deltaX * speed, deltaY * speed);
    }

    private Body createBody(float x, float y) {
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
        handleInput();
        sprite.setSize(size, size);
        sprite.setPosition(body.getPosition().x - size / 2, body.getPosition().y - size / 2); // Center the sprite
        sprite.setRotation(body.getAngle() * (180f / (float) Math.PI)); // Convert radians to degrees
        spriteBatch.begin(); // Start batch
        sprite.draw(spriteBatch); // Draw the enemy sprite
        spriteBatch.end();
    }

    public void handleInput() {
        Vector2 direction = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) direction.x = -1;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) direction.x = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) direction.y = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.S)) direction.y = -1;

        if (direction.len() > 1) direction.nor();

        move(direction.x, direction.y);
    }

    public Body getBody() {
        return body;
    }

    public float getSize() {
        return size;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
