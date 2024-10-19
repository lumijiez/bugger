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
import org.lumijiez.bugger.factories.EnemyFactory;
import org.lumijiez.bugger.handlers.GameContactListener;
import org.lumijiez.bugger.vfx.ParticleManager;
import org.lumijiez.bugger.vfx.SpaceBackground;

import java.util.ArrayList;
import java.util.List;

public class Bugger {
    private static Bugger instance;
    private final World world;
    private final SpaceBackground spaceBackground;
    private final Array<Projectile> projectiles;
    private final List<EnemyEntity> enemies;
    private final Array<Entity> entitiesToDestroy;
    private final Player player;
    private float enemySpawnTimer = 0f;
    private static final float ENEMY_SPAWN_INTERVAL = 0.5f;
    private final Box2DDebugRenderer debugRenderer;
    public static OrthographicCamera cam;
    public static OrthographicCamera uiCam;
    public static SpriteBatch spriteBatch;
    public static SpriteBatch uiBatch;
    public static int kills = 0;
    private final BitmapFont bitmapFont;

    private Bugger() {
        bitmapFont = new BitmapFont(Gdx.files.internal("EA.fnt"), Gdx.files.internal("EA.png"), false);
        world = new World(new Vector2(0, 0), true);
        this.projectiles = new Array<>();
        this.entitiesToDestroy = new Array<>();
        this.enemies = new ArrayList<>();
        this.spaceBackground = new SpaceBackground();
        this.player = Player.getInstance();
        this.player.setPlayer(world, 100, 100);
        this.world.setContactListener(new GameContactListener());
        this.debugRenderer = new Box2DDebugRenderer();
        spriteBatch = new SpriteBatch();
        uiBatch = new SpriteBatch();
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

        clearEntities();

        cam.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        cam.update();

        renderPlayer();
        renderEnemies(delta);
        // renderDebug();

        spriteBatch.setProjectionMatrix(cam.combined);

        uiBatch.begin();

        uiBatch.setColor(1, 1, 1, 1);
        bitmapFont.draw(uiBatch, String.format("Speed: %.2f", Player.getInstance().getBody().getLinearVelocity().len()),
            10, uiCam.viewportHeight - 10);
        bitmapFont.draw(uiBatch, "Kills: " + kills, 10, uiCam.viewportHeight - 40);

        bitmapFont.setColor(0, 1, 0, 1);


        bitmapFont.draw(uiBatch, "Enemies: " + enemies.size(), 10, uiCam.viewportHeight - 70);
        bitmapFont.draw(uiBatch, "Projectiles: " + projectiles.size, 10, uiCam.viewportHeight - 100);
        bitmapFont.draw(uiBatch, "Entities to Destroy: " + entitiesToDestroy.size, 10, uiCam.viewportHeight - 130);
        bitmapFont.draw(uiBatch, String.format("Player Pos: (%.2f, %.2f)",
            Player.getInstance().getBody().getPosition().x,
            Player.getInstance().getBody().getPosition().y), 10, uiCam.viewportHeight - 160);
        bitmapFont.draw(uiBatch, "Bodies: " + world.getBodyCount(), 10, uiCam.viewportHeight - 190);
        bitmapFont.draw(uiBatch, "Proxies: " + world.getProxyCount(), 10, uiCam.viewportHeight - 220);

        bitmapFont.setColor(1, 1, 1, 1);

        uiBatch.end();


        uiBatch.setProjectionMatrix(uiCam.combined);
        uiCam.update();
        handleInput();
    }

    public void renderBackground() {
        spaceBackground.render();
    }

    public void renderEnemies(float delta) {

        enemySpawnTimer += delta;
        if (enemySpawnTimer >= ENEMY_SPAWN_INTERVAL) {
            enemies.add(EnemyFactory.createRandomEnemy(world, Player.getInstance().getPosition()));
            enemySpawnTimer = 0;
        }

        for (EnemyEntity enemy : enemies) {
            enemy.moveTowards(player.getPosition());
            enemy.render();
        }
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

    public void clearEntities() {
        for (Entity entity : entitiesToDestroy) {
            world.destroyBody(entity.getBody());
            if (entity instanceof Projectile) {
                projectiles.removeValue((Projectile) entity, true);
            }
            if (entity instanceof EnemyEntity) {
                playParticle(entity.getBody().getPosition().x, entity.getBody().getPosition().y);
                enemies.remove(entity);
            }

        }
        entitiesToDestroy.clear();
    }

    public void step() {
        world.step(1 / 30f, 6, 2);
    }

    public void playParticle(float x, float y) {
        ParticleManager.getInstance().playEffect(x, y);
    }

    public void shoot() {
        Ray ray = player.shootArrow();
        projectiles.add(ray);
    }

    public void handleInput() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            shoot();
        }
    }

    public SpriteBatch batch() {
        return spriteBatch;
    }

    public void dispose() {
        spriteBatch.dispose();
        uiBatch.dispose();
        world.dispose();
        spaceBackground.dispose();
        ParticleManager.getInstance().dispose();
    }
}
