package org.lumijiez.bugger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.lumijiez.bugger.entities.Player;
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

    public World getWorld() {
        return world;
    }
}
