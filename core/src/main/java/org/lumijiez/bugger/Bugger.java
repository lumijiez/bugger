package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.entities.player.Player;
import org.lumijiez.bugger.handlers.*;
import org.lumijiez.bugger.util.GameSystemsFacade;

public class Bugger {
    public static float deltaTime = 0f;
    private static Bugger instance;
    private final World world = new World(new Vector2(0, 0), true);
    public final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    public static ShapeRenderer shapeRenderer = new ShapeRenderer();
    public static SpriteBatch spriteBatch = new SpriteBatch();
    public static SpriteBatch uiBatch = new SpriteBatch();
    public static int kills = 0;

    private Bugger() {
        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());

        uiBatch.getProjectionMatrix().setToOrtho2D(0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());

        shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 1,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());

        Player.getInstance().setPlayer(world, 100, 100);
        this.world.setContactListener(new CollisionHandler());
    }

    public static Bugger getInstance() {
        if (instance == null) {
            instance = new Bugger();
        }
        return instance;
    }

    public void cycle(float delta) {
        deltaTime = delta;
        GameSystemsFacade.getInstance().update(delta);
    }

    public void dispose() {
        CleanupHandler.getInstance().disposeAll();
    }

    public void resize(int width, int height) {
        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        uiBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    public World getWorld() {
        return world;
    }
}
