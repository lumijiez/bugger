package org.lumijiez.bugger.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import org.lumijiez.bugger.handlers.CameraHandler;

import static org.lumijiez.bugger.Bugger.shapeRenderer;

public class Player extends Entity {
    private static Player instance;

    private int health = 1000;

    private Player() {
        super(null, "images/wasp.png", 10f);
    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    @Override
    protected Body createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size / 2, size / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);

        shape.dispose();
        return body;
    }


    public void setPlayer(World world, float x, float y) {
        this.world = world;
        this.body = createBody(x, y);
        this.body.setUserData(this);
    }

    public void move(float deltaX, float deltaY) {
        float speed = 500f;
        Vector2 force = new Vector2(deltaX * speed, deltaY * speed);

        body.applyForceToCenter(force, true);
    }
    public void render() {
        handleInput();
        updateSpriteRotation();
        super.render(0, 0);
        renderHealthBar();
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

        Vector3 mousePosition = CameraHandler.getInstance().getCamera().unproject(new Vector3(mouseX, mouseY, 0));

        Vector2 direction = new Vector2(mousePosition.x, mousePosition.y).sub(body.getPosition()).nor();

        float angle = direction.angleDeg() + 270f;
        body.setTransform(body.getPosition(), angle * (float) Math.PI / 180f);
        sprite.setRotation(body.getAngle() * (180f / (float) Math.PI));
    }

    public void damage(int damage) {
        health -= damage;
    }

    private void renderHealthBar() {
        float maxHealth = 1000f;
        float healthPercentage = Math.max(health / maxHealth, 0);
        float healthBarWidth = 8f;
        float healthBarHeight = 1f;
        float healthBarX = body.getPosition().x - healthBarWidth / 2;
        float healthBarY = body.getPosition().y + size - 17;

        shapeRenderer.setProjectionMatrix(CameraHandler.getInstance().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1);
        shapeRenderer.rect(healthBarX, healthBarY, healthBarWidth , healthBarHeight);

        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(healthBarX, healthBarY, healthBarWidth * healthPercentage, healthBarHeight);

        shapeRenderer.end();
    }
}
