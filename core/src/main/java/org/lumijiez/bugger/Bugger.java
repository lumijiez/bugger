package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.handlers.*;

public class Bugger {
    public static float deltaTime = 0f;
    private static Bugger instance;
    private final World world = new World(new Vector2(0, 0), true);
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    public static ShapeRenderer shapeRenderer = new ShapeRenderer();
    public static SpriteBatch spriteBatch = new SpriteBatch();
    public static SpriteBatch uiBatch = new SpriteBatch();
    public static int kills = 0;

    private Bugger() {
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
        renderClear();
        SpaceVFXHandler.getInstance().render();

        step();

        ProjectileHandler.getInstance().cycle(delta);
        EnemyProjectileHandler.getInstance().cycle(delta);
        EnemyHandler.getInstance().cycle();
        ParticleHandler.getInstance().cycle(delta);

        CleanupHandler.getInstance().tryClean();

        CameraHandler.getInstance().updateCamera();

        Player.getInstance().render();

        EnemyHandler.getInstance().render(delta);

        if (InterfaceHandler.getInstance().isDebug()) renderDebug();

        InterfaceHandler.getInstance().renderUI();
        InputHandler.getInstance().handleInput();
    }

    public void renderClear() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void renderDebug() {
        debugRenderer.render(world, spriteBatch.getProjectionMatrix());
    }

    public void step() {
        world.step((float) 1 / 30f, 6, 2);
    }

    public void dispose() {
        CleanupHandler.getInstance().disposeAll();
    }

    public World getWorld() {
        return world;
    }
}
