package org.lumijiez.bugger.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    private static Player instance;
    private final float speed = 5f;

    private Player() {
        super(null, "images/wasp.png", 50f); // World will be set later
    }

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

    public void render() {
        handleInput();
        updateSpriteRotation();
        super.render();
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

    private void updateSpriteRotation() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();
        Vector2 mousePosition = new Vector2(mouseX, Gdx.graphics.getHeight() - mouseY);
        Vector2 direction = mousePosition.cpy().sub(body.getPosition()).nor();
        float angle = direction.angleDeg() + 270f;
        body.setTransform(body.getPosition(), angle * (float) Math.PI / 180f);
        sprite.setRotation(body.getAngle() * (180f / (float) Math.PI));
    }
}
