package org.lumijiez.bugger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.lumijiez.bugger.entities.Entity;
import org.lumijiez.bugger.entities.Player;
import org.lumijiez.bugger.entities.enemies.*;
import org.lumijiez.bugger.entities.weapons.Ray;
import org.lumijiez.bugger.entities.weapons.Projectile;
import org.lumijiez.bugger.handlers.*;
import org.lumijiez.bugger.vfx.ParticleManager;
import org.lumijiez.bugger.vfx.SpaceBackground;

import java.util.ArrayList;
import java.util.List;

public class Bugger {
    private static Bugger instance;
    private final World world = new World(new Vector2(0, 0), true);;
    private final SpaceBackground spaceBackground = new SpaceBackground();
    private final Array<Projectile> projectiles = new Array<>();
    private final List<EnemyEntity> enemies = new ArrayList<>();
    private final Array<Entity> entitiesToDestroy = new Array<>();
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    public static SpriteBatch spriteBatch = new SpriteBatch();
    public static SpriteBatch uiBatch = new SpriteBatch();
    public static OrthographicCamera cam;
    public static OrthographicCamera uiCam;
    private final Player player;
    public static int kills = 0;

    private Bugger() {
        this.player = Player.getInstance();
        this.player.setPlayer(world, 100, 100);
        this.world.setContactListener(new GameContactListener());

        cam = new OrthographicCamera(160, 90);
        cam.position.set(Player.getInstance().getPosition().x / 2f, Player.getInstance().getPosition().y / 2f, 0);
        cam.update();

        uiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCam.position.set(uiCam.viewportWidth / 2, uiCam.viewportHeight / 2, 0);
        uiCam.update();
    }

    public static Bugger getInstance() {
        if (instance == null) {
            instance = new Bugger();
        }
        return instance;
    }

    public void cycle(float delta) {
        renderClear();
        renderBackground();

        step();

        cycleProjectiles(delta);
        cycleEnemies();
        cycleParticles(delta);

        EntityCleaner.getInstance().tryClean();

        updateCamera();

        renderPlayer();
        renderEnemies(delta);
//        renderDebug();

        UIRenderer.getInstance().renderUI();
        InputHandler.getInstance().handleInput();
    }

    public void updateCamera() {
        cam.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);
    }

    public void renderBackground() {
        spaceBackground.render();
    }

    public void renderEnemies(float delta) {
        EnemySpawner.getInstance().cycle(delta);
        for (EnemyEntity enemy : enemies) enemy.cycle();
    }

    public void renderPlayer() {
        player.render();
    }

    public void renderClear() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void renderDebug() {
        debugRenderer.render(world, spriteBatch.getProjectionMatrix());
    }

    public void cycleProjectiles(float delta) {
        for (Projectile arrow : projectiles) {
            if (!arrow.isMarkedToDestroy()) {
                arrow.update(delta);
                arrow.render();
            } else {
                entitiesToDestroy.add(arrow);
            }
        }
    }

    public void cycleEnemies() {
        for (EnemyEntity enemy : enemies) {
            if (enemy.isMarkedToDestroy()) {
                entitiesToDestroy.add(enemy);
            }
        }
    }

    public void cycleParticles(float delta) {
        ParticleManager.getInstance().update(delta);
        ParticleManager.getInstance().render(spriteBatch);
    }

    public void step() {
        world.step(1 / 30f, 6, 2);
    }

    public void dispose() {
        EntityCleaner.getInstance().disposeAll();
    }

    public List<EnemyEntity> getEnemies() {
        return enemies;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Entity> getEntitiesToDestroy() {
        return entitiesToDestroy;
    }

    public Array<Projectile> getProjectiles() {
        return projectiles;
    }

    public SpaceBackground getSpaceBackground() {
        return spaceBackground;
    }
}
